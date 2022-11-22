package com.twopark1jo.lobster.department.chat;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("command : " + accessor.getCommand());

        if(StompCommand.SUBSCRIBE == accessor.getCommand()){
            System.out.println("SUBSCRIBE>>>>>>>>>>>>SessionId = " + accessor.getSessionId());
        }

        if(StompCommand.DISCONNECT == accessor.getCommand()){
            System.out.println("DISCONNECT>>>>>>>>>>>>SessionId = " + accessor.getSessionId());
        }

        if(StompCommand.CONNECT == accessor.getCommand()){
            System.out.println("CONNECT>>>>>>>>>>>>SessionId = " + accessor.getSessionId());
        }

        if(StompCommand.UNSUBSCRIBE == accessor.getCommand()){
            System.out.println("UNSUBSCRIBE>>>>>>>>>>>>SessionId = " + accessor.getSessionId());
        }

        if(StompCommand.ERROR == accessor.getCommand()){
            System.out.println("ERROR>>>>>>>>>>>>SessionId = " + accessor.getSessionId());
        }

        return message;
    }
}
