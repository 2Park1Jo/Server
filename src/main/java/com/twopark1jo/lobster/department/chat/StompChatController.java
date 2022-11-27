package com.twopark1jo.lobster.department.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twopark1jo.lobster.department.chat.model.ChatContent;
import com.twopark1jo.lobster.department.chat.model.ConnectedMember;
import com.twopark1jo.lobster.department.chat.model.NumberOfMessage;
import com.twopark1jo.lobster.department.chat.model.TopThreeChats;
import com.twopark1jo.lobster.department.department.*;
import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.member.MemberServiceImpl;
import com.twopark1jo.lobster.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class StompChatController {

    private final ChatContentRepository chatContentRepository;
    private final DepartmentServiceImpl departmentService;
    private final MemberServiceImpl memberService;
    private final SimpMessagingTemplate simpMessagingTemplate;  //특정 브로커로 메세지 전달
    private Map<String, String> sessionList = new HashMap<String, String>();       //부서별 세션목록
    private String sessionId;

    private ConnectedMember getListOfConnectedMembers(){
        List<String> listOfConnectedMembers = new ArrayList<>();
        Iterator<String> mapIter = sessionList.keySet().iterator();
        ConnectedMember connectedMember = new ConnectedMember();

        while(mapIter.hasNext()){                 //현재 로그인한 회원의 세션목록
            String sessionId = mapIter.next();
            String connectedMemberEmail = sessionList.get(sessionId);
            listOfConnectedMembers.add(connectedMemberEmail);
        }

        connectedMember.setListOfConnectedMembers(listOfConnectedMembers);

        return connectedMember;
    }

    //client가 send 경로(setApplicationDestinationPrefixes)
    //"/pub/chat/enter" : 회원 입장 -> "/sub/chat/department/{departmentId}"로 채팅방에 참여한 회원 이메일 전송
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatContent chatContent){
        String email = chatContent.getEmail();        //현재 stomp client연결을 시도한 회원의 이메일

        String key;

        if(sessionList.containsValue(email)){  //사용자가 새로고침을 하는 경우 기존 세션 저장값 삭제
            key = findKeyByValue(email);
            sessionList.remove(key);
        }

        sessionList.put(sessionId, email);     //stomp연결을 시도한 회원의 세션아이디 저장

        simpMessagingTemplate.convertAndSend("/sub/chat/session", getListOfConnectedMembers());
    }

    //부서에 초대한 회원의 명단
    private String getMemberNameList(List<DepartmentMember> departmentMemberList){
        int numberOfMembers = departmentMemberList.size();
        StringBuilder memberName = new StringBuilder();

        for(int index = 0; index < numberOfMembers; index++){
            memberName.append(departmentMemberList.get(index).getMemberName());  //초대받은 회원 명단
            if(index + 1 < numberOfMembers){
                memberName.append(", ");    //회원 이름마다 콤마로 구분
            }
        }

        return memberName.toString();
    }

    //대한민국 서울 기준 현재시각
    private String getLocalDateTime(){
        LocalDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().withNano(0);
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return date.format(myPattern);
    }

    //"/pub/chat/invitation" : 기존 부서에 회원 추가
    @MessageMapping(value = "/chat/department/invitation")
    public ResponseEntity inviteToDepartment(List<DepartmentMember> departmentMemberList){
        String departmentId = departmentMemberList.get(0).getDepartmentId();
        ChatContent chatContent;

        if(memberService.addToDepartment(departmentMemberList)   //부서회원DB에 부서회원목록 저장
            == !Constants.IS_DATA_SAVED_SUCCESSFULLY){
            return ResponseEntity.notFound().build();
        }

        chatContent = ChatContent.builder()                      //채팅방 참여 메세지
                .departmentId(departmentId)
                .email(null)
                .content(getMemberNameList(departmentMemberList) + "님이 채팅방에 참여하였습니다.")
                .date(getLocalDateTime())
                .contentType("-1")
                .link(null)
                .build();

        System.out.println("chatContent = " + chatContent.toString());
        chatContentRepository.save(chatContent);
        simpMessagingTemplate.convertAndSend(
                "/sub/chat/workspace", chatContent);

        return ResponseEntity.ok().build();
    }

    //"/pub/department/creation" : 부서 생성 + 회원 추가
    @MessageMapping(value = "/chat/department/creation")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public ResponseEntity createDepartment(@RequestBody DepartmentCreation departmentCreation){
        Department department = departmentCreation.getDepartment();    //생성할 부서 정보
        List<DepartmentMember> departmentMemberList = departmentCreation.getDepartmentMemberList();  //부서에 추가할 회원 목록

        if(departmentService.create(department)
                == !Constants.IS_DATA_SAVED_SUCCESSFULLY){
            return ResponseEntity.badRequest().build();
        }

        for (int i=0; i<departmentMemberList.size(); i++){   //부서 회원 정보에 생성한 부서 아이디 저장
            departmentMemberList.get(i).setDepartmentId(department.getDepartmentId());
        }

        inviteToDepartment(departmentMemberList);            //회원 추가 및 회원 초대 메세지 전송

        simpMessagingTemplate.convertAndSend(                //부서 생성 메세지 전송
                "/sub/chat/workspace/" + department.getWorkspaceId(), department);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @MessageMapping(value = "/chat/department/update")
    @PostMapping("/test")
    public ResponseEntity updateDepartment(@RequestBody Department department){
        ChatContent chatContent;

        chatContent = ChatContent.builder()                      //부서 정보 변경 메세지
                .departmentId(department.getDepartmentId())
                .email(null)
                .content("부서정보가 변경되었습니다.")
                .date(getLocalDateTime())
                .contentType("-1")
                .link(null)
                .build();

        if(departmentService.update(department)      //부서명이 중복되는 경우 부서정보를 수정하지 않고 끝냄
                == !Constants.IS_DATA_SAVED_SUCCESSFULLY){
            simpMessagingTemplate.convertAndSend(
                    "/sub/chat/workspace/" + department.getWorkspaceId(), "이미 존재하는 부서명입니다.");
            return ResponseEntity.badRequest().build();
        }

        chatContentRepository.save(chatContent);  //부서 수정 메세지 저장

        simpMessagingTemplate.convertAndSend(     //부서 수정 메세지 전송
                "/sub/chat/workspace/" + department.getWorkspaceId(), chatContent);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    //"/pub/chat/message" : 메세지 전송 -> "/sub/chat/department/{departmentId}"로 해당 채팅방으로 메세지 전달
    @MessageMapping(value = "/chat/message")
    public void message(@RequestBody ChatContent chatContent) {
        String content;

        System.out.println("chatMessage = " + chatContent.toString());

        content = Normalizer.normalize(chatContent.getContent(), Normalizer.Form.NFC);  //윈도우, 맥 자소분리 합치기
        chatContent.setContent(content);

        chatContentRepository.save(chatContent);  //채팅내용 저장

        simpMessagingTemplate.convertAndSend("/sub/chat/department/" + chatContent.getDepartmentId(), chatContent);
    }

    //"/pub/chat/workspace/invitation" : 회원 추가 알림
    @MessageMapping(value = "/chat/workspace/invitation")
    public void announceAdditionOfWorkspaceMembers(String chatcontent){

        simpMessagingTemplate.convertAndSend("/sub/chat/workspace", chatcontent);
    }

    //부서별 채팅 데이터
    @GetMapping("department/{departmentId}/chat/content")
    public ResponseEntity<List<ChatContent>> getDepartmentChatContent(@PathVariable("departmentId") String departmentId){
        boolean isDepartment = departmentService.isExistingDepartment(departmentId);

        if(isDepartment){
            return new ResponseEntity<>(chatContentRepository.findAllByDepartmentId(departmentId), HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    private String findKeyByValue(String value){
        for(String key : sessionList.keySet()) {
            // 키와 매핑된 값이랑 equals() 메서드에 전달된 값이랑 일치하면 반복문을 종료합니다.
            if(sessionList.get(key).equals(value)) { // 키가 null이면 NullPointerException 예외 발생
                return key;
            }
        }
        return null;
    }

    void printSessionList(){
        Iterator<String> mapIter = sessionList.keySet().iterator();

        System.out.println();
        while(mapIter.hasNext()){
            String key = mapIter.next();
            String value = sessionList.get( key );

            System.out.println(key+" : "+value);
        }
        System.out.println();
    }

    //stomp가 연결되었을 경우
    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event){
        sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();     //현재 연결을 시도한
    }

    //stomp연결이 끊겼을 경우
    @EventListener(SessionDisconnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event){
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();

        sessionList.remove(sessionId);

        simpMessagingTemplate.convertAndSend("/sub/chat/session", getListOfConnectedMembers());
    }

    @GetMapping("/workspace/{workspaceId}/chat/count")
    public ResponseEntity<List> getNumberOfMessage(@PathVariable String workspaceId){
        List<Department> departmentList = departmentService.getDepartmentListByWorkspace(workspaceId);  //워크스페이스의 부서 목록
        List<NumberOfMessage> numberOfMessageList = new ArrayList<>();
        String departmentId, messageCount;

        for(int index = 0; index < departmentList.size(); index++){
            departmentId = departmentList.get(index).getDepartmentId();
            messageCount = chatContentRepository.getMessageCount(departmentId);

            numberOfMessageList.add(new NumberOfMessage(departmentId, messageCount));  //해당 부서의 총 채팅 메세지 수
        }

        return new ResponseEntity<List>(numberOfMessageList, HttpStatus.OK);
    }

    //워크스페이스의 회원이메일, 부서명을 매칭한 맵 데이터
    private MultiValueMap<String ,String> getDepartmentNameListByWorkspace(String workspaceId){
        MultiValueMap<String ,String> departmentNameMap = new LinkedMultiValueMap<>();
        List<String> departmentNameListFromDB =
                chatContentRepository.getDepartmentNameListByWorkspace(workspaceId);
        int size = departmentNameListFromDB.size();
        String departmentName[];

        for (int index=0; index<size; index++){
            departmentName = departmentNameListFromDB.get(index).split(",");
            departmentNameMap.add(departmentName[0], departmentName[1]);    //key : 이메일, value : 부서명
        }

        return departmentNameMap;
    }

    //워크스페이스에서 회원이 속한 부서명
    private String getDepartmentNameListByMember(MultiValueMap<String ,String> departmentNameMap, String email){
        List<String> departmentNameList = departmentNameMap.get(email);
        StringBuilder departmentName = new StringBuilder();
        int size = departmentNameList.size();

        for (int index=0; index< size; index++){
            departmentName.append(departmentNameList.get(index));
            if(index < size - 1) {
                departmentName.append(", ");
            }
        }

        return departmentName.toString();
    }

    //채팅 수가 가장 많은 상위 3명의 회원 목록
    @GetMapping("/workspace/{workspaceId}/top-three-chats")
    public List<TopThreeChats> getListOfThreeMostChattedPeople(@PathVariable String workspaceId){
        List<String> listOfThreeMostChattedPeopleFromDb = chatContentRepository.getListOfThreeMostChattedPeople(workspaceId);
        List<TopThreeChats> listOfThreeMostChattedMember = new ArrayList<>();
        MultiValueMap<String ,String> departmentNameMap = getDepartmentNameListByWorkspace(workspaceId);
        int size = listOfThreeMostChattedPeopleFromDb.size();
        String topThreeChat[] , departmentNameList;

        for(int index=0; index<size; index++){
            //[0] : email, [1] : 회원이름, [2] : 채팅수
            topThreeChat = listOfThreeMostChattedPeopleFromDb.get(index).split(",");
            departmentNameList = getDepartmentNameListByMember(departmentNameMap, topThreeChat[0]);

            listOfThreeMostChattedMember.add(new TopThreeChats(topThreeChat[1], departmentNameList, topThreeChat[2]));
        }

        return listOfThreeMostChattedMember;
    }
}
