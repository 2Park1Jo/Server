package com.twopark1jo.lobster.department.department.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    public List<Member> findAllByDepartmentId(String departmentId);
}
