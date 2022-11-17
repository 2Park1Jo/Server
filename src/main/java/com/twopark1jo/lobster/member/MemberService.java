package com.twopark1jo.lobster.member;

import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.workspace.member.WorkspaceMember;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {

    boolean signUp(Member member);

    boolean login(Member member);

    void logout(Member member);

    boolean duplicateId(String email);

    Member getMemberProfile(String email);
    List<Member> getAllMemberList();

    List<DepartmentMember> getMemberListByDepartment(String departmentId);

    List<WorkspaceMember> getMemberListByWorkspace(String workspaceId);

    boolean isExistingMember(String email);

    boolean addToDepartment(List<DepartmentMember> memberList);

    void addToWorkspace(WorkspaceMember member);

    void addToNoticeBoard(List<WorkspaceMember> workspaceMemberList);
}
