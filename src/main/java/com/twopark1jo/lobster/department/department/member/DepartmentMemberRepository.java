package com.twopark1jo.lobster.department.department.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentMemberRepository extends JpaRepository<DepartmentMember, String> {
    public List<DepartmentMember> findAllByDepartmentId(String departmentId);

    boolean existsByDepartmentIdAndEmail(String departmentId, String email);
}
