package com.twopark1jo.lobster.member;

import com.twopark1jo.lobster.department.department.DepartmentRepository;
import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.department.department.member.DepartmentMemberRepository;
import com.twopark1jo.lobster.utility.Constants;
import com.twopark1jo.lobster.workspace.member.WorkspaceMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    final private MemberRepository memberRepository;
    final private DepartmentRepository departmentRepository;
    final private DepartmentMemberRepository departmentMemberRepository;

    @Override
    public void signUp() {

    }

    @Override
    public void singIn() {

    }

    @Override
    public Member getMemberProfile(String email) {
        return null;
    }

    @Override
    public List<DepartmentMember> getMemberListByDepartment(String departmentId) {
        boolean isDepartment = departmentRepository.existsById(departmentId);

        if (isDepartment == !Constants.IS_EXISTING_DEPARTMENT) {
            return null;
        }

        return departmentMemberRepository.findAllByDepartmentId(departmentId);
    }

    @Override
    public List<Member> getMemberListByWorkspace(String workspaceId) {
        return null;
    }

    @Override
    public boolean isExistingMember(String email) {
        return false;
    }

    @Override
    public boolean addToDepartment(String departmentId, List<DepartmentMember> memberList) {
        boolean isDepartment = departmentRepository.existsById(departmentId);
        DepartmentMember member;

        if (isDepartment == !Constants.IS_EXISTING_DEPARTMENT) {   //존재하지 않는 부서일 경우
            return !Constants.IS_EXISTING_DEPARTMENT;
        }

        for (int index = 0; index < memberList.size(); index++) {
            member = memberList.get(index);
            //부서에 이미 소속된 회원정보는 저장X
            if (departmentMemberRepository.existsByDepartmentIdAndEmail(departmentId, member.getEmail())) {
                continue;
            }

            member.setDepartmentId(departmentId);
            departmentMemberRepository.save(member);
        }

        return Constants.IS_DATA_SAVED_SUCCESSFULLY;
    }

    @Override
    public void addToWorkspace(WorkspaceMember member) {

    }
}
