package com.twopark1jo.lobster.department.chatroom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

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
