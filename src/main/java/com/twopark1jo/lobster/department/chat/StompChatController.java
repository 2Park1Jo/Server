package com.twopark1jo.lobster.department.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twopark1jo.lobster.department.department.*;
import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.department.department.member.DepartmentMemberController;
import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import com.twopark1jo.lobster.member.MemberRepository;
import com.twopark1jo.lobster.member.MemberServiceImpl;
import com.twopark1jo.lobster.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    //특정 브로커로 메세지 전달
    private final SimpMessagingTemplate simpMessagingTemplate;

    //client가 send 경로(setApplicationDestinationPrefixes)
    //"/pub/chat/enter" : 회원 입장 -> "/sub/chat/department/{departmentId}"로 채팅방에 참여한 회원 이메일 전송
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatContent chatContent){
        chatContent.setContent(chatContent.getEmail() + "님이 채팅방에 참여하였습니다.");

        //chatContentRepository.save(chatContent);

        simpMessagingTemplate.convertAndSend("/sub/chat/department/" + chatContent.getDepartmentId(), chatContent);
    }

    //부서에 초대한 회원의 명단
    private String getMemberList(List<DepartmentMember> departmentMemberList){
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

    //채팅내용 아이디 = 부서아이디 + 현재 시각
    private String getTableId(String tableId, String date){
        return tableId.substring(0, 3) + date;
    }

    //"/pub/chat/invitation"
    @MessageMapping(value = "/chat/invitation")
    public ResponseEntity inviteToDepartment(List<DepartmentMember> departmentMemberList){
        String departmentId = departmentMemberList.get(0).getDepartmentId();
        ChatContent chatContent;
        String date = getLocalDateTime();

        if(memberService.addToDepartment(departmentId, departmentMemberList)
            == !Constants.IS_DATA_SAVED_SUCCESSFULLY){
            return ResponseEntity.notFound().build();
        }

        chatContent = ChatContent.builder()
                .chatId(getTableId(departmentId, date))
                .departmentId(departmentId)
                .email(null)
                .content(getMemberList(departmentMemberList) + "님이 채팅방에 참여하였습니다.")
                .date(date.toString())
                .contentType("-1")
                .link(null)
                .build();

        chatContentRepository.save(chatContent);
        simpMessagingTemplate.convertAndSend(
                "/sub/chat/department/" + departmentId, chatContent);

        return ResponseEntity.ok().build();
    }

    //"/pub/department/creation" : 부서 생성
    @MessageMapping(value = "/chat/department/creation")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @PostMapping("/test")
    public void createDepartment(@RequestBody DepartmentCreation departmentCreation){
        Department department = departmentCreation.getDepartment();
        List<DepartmentMember> departmentMemberList = departmentCreation.getDepartmentMemberList();

        department.setDepartmentId(getTableId(department.getWorkspaceId(), getLocalDateTime()));

        departmentService.create(department);  //부서 생성

        for (int i=0; i<departmentMemberList.size(); i++){
            departmentMemberList.get(i).setDepartmentId(department.getDepartmentId());
        }

        inviteToDepartment(departmentMemberList); //멤버 추가

        simpMessagingTemplate.convertAndSend(
                "/sub/chat/workspace/" + department.getWorkspaceId());
    }

    //"/pub/chat/message" : 메세지 전송 -> "/sub/chat/department/{departmentId}"로 해당 채팅방으로 메세지 전달
    @MessageMapping(value = "/chat/message")
    public void message(ChatContent chatContent){
        System.out.println("chatMessage = " + chatContent.toString());

        chatContent.setChatId(chatContent.getDepartmentId() + chatContent.getDate());   //채팅아이디(방 아이디 + 시간)

        chatContentRepository.save(chatContent);  //채팅내용 저장

        simpMessagingTemplate.convertAndSend("/sub/chat/department/" + chatContent.getDepartmentId(), chatContent);
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
