package com.twopark1jo.lobster.department.chat.lastchat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LastChatContentRepository extends JpaRepository<LastChatContent, String> {
    List<LastChatContent> findAllByEmailAndWorkspaceId(String email, String workspace);

    boolean existsByEmailAndDepartmentId(String email, String departmentId);
}
