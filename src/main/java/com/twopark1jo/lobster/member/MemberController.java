package com.twopark1jo.lobster.member;

import com.twopark1jo.lobster.exception.ErrorCode;
import com.twopark1jo.lobster.exception.ErrorResponse;
import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import com.twopark1jo.lobster.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    @GetMapping("/profile")
    public Optional<Member> getMember(@RequestParam("email") String email){
        return memberRepository.findById(email);
    }

    @GetMapping("/duplicateid")
    public boolean checkDuplicateEmail(@RequestParam("email") String email){
        return memberRepository.existsById(email);
    }

    @PostMapping("/login")
    public int checkLogin(@RequestBody Member member){
        return memberRepository.checkLogin(member.getEmail(), member.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@Valid @RequestBody Member member){
        boolean isMember = memberRepository.existsById(member.getEmail());

        if(isMember){
            throw new MemberException(ErrorCode.EXISTED_EMAIL);
        }

        memberRepository.save(new Member(member.getEmail(), member.getPassword(),
                    member.getName()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/allmember")
    public List<Member> getAllMemberList(){
        return memberRepository.findAll();
    }
}
