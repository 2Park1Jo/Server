package com.twopark1jo.lobster.department.chat;

import com.twopark1jo.lobster.department.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatContentRepository extends JpaRepository<ChatContent, String> {
    public List<ChatContent> findAllByDepartmentId(String departmentId);
}
