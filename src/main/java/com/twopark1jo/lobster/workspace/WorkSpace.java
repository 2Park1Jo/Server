package com.twopark1jo.lobster.workspace;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "workspace")
@Table(name = "workspace")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class WorkSpace {
    @Id
    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "workspace_name")
    private String workspaceName;

    @Column(name = "goal")
    private String goal;

    @Column(name = "deadline")
    private String deadline;

    public WorkSpace(String workspaceId, String workspaceName, String goal, String deadline) {
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.goal = goal;
        this.deadline = deadline;
    }
}
