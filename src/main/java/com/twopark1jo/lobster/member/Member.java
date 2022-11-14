package com.twopark1jo.lobster.member;

import lombok.*;

import javax.persistence.*;

@Entity(name = "member")
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "member_name")
    private String memberName;

    public Member(String email, String memberName) {
        this.email = email;
        this.memberName = memberName;
    }
}
