package com.twopark1jo.lobster.member;

import com.twopark1jo.lobster.exception.ErrorCode;
import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import com.twopark1jo.lobster.exception.MemberException;
import com.twopark1jo.lobster.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;
    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    @GetMapping("/profile")
    public ResponseEntity<Member> getMember(@RequestParam("email") String email){
        Member member = memberService.getMemberProfile(email);

        if(member == null){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @GetMapping("/duplicateid")
    public boolean checkDuplicateEmail(@RequestParam("email") String email){
        return memberService.duplicateId(email);
    }

    @PostMapping("/login")
    public ResponseEntity checkLogin(@RequestBody Member member){ //HttpServletRequest request){
        if(memberService.login(member)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody Member member){

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity signUp(@Valid @RequestBody Member member){
        if(memberService.signUp(member)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        throw new MemberException(ErrorCode.EXISTED_EMAIL);
    }

    @GetMapping("/allmember")
    public List<Member> getAllMemberList(){
        return memberService.getAllMemberList();
    }
}
