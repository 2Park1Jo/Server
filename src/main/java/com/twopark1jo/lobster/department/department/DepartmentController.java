package com.twopark1jo.lobster.department.department;

import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.exception.DepartmentException;
import com.twopark1jo.lobster.exception.ErrorCode;
import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import com.twopark1jo.lobster.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;
    private final MemberServiceImpl memberService;
    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    //전체 부서 목록
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getDepartmentList(){
        List<Department> departmentList = departmentService.getDepartmentList();

        if(departmentList.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    //워크스페이스별 부서목록 조회
    @GetMapping("/workspace/{workspaceId}/departments")
    public ResponseEntity<List<Department>> getDepartmentListByWorkspace(@PathVariable String workspaceId){
        List<Department> departmentList = departmentService.getDepartmentListByWorkspace(workspaceId);

        if(departmentList.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    //워크스페이스의 부서 생성
    @PostMapping("workspace/department/create")
    public ResponseEntity create(@RequestBody DepartmentCreation departmentCreation){
        Department department = departmentCreation.getDepartment();    //생성할 부서 정보
        List<DepartmentMember> departmentMemberList = departmentCreation.getDepartmentMemberList();  //부서에 추가할 회원 목록

        if(departmentService.create(department)){
            for (int i=0; i<departmentMemberList.size(); i++){   //부서 회원 정보에 생성한 부서 아이디 저장
                departmentMemberList.get(i).setDepartmentId(department.getDepartmentId());
            }

            memberService.addToDepartment(departmentMemberList); //부서회원정보에 회원 정보 저장

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        throw new DepartmentException(ErrorCode.EXISTED_DEPARTMENT_NAME);
    }

    //회원이 속한 워크스페이스 부서 목록
    @GetMapping("/member/{email}/workspace/{workspaceId}/departments")
    public ResponseEntity<List<Department>> getDepartmentListByWorkspaceAndMember(@PathVariable ("email") String email,
                                                                                  @PathVariable ("workspaceId") String workspaceId){
        List<Department> departmentList = departmentService.getDepartmentListByMember(workspaceId, email);

        if(departmentList.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    //부서 정보 수정
    @PostMapping("/department/update")
    public ResponseEntity updateDepartment(@RequestBody Department department) {
        boolean isDepartment = departmentService.isExistingDepartment(department.getDepartmentId());

        if (isDepartment) {
            departmentService.create(department);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
