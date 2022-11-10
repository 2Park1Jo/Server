package com.twopark1jo.lobster.department.department;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "department")
@Table(name = "department")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Department {
    @Id
    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_goal")
    private String departmentGoal;

    @Column(name = "department_deadline")
    private String departmentDeadline;
}
