package com.twopark1jo.lobster.member;

import lombok.*;

import javax.persistence.*;

@Entity(name = "member")
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private String name;

    @Builder
    public Member(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @Builder
    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
