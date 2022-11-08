package com.twopark1jo.lobster.member;

import com.twopark1jo.lobster.exception.ErrorCode;
import com.twopark1jo.lobster.exception.ErrorResponse;
import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import com.twopark1jo.lobster.exception.MemberException;
import com.twopark1jo.lobster.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.hibernate.metamodel.model.domain.internal.MapMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.scanner.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public ResponseEntity checkLogin(@RequestBody Member member){ //HttpServletRequest request){
        int isMember = memberRepository.checkLogin(member.getEmail(), member.getPassword());

        //HttpSession session = request.getSession();
        //session.setAttribute("email", member.getEmail());   //세션 저장

        if(isMember == Constants.IS_MEMBER){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody Member member, HttpServletRequest request){
        //HttpSession session = request.getSession();
        //session.invalidate();    //세션 삭제

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity signUp(@Valid @RequestBody Member member){
        boolean isMember = memberRepository.existsById(member.getEmail());

        if(isMember){
            throw new MemberException(ErrorCode.EXISTED_EMAIL);
        }

        memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/allmember")
    public List<Member> getAllMemberList(){
        return memberRepository.findAll();
    }
}
