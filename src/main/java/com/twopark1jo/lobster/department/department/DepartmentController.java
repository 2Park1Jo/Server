package com.twopark1jo.lobster.department.department;

import com.twopark1jo.lobster.exception.DepartmentException;
import com.twopark1jo.lobster.exception.ErrorCode;
import com.twopark1jo.lobster.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentRepository departmentRepository;
    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    //워크스페이스별 부서목록 조회
    @GetMapping("/workspace/{workspaceId}/department")
    public List<Department> getDepartmentListByWorkspace(@PathVariable String workspaceId){
        return departmentRepository.findAllByWorkspaceId(workspaceId);
    }

    @PostMapping("/workspace/{workspaceId}/chat/departemnt/create")
    public ResponseEntity create(@Valid Department department){
        boolean isDuplicatedDepartment =
                departmentRepository.existsById(department.getDepartmentId());

        if(isDuplicatedDepartment){
            throw new DepartmentException(ErrorCode.EXISTED_DEPARTMENT);
        }

        departmentRepository.save(department);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //전체 부서 목록
    @GetMapping("/department")
    public List<Department> getDepartmentList(@PathVariable String workspaceId){
        return departmentRepository.findAll();
    }
}
