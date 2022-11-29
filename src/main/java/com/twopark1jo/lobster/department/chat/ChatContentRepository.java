package com.twopark1jo.lobster.department.chat;

import com.twopark1jo.lobster.department.chat.model.ChatContent;
import com.twopark1jo.lobster.department.chat.model.TopThreeChats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatContentRepository extends JpaRepository<ChatContent, String> {
    public List<ChatContent> findAllByDepartmentId(String departmentId);

    @Query(value =
            "SELECT department_id, COUNT(chat_id) " +
            "FROM chat_content WHERE chat_content.department_id IN" +
            "(SELECT department.department_id FROM department LEFT JOIN department_member " +
            "ON department.department_id=department_member.department_id " +
            "WHERE department.workspace_id=:workspace_id AND email=:email) " +
            "GROUP BY department_id", nativeQuery = true)
    public List<String> findDepartmentIdAndChatCountByWorkspace(
            @Param("workspace_id") String workspaceId, @Param("email") String email);

    @Query(value = "SELECT COUNT(chat_id) FROM chat_content WHERE department_id=:department_id AND email IS NOT NULL", nativeQuery = true)
    public String getMessageCount(@Param("department_id") String departmentId);

    @Query(value =
            "SELECT chat_content.email, member_name, COUNT(chat_id) " +
            "FROM chat_content JOIN department_member " +
            "ON chat_content.email=department_member.email AND chat_content.department_id=department_member.department_id " +
            "WHERE chat_content.department_id " +
            "IN(SELECT department_id FROM department WHERE workspace_id=:workspace_id) " +
            "GROUP BY email ORDER BY COUNT(chat_id) DESC LIMIT 3"
            , nativeQuery = true)
    public List<String> getListOfThreeMostChattedPeople(@Param("workspace_id") String workspaceId);

    @Query(value =
            "SELECT email, department_name " +
            "FROM department JOIN department_member " +
            "ON department.department_id=department_member.department_id " +
            "WHERE workspace_id=:workspace_id", nativeQuery = true)
    public List<String> getDepartmentNameListByWorkspace(@Param("workspace_id") String workspaceId);
}
