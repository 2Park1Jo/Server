package com.twopark1jo.lobster.department.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    List<Department> findAllByWorkspaceId(String workspaceId);

    @Query(value = "SELECT * FROM department d LEFT JOIN department_member dm " +
            "ON d.department_id=dm.department_id " +
            "WHERE d.workspace_id=:workspace_id " +
            "AND dm.email=:email", nativeQuery = true)
    List<Department> findDepartmentListByMember(
            @Param("workspace_id") String workspaceId, @Param("email") String email);

    boolean existsByWorkspaceIdAndDepartmentName(String workspaceId, String departmentName);
}