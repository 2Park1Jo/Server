package com.twopark1jo.lobster.chatting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatMessage {
    private String departmentId;  //부서아이디(채팅방 아이디)
    private String email;     //회원 이메일
    private String content;   //대화내용
    
    //private String date;
    //private String contentType;
    //private String link;

    public ChatMessage(String departmentId, String email, String content) {
        this.departmentId = departmentId;
        this.email = email;
        this.content = content;
    }
}
