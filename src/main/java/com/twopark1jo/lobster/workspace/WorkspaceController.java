package com.twopark1jo.lobster.workspace;

import com.twopark1jo.lobster.department.department.Department;
import com.twopark1jo.lobster.department.department.DepartmentController;
import com.twopark1jo.lobster.department.department.DepartmentRepository;
import com.twopark1jo.lobster.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceRepository workspaceRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/workspace/{workspaceId}/detail")
    public ResponseEntity<Workspace> getWorkspaceDetail(@PathVariable String workspaceId){
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);

        if(isWorkspace){
            return new ResponseEntity(workspaceRepository.findById(workspaceId), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/workspaces")
    public ResponseEntity<List<Workspace>> getWorkspaces(){
        List<Workspace> workspaceList = workspaceRepository.findAll();

        return new ResponseEntity<>(workspaceList, HttpStatus.OK);
    }

    @PostMapping("/workspace/create")
    public ResponseEntity create(@RequestBody Workspace workspace){
        boolean isWorkspace = workspaceRepository.existsById(workspace.getWorkspaceId());

        if(isWorkspace){
            return ResponseEntity.badRequest().build();
        }

        workspaceRepository.save(workspace);        //워크스페이스 생성

        Department department = new Department(
                workspace.getWorkspaceId() + "_1", workspace.getWorkspaceId(),
                "\uD83D\uDCE2 공지방", null, null);

        departmentRepository.save(department);     //기본 공지방 생성

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("{email}/workspace/list")
    public ResponseEntity<List<Workspace>> getListOfWorkspacesByMember(@PathVariable("email") String email){
        boolean isMember = memberRepository.existsById(email);

        if(isMember){
            List<Workspace> workspaceList = workspaceRepository.findAllByWorkspaceMember(email);
            return new ResponseEntity<>(workspaceList, HttpStatus.OK);
        }

        return  ResponseEntity.badRequest().build();
    }
}
