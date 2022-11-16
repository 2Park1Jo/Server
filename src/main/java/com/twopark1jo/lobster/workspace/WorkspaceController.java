package com.twopark1jo.lobster.workspace;

import com.twopark1jo.lobster.department.department.Department;
import com.twopark1jo.lobster.department.department.DepartmentController;
import com.twopark1jo.lobster.department.department.DepartmentRepository;
import com.twopark1jo.lobster.department.department.DepartmentServiceImpl;
import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.department.department.member.DepartmentMemberRepository;
import com.twopark1jo.lobster.member.Member;
import com.twopark1jo.lobster.member.MemberRepository;
import com.twopark1jo.lobster.workspace.member.WorkspaceMember;
import com.twopark1jo.lobster.workspace.member.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceRepository workspaceRepository;
    private final DepartmentServiceImpl departmentService;
    @Autowired
    private DepartmentMemberRepository departmentMemberRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;

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

    private String getLocalDateTime(){
        LocalDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().withNano(0);
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return date.format(myPattern);
    }

    private String getWorkspaceId(String workspaceId){
        String date = getLocalDateTime();
        return workspaceId.substring(0, 3) + date;
    }

    @PostMapping("{email}/workspace/create")
    public ResponseEntity create(@PathVariable("email") String email,
                                 @RequestBody Workspace workspace){

        boolean isWorkspace = workspaceRepository.existsById(workspace.getWorkspaceId());
        String memberName = memberRepository.findByEmail(email).getMemberName();

        if(isWorkspace){
            return ResponseEntity.badRequest().build();
        }

        workspace.setWorkspaceId(getWorkspaceId(email));
        workspaceRepository.save(workspace);        //워크스페이스 생성

        Department department = new Department("", workspace.getWorkspaceId(),
                "\uD83D\uDCE2 공지방", null, null);

        departmentService.create(department);       //기본 공지방 생성

        workspaceMemberRepository.save(new WorkspaceMember(email, workspace.getWorkspaceId(),
                memberName, null));    //워크스페이스 멤버에 추가

        departmentMemberRepository.save(new DepartmentMember(department.getDepartmentId(),
                email, memberName,null, null));     //공지방에 회원정보 추가

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("{email}/workspaces")
    public ResponseEntity<List<Workspace>> getListOfWorkspacesByMember(@PathVariable("email") String email){
        boolean isMember = memberRepository.existsById(email);

        if(isMember){
            List<Workspace> workspaceList = workspaceRepository.findAllByWorkspaceMember(email);
            return new ResponseEntity<>(workspaceList, HttpStatus.OK);
        }

        return  ResponseEntity.badRequest().build();
    }
}
