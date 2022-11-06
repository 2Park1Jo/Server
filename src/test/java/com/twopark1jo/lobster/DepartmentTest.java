package com.twopark1jo.lobster;

import com.twopark1jo.lobster.department.department.Department;
import com.twopark1jo.lobster.department.department.DepartmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DepartmentTest extends BaseControllerTest{

    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("부서 생성 성공")
    void insertPostSuccess() throws Exception{
        String departmentId = "department555";
        String workspaceId = "12345";
        String departmentName = "파스타먹자";
        String goal = "토마토파스타 먹자";
        String deadline = "2022-11-15 23:59:59";

        //Given
        Department department = new Department(departmentId, workspaceId,
                departmentName, goal, deadline);
        departmentRepository.save(department);

        //When
        ResultActions resultActions = this.mockMvc.perform(post("/workspace/" + workspaceId + "/chat/departemnt/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(department))
        );

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("departmentId").value(departmentId))
                .andExpect(jsonPath("workspaceId").value(workspaceId))
                .andExpect(jsonPath("departmentName").value(departmentName))
                .andExpect(jsonPath("goal").value(goal))
                .andExpect(jsonPath("deadline").value(deadline));
    }
}
