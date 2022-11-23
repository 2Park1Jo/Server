package com.twopark1jo.lobster.department.chat.lastchat;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class LastChatContentPK implements Serializable {
    @Column(name = "email")
    private String email;  //부서아이디(채팅방 아이디)

    @Column(name = "department_id")
    private String departmentId;  //부서아이디(채팅방 아이디)
}
