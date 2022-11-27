package com.twopark1jo.lobster.department.department;

import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.member.Member;

import java.util.List;

public interface DepartmentService {

    boolean create(Department department);

    boolean update(Department department);

    void addDepartmentMember(DepartmentMember member);

    List<Department> getDepartmentList();

    List<Department> getDepartmentListByWorkspace(String workspaceId);

    List<Department> getDepartmentListByMember(String workspaceId, String email);

    boolean isExistingDepartment(String departmentId);

    boolean isDepartmentNameInWorkspace(String workspaceId, String departmentName);

    boolean isExistingMember(String departmentId, String email);
}
