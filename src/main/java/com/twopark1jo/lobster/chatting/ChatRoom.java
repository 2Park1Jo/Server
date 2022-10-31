package com.twopark1jo.lobster.chatting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
    private String departmentId;
    private String roomName;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom(String departmentId, String roomName) {
        this.departmentId = departmentId;
        this.roomName = roomName;
    }
}
