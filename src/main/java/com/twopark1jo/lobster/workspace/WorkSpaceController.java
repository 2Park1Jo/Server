package com.twopark1jo.lobster.workspace;

import com.twopark1jo.lobster.member.Member;
import com.twopark1jo.lobster.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkSpaceController {

    private final WorkSpaceRepository workSpaceRepository;

    @GetMapping("/information")
    public WorkSpace getWorkSpace(@RequestParam("id") String workspaceId){
        return workSpaceRepository.getWorkSpace(workspaceId);
    }
}
