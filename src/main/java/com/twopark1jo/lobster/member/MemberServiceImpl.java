package com.twopark1jo.lobster.member;

import com.twopark1jo.lobster.department.department.DepartmentRepository;
import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.department.department.member.DepartmentMemberRepository;
import com.twopark1jo.lobster.exception.ErrorCode;
import com.twopark1jo.lobster.exception.MemberException;
import com.twopark1jo.lobster.utility.Constants;
import com.twopark1jo.lobster.workspace.member.WorkspaceMember;
import com.twopark1jo.lobster.workspace.member.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    final private MemberRepository memberRepository;
    final private DepartmentRepository departmentRepository;
    final private DepartmentMemberRepository departmentMemberRepository;
    final private WorkspaceMemberRepository workspaceMemberRepository;

    @Override
    public boolean signUp(Member member) {
        boolean isMember = memberRepository.existsById(member.getEmail());

        if(isMember){
            return !Constants.IS_DATA_SAVED_SUCCESSFULLY;
        }

        memberRepository.save(member);
        return Constants.IS_DATA_SAVED_SUCCESSFULLY;
    }

    @Override
    public boolean login(Member member) {
        int isMember = memberRepository.checkLogin(member.getEmail(), member.getPassword());

        if(isMember == Constants.IS_MEMBER){
            return Constants.IS_LOGIN_SUCCESSFULL;
        }

        return !Constants.IS_LOGIN_SUCCESSFULL;
    }

    @Override
    public void logout(Member member) {

    }

    @Override
    public boolean duplicateId(String email) {
        return memberRepository.existsById(email);
    }

    @Override
    public Member getMemberProfile(String email) {
        boolean isMember = memberRepository.existsById(email);

        if(isMember){
            return memberRepository.findByEmail(email);
        }

        return null;
    }

    @Override
    public List<Member> getAllMemberList() {
        return memberRepository.findAll();
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
    public List<WorkspaceMember> getMemberListByWorkspace(String workspaceId) {
        return workspaceMemberRepository.findAllByWorkspaceId(workspaceId);
    }

    @Override
    public boolean isExistingMember(String email) {
        return memberRepository.existsById(email);
    }

    @Override
    public boolean addToDepartment(List<DepartmentMember> memberList) {
        String departmentId = memberList.get(0).getDepartmentId(); //부서 아이디
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
            departmentMemberRepository.save(member);   //생성된 부서에 부서회원 정보 저장
        }

        return Constants.IS_DATA_SAVED_SUCCESSFULLY;
    }

    @Override
    public void addToWorkspace(WorkspaceMember member) {

    }
}
