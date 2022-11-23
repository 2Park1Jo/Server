package com.twopark1jo.lobster.department.chat.lastchat;

import com.twopark1jo.lobster.department.chat.lastchat.LastChatContentPK;
import lombok.*;

import javax.persistence.*;

@Entity(name = "last_chat_content")
@Table(name = "last_chat_content")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Data
@IdClass(LastChatContentPK.class)
public class LastChatContent {
    @Id
    @Column(name = "email")
    private String email;  //부서아이디(채팅방 아이디)

    @Id
    @Column(name = "department_id")
    private String departmentId;  //부서아이디(채팅방 아이디)

    @Column(name = "workspace_id")
    private String workspaceId;   //워크스페이스 아이디

    @Column(name = "last_chat_id")
    private String lastChatId;    //마지막 채팅 아이디

    @Column(name = "message_count")
    private String messageCount;  //채팅방의 총 메세지 수
}
