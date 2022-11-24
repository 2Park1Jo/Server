package com.twopark1jo.lobster.workspace.member;

import com.twopark1jo.lobster.member.MemberServiceImpl;
import com.twopark1jo.lobster.utility.Constants;
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
    private final MemberServiceImpl memberService;

    //워크스페이스의 회원 목록
    @GetMapping("/{workspaceId}/members")
    public ResponseEntity<List<WorkspaceMember>> getWorkspaceMember(@PathVariable String workspaceId){
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);

        if(isWorkspace == !Constants.IS_EXISTING_WORKSPACE) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(
                workspaceMemberRepository.findAllByWorkspaceId(workspaceId), HttpStatus.OK);
    }

    //워크스페이스에 회원 초대
    @PostMapping("/{workspaceId}/invitation")
    public ResponseEntity addToWorkspaceMemberList(@PathVariable String workspaceId,
                                                   @RequestBody List<WorkspaceMember> workspaceMemberList){
        boolean isWorkspace = workspaceRepository.existsById(workspaceId);
        WorkspaceMember member;

        if(isWorkspace == !Constants.IS_EXISTING_WORKSPACE) {   //워크스페이스 존재여부 확인
            return ResponseEntity.notFound().build();
        }

        for(int index = 0; index < workspaceMemberList.size(); index++){   //워크스페이스 회원DB에 회원정보 저장
            member = workspaceMemberList.get(index);  //워크스페이스에 초대할 회원 정보
            //워크스페이스에 이미 소속된 회원정보는 저장X
            if(workspaceMemberRepository.existsByWorkspaceIdAndEmail(workspaceId, member.getEmail())){
                continue;
            }

            member.setWorkspaceId(workspaceId);       //워크스페이스 회원정보에 워크스페이스 정보 저장
            member.setMemberName(memberService.getMemberName(member.getEmail())); //회원 이름 저장
            workspaceMemberRepository.save(member);   //워크스페이스 회원정보 저장
        }

        memberService.addToNoticeBoard(workspaceMemberList);   //공지방에 회원 초대

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
