package com.twopark1jo.lobster.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceRepository workSpaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @GetMapping("/details")
    public Optional<Workspace> getWorkSpace(@RequestParam("id") String workspaceId){
        return workSpaceRepository.findById(workspaceId);
    }

    @GetMapping("/member")
    public Optional<WorkspaceMember> getWorkspaceMember(@RequestParam("id") String workspaceId,
                                                        @RequestParam("email") String email){
        return workspaceMemberRepository.findByWorkspaceIdAndEmail(workspaceId, email);
    }

    @GetMapping("/memberworkspace")
    public List<WorkspaceMember> getWorkspaceList(@RequestParam("email") String email){
         return workspaceMemberRepository.findAllByEmail(email);
    }
}
