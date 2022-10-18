package com.twopark1jo.lobster.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/checklogin")
    public int checkLogin(@RequestBody Member member){
        return memberRepository.checkLogin(member.getEmail(), member.getPassword());
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody Member member){
        memberRepository.save(new Member(member.getEmail(), member.getPassword(),
                member.getName()));
    }

    @GetMapping("/allmember")
    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }
}
