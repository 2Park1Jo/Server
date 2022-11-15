package com.twopark1jo.lobster.department.department;

import java.util.List;

public interface DepartmentService {

    void create(Department department);

    List<Department> getDepartmentList();

    List<Department> getDepartmentListByWorkspace(String workspaceId);

    List<Department> getDepartmentListByMember(String workspaceId, String email);

    boolean isExistingDepartment(String departmentId);

    boolean isDepartmentNameInWorkspace(String workspaceId, String departmentName);

    boolean isExistingMember(String email);
}
