package com.twopark1jo.lobster.workspace;

import com.twopark1jo.lobster.department.chat.ChatContentRepository;
import com.twopark1jo.lobster.department.chat.model.ChatContent;
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
    private final ChatContentRepository chatContentRepository;
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

    private String getTableId(){
        LocalDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().withNano(0);
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        return date.format(myPattern) + date.getNano();
    }

    //워크스페이스 생성 후 공지방에 자동으로 초대받은 회원의 이름 목록
    private void saveMemberEntryMesaage(String departmentId, String memberName){
        ChatContent chatContent;

        chatContent = ChatContent.builder()                      //채팅방 참여 메세지
                .departmentId(departmentId)
                .email(null)
                .content(memberName + "님이 채팅방에 참여하였습니다.")
                .date(getLocalDateTime())
                .contentType("-1")
                .link(null)
                .build();

        chatContentRepository.save(chatContent);
    }

    @PostMapping("workspace/create")
    public ResponseEntity create(@RequestBody WorkspaceCreation workspaceCreation){
        Workspace workspace = workspaceCreation.getWorkspace();   //생성할 워크스페이스 정보
        List<WorkspaceMember> workspaceMemberList = workspaceCreation.getWorkspaceMemberList();  //추가할 회원 목록
        WorkspaceMember workspaceMember;
        DepartmentMember departmentMember;
        String departmentId;
        StringBuilder listOfInvitedMemberNames = new StringBuilder();

        boolean isWorkspace = workspaceRepository.existsById(workspace.getWorkspaceId());
        String email = workspaceMemberList.get(0).getEmail();   //워크스페이스 생성자의 정보

        if(isWorkspace){
            return ResponseEntity.badRequest().build();
        }

        workspace.setWorkspaceId(getTableId());
        workspaceRepository.save(workspace);        //워크스페이스 생성

        departmentId = workspace.getWorkspaceId();          //공지방 아이디 = 워크스페이스 아이디
        Department department = new Department(departmentId, workspace.getWorkspaceId(),
                "\uD83D\uDCE2 공지방", null, null);

        departmentService.create(department);      //기본 공지방 생성

        for(int index = 0; index < workspaceMemberList.size(); index++){    //워크스페이스에 회원목록 추가
            workspaceMember = workspaceMemberList.get(index);
            workspaceMember.setWorkspaceId(workspace.getWorkspaceId());              //생성한 워크스페이스 아이디 추가
            workspaceMember.setMemberName(memberRepository.findByEmail(workspaceMember.getEmail()).getMemberName());   //회원 이름

            workspaceMemberRepository.save(workspaceMember);     //워크스페이스회원 목록에 회원 추가

            departmentMember = new DepartmentMember(departmentId,
                    workspaceMember.getEmail(), workspaceMember.getMemberName(),null, null);
            departmentMemberRepository.save(departmentMember);  //생성된 공지방에 회원정보 추가

            listOfInvitedMemberNames.append(departmentMember.getMemberName());   //공지방에 초대받은 회원 이름 목록
            if(index - 1 < workspaceMemberList.size()){
                listOfInvitedMemberNames.append(", ");
            }
        }

        listOfInvitedMemberNames.append("님이 채팅방에 참여하였습니다.");
        saveMemberEntryMesaage(departmentId, listOfInvitedMemberNames.toString());  //DB에 공지방에 초대받은 회원의 입장 메세지 저장

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
