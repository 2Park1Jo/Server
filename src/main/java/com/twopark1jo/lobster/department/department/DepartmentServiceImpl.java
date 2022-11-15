package com.twopark1jo.lobster.department.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    final private DepartmentRepository departmentRepository;

    @Override
    public void create(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public List<Department> getDepartmentList() {
        return  departmentRepository.findAll();
    }

    @Override
    public List<Department> getDepartmentListByWorkspace(String workspaceId) {
        return departmentRepository.findAllByWorkspaceId(workspaceId);
    }

    @Override
    public List<Department> getDepartmentListByMember(String workspaceId, String email) {
        return departmentRepository.findDepartmentListByMember(workspaceId, email);
    }

    @Override
    public boolean isExistingDepartment(String departmentId) {
        return departmentRepository.existsById(departmentId);
    }

    @Override
    public boolean isDepartmentNameInWorkspace(String workspaceId, String departmentName) {
        return departmentRepository.existsByWorkspaceIdAndDepartmentName(workspaceId, departmentName);
    }

    @Override
    public boolean isExistingMember(String email) {
        return false;
    }
}
