package com.twopark1jo.lobster.department.chat;

import com.twopark1jo.lobster.department.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatContentRepository extends JpaRepository<ChatContent, String> {
    public List<ChatContent> findAllByDepartmentId(String departmentId);

    @Query(value = "SELECT COUNT(chat_id) FROM chat_content where department_id=:department_id", nativeQuery = true)
    public String getMessageCount(@Param("department_id") String departmentId);
}
