package com.twopark1jo.lobster.member;

import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.workspace.member.WorkspaceMember;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {

    void signUp();

    void singIn();

    Member getMemberProfile(String email);

    List<DepartmentMember> getMemberListByDepartment(String departmentId);

    List<Member> getMemberListByWorkspace(String workspaceId);

    boolean isExistingMember(String email);

    boolean addToDepartment(String departmentId, List<DepartmentMember> memberList);

    void addToWorkspace(WorkspaceMember member);
}
