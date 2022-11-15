package com.twopark1jo.lobster.department.department;

import com.twopark1jo.lobster.exception.DepartmentException;
import com.twopark1jo.lobster.exception.ErrorCode;
import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.apache.naming.HandlerRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;
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
    @PostMapping("/workspace/department/create")
    public ResponseEntity create(@RequestBody Department department){
        boolean isDuplicatedDepartmentId =
                departmentService.isExistingDepartment(department.getDepartmentId());

        boolean isDuplicatedDepartmentName = departmentService.isDepartmentNameInWorkspace(
                        department.getWorkspaceId(), department.getDepartmentName());

        if(isDuplicatedDepartmentId){
            throw new DepartmentException(ErrorCode.EXISTED_DEPARTMENT_ID);
        }
        if(isDuplicatedDepartmentName){
            throw new DepartmentException(ErrorCode.EXISTED_DEPARTMENT_NAME);
        }

        departmentService.create(department);

        return new ResponseEntity<>(HttpStatus.CREATED);
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
