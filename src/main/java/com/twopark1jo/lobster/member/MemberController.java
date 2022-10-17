package com.twopark1jo.lobster.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/profile")
    public Member getMember(@RequestParam("email") String email){
        return memberRepository.getMember(email);
    }

    @GetMapping("/checkduplicateid")
    public int checkDuplicateEmail(@RequestParam("email") String email){
        return memberRepository.checkDuplicateEmail(email);
    }

    @GetMapping("/checklogin")
    public int checkLogin(@RequestParam("email") String email,
                          @RequestParam("password") String password){
        return memberRepository.checkLogin(email, password);
    }

    @GetMapping("/signup")
    public void signUp(@RequestParam("email") String email,
                      @RequestParam("password") String password,
                      @RequestParam("name") String name){
        memberRepository.save(new Member(email, password, name));
    }
}
