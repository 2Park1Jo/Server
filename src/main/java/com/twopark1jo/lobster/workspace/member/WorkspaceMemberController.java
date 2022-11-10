package com.twopark1jo.lobster.workspace.member;

import com.twopark1jo.lobster.workspace.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceMemberController {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    //워크스페이스의 회원 목록
    @GetMapping("/{workspaceId}/members")
    public ResponseEntity<List<WorkspaceMember>> getWorkspaceMember(@PathVariable String workspaceId){
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);

        if(isWorkspace){
            return new ResponseEntity<>(workspaceMemberRepository.findAllByWorkspaceId(workspaceId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
