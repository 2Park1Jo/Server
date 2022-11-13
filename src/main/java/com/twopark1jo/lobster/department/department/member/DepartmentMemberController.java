package com.twopark1jo.lobster.department.department.member;

import com.twopark1jo.lobster.department.department.Department;
import com.twopark1jo.lobster.department.department.DepartmentRepository;
import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import com.twopark1jo.lobster.utility.Constants;
import com.twopark1jo.lobster.workspace.member.WorkspaceMember;
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
    private final DepartmentMemberRepository departmentMemberRepository;
    private final DepartmentRepository departmentRepository;
    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    //해당 부서의 모든 멤버 목록
    @GetMapping("/{departmentId}/members")
    public ResponseEntity<List<DepartmentMember>> getMemberByDepartment(@PathVariable String departmentId) {
        boolean isDepartment = departmentRepository.existsById(departmentId);

        if (isDepartment == !Constants.IS_EXISTING_DEPARTMENT) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(departmentMemberRepository.findAllByDepartmentId(departmentId), HttpStatus.OK);
    }

    @PostMapping("/{departmentId}/invitation")
    public ResponseEntity addToDepartmentMemberList(@PathVariable String departmentId,
                                                   @RequestBody List<DepartmentMember> departmentMemberList) {
        boolean isDepartment = departmentRepository.existsById(departmentId);
        DepartmentMember member;

        if (isDepartment == !Constants.IS_EXISTING_DEPARTMENT) {
            return ResponseEntity.notFound().build();
        }

        for (int index = 0; index < departmentMemberList.size(); index++) {
            member = departmentMemberList.get(index);
            //부서에 이미 소속된 회원정보는 저장X
            if (departmentMemberRepository.existsByDepartmentIdAndEmail(departmentId, member.getEmail())) {
                continue;
            }

            member.setDepartmentId(departmentId);
            departmentMemberRepository.save(member);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
