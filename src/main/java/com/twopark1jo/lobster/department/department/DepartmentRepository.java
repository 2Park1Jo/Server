package com.twopark1jo.lobster.department.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query(value = "SELECT * FROM department WHERE workspace_id=:workspaceId;", nativeQuery = true)
    List<Department> findAllByWorkspaceId(String workspaceId);
}