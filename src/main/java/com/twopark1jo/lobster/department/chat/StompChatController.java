package com.twopark1jo.lobster.department.chat;

import com.twopark1jo.lobster.department.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    //특정 브로커로 메세지 전달
    private final SimpMessageSendingOperations sendingOperations;

    //client가 send 경로(setApplicationDestinationPrefixes)
    //"/pub/chat/enter" : 회원 입장 -> "/sub/chat/room/{departmentId}"로 채팅방에 참여한 회원 이메일 전송
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessage chatMessage){
        chatMessage.setContent(chatMessage.getEmail() + "님이 채팅방에 참여하였습니다.");
        sendingOperations.convertAndSend("/sub/chat/department/" + chatMessage.getDepartmentId(), chatMessage);
    }

    //"/pub/chat/message" : 메세지 전송 -> "/sub/chat/department/{departmentId}"로 해당 채팅방으로 메세지 전달
    @MessageMapping(value = "/chat/message")
    public void message(ChatMessage chatMessage){
        System.out.println("chatMessage = " + chatMessage.toString());
        sendingOperations.convertAndSend("/sub/chat/department/" + chatMessage.getDepartmentId(), chatMessage);
    }
}
