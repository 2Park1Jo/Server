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
@Getter
@Setter
@ToString
public class Department {
    @Id
    @Column(name = "department_id")
    private String departmentId;
    @Column(name = "workspace_id")
    @NotBlank
    private String workspaceId;
    @Column(name = "department_name")
    @NotBlank
    private String departmentName;
    @Column(name = "goal")
    @NotBlank
    private String goal;
    @Column(name = "deadline")
    @NotBlank
    private String deadline;

    @Transient
    private Set<WebSocketSession> sessions = new HashSet<>();

    public Department(String departmentId, String workspaceId, String departmentName, String goal, String deadline) {
        this.departmentId = departmentId;
        this.workspaceId = workspaceId;
        this.departmentName = departmentName;
        this.goal = goal;
        this.deadline = deadline;
    }
}
