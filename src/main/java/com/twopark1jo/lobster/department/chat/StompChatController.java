package com.twopark1jo.lobster.department.chat;

import com.twopark1jo.lobster.department.department.DepartmentRepository;
import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.department.department.member.DepartmentMemberController;
import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import com.twopark1jo.lobster.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequiredArgsConstructor
public class StompChatController {

    private final ChatContentRepository chatContentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMemberController departmentMemberController;

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

    private LocalDateTime getLocalDateTime(){
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    private String getChatContentId(String departmentId, LocalDateTime date){
        return departmentId + date;
    }

    //"/pub/chat/invitation
    @MessageMapping(value = "/chat/invitation")
    public ResponseEntity inviteToDepartment(List<DepartmentMember> departmentMemberList){
        String departmentId = departmentMemberList.get(0).getDepartmentId();
        ChatContent chatContent;
        LocalDateTime date = getLocalDateTime();

        ResponseEntity responseEntity =
                departmentMemberController.addToDepartmentMemberList(departmentId, departmentMemberList);

        if(responseEntity.getStatusCode() != HttpStatus.CREATED){
            return responseEntity;
        }

        chatContent = ChatContent.builder()
                .chatId(getChatContentId(departmentId, date))
                .departmentId(departmentId)
                .email(null)
                .content(getMemberList(departmentMemberList) + "님이 채팅방에 참여하였습니다.")
                .date(date.toString())
                .contentType("0")
                .link(null)
                .build();

        simpMessagingTemplate.convertAndSend("/sub/chat/department/" + departmentId, chatContent);

        return responseEntity;
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
        boolean isDepartment = departmentRepository.existsById(departmentId);

        if(isDepartment){
            return new ResponseEntity<>(chatContentRepository.findAllByDepartmentId(departmentId), HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}
