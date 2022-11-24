package com.twopark1jo.lobster.workspace;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "workspace")
@Table(name = "workspace")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Workspace {
    @Id
    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "workspace_name")
    private String workspaceName;

    @Column(name = "workspace_goal")
    private String workspaceGoal;

    @Column(name = "workspace_deadline")
    private String workspaceDeadline;

    @Column(name = "creation_date")
    private String creationDate;
}
