package com.twopark1jo.lobster.member;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private String password;

    @Column(name = "member_name")
    @NotBlank
    private String memberName;

    @Builder
    public Member(String email, String memberName) {
        this.email = email;
        this.memberName = memberName;
    }

    @Builder
    public Member(String email, String password, String memberName) {
        this.email = email;
        this.password = password;
        this.memberName = memberName;
    }
}
