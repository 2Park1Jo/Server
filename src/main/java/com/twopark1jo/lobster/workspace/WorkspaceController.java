package com.twopark1jo.lobster.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceRepository workSpaceRepository;

    @GetMapping("/information")
    public Workspace getWorkSpace(@RequestParam("id") String workspaceId){
        return workSpaceRepository.getWorkSpace(workspaceId);
    }
}
