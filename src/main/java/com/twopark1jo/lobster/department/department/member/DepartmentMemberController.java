package com.twopark1jo.lobster.department.department.member;

import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import com.twopark1jo.lobster.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentMemberController {
    private final MemberServiceImpl memberService;

    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    //해당 부서의 모든 멤버 목록
    @GetMapping("/{departmentId}/members")
    public ResponseEntity<List<DepartmentMember>> getMemberListByDepartment(@PathVariable String departmentId) {
        List<DepartmentMember> memberList = memberService.getMemberListByDepartment(departmentId);

        if(memberList == null || memberList.size() == 0){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    @PostMapping("/{departmentId}/invitation")
    public ResponseEntity addDepartmentMemberList(@PathVariable String departmentId,
                                                   @RequestBody List<DepartmentMember> memberList) {
        if(memberService.addToDepartment(memberList)){
            return new ResponseEntity(HttpStatus.CREATED);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
