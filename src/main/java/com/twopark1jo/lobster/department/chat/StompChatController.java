package com.twopark1jo.lobster.department.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twopark1jo.lobster.bucket.BucketServiceImpl;
import com.twopark1jo.lobster.department.department.*;
import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.member.MemberServiceImpl;
import com.twopark1jo.lobster.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StompChatController {

    private final ChatContentRepository chatContentRepository;
    private final DepartmentServiceImpl departmentService;
    private final MemberServiceImpl memberService;

    private final BucketServiceImpl bucketService;
    private final SimpMessagingTemplate simpMessagingTemplate;  //특정 브로커로 메세지 전달

    //client가 send 경로(setApplicationDestinationPrefixes)
    //"/pub/chat/enter" : 회원 입장 -> "/sub/chat/department/{departmentId}"로 채팅방에 참여한 회원 이메일 전송
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatContent chatContent){
        chatContent.setContent(chatContent.getEmail() + "님이 채팅방에 참여하였습니다.");

        //chatContentRepository.save(chatContent);

        simpMessagingTemplate.convertAndSend("/sub/chat/department/" + chatContent.getDepartmentId(), chatContent);
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
    @MessageMapping(value = "/chat/invitation")
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
                "/sub/chat/department/" + departmentId, chatContent);

        return ResponseEntity.ok().build();
    }

    //"/pub/department/creation" : 부서 생성 + 회원 추가
    @MessageMapping(value = "/chat/department/creation")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public ResponseEntity createDepartment(@RequestBody DepartmentCreation departmentCreation){
        Department department = departmentCreation.getDepartment();    //생성할 부서 정보
        List<DepartmentMember> departmentMemberList = departmentCreation.getDepartmentMemberList();  //부서에 추가할 회원 목록

        departmentService.create(department);                //부서 생성

        if(departmentService.create(department)){
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

    //"/pub/chat/message" : 메세지 전송 -> "/sub/chat/department/{departmentId}"로 해당 채팅방으로 메세지 전달
    @MessageMapping(value = "/chat/message")
    @PostMapping("/test")
    public void message(@RequestBody ChatContent chatContent) {
        String content;

        System.out.println("chatMessage = " + chatContent.toString());

        content = Normalizer.normalize(chatContent.getContent(), Normalizer.Form.NFC);  //윈도우, 맥 자소분리 합치기
        chatContent.setContent(content);

        chatContentRepository.save(chatContent);  //채팅내용 저장

        //simpMessagingTemplate.convertAndSend("/sub/chat/department/" + chatContent.getDepartmentId(), chatContent);
    }

    //"/pub/workspace/invitation" : 회원 추가 알림
    @MessageMapping(value = "/chat/workspace/invitation")
    public void announceAdditionOfWorkspaceMembers(String chatcontent){


        simpMessagingTemplate.convertAndSend("/sub/chat/workspace", chatcontent);
    }

    @GetMapping("department/{departmentId}/chat/content")
    public ResponseEntity<List<ChatContent>> getDepartmentChatContent(@PathVariable("departmentId") String departmentId){
        boolean isDepartment = departmentService.isExistingDepartment(departmentId);

        if(isDepartment){
            return new ResponseEntity<>(chatContentRepository.findAllByDepartmentId(departmentId), HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}
