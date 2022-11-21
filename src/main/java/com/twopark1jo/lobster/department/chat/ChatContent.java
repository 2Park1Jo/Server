package com.twopark1jo.lobster.department.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "chat_content")
@Table(name = "chat_content")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ChatContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private String chatId;       //채팅 아이디

    @Column(name = "department_id")
    private String departmentId;  //부서아이디(채팅방 아이디)

    @Column(name = "email")
    private String email;         //회원 이메일

    @Column(name = "content")
    private String content;       //대화내용

    @Column(name = "date")
    private String date;          //대화 시간

    @Column(name = "content_type")
    private String contentType;   //메세지 or 파일

    @Column(name = "link")
    private String link;          //파일 링크
}
