package com.twopark1jo.lobster.department.chat;

import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import com.twopark1jo.lobster.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class StompChatController {

    private final ChatContentRepository chatContentRepository;

    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    //특정 브로커로 메세지 전달
    private final SimpMessagingTemplate simpMessagingTemplate;

    //client가 send 경로(setApplicationDestinationPrefixes)
    //"/pub/chat/enter" : 회원 입장 -> "/sub/chat/room/{departmentId}"로 채팅방에 참여한 회원 이메일 전송
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatContent chatContent){
        chatContent.setContent(chatContent.getEmail() + "님이 채팅방에 참여하였습니다.");
        simpMessagingTemplate.convertAndSend("/sub/chat/department/" + chatContent.getDepartmentId(), chatContent);
    }

    //"/pub/chat/message" : 메세지 전송 -> "/sub/chat/department/{departmentId}"로 해당 채팅방으로 메세지 전달
    @MessageMapping(value = "/chat/message")
    public void message(ChatContent chatContent){
        System.out.println("chatMessage = " + chatContent.toString());

        ///임시 데이터
        chatContent.setDepartmentId("department12345");
        chatContent.setEmail("abc@naver.com");
        chatContent.setContentType("0");
        chatContent.setDate(Timestamp.valueOf(LocalDateTime.now()));
        chatContent.setChatId(chatContent.getDepartmentId() + chatContent.getDate());
        /////////////

        chatContentRepository.save(chatContent);

        simpMessagingTemplate.convertAndSend("/sub/chat/department/" + chatContent.getDepartmentId(), chatContent);
    }

    @GetMapping("/content")
    public List<ChatContent> getDepartmentChatContent(@RequestParam String departmentId){
        return chatContentRepository.findAllByDepartmentId(departmentId);
    }
}
