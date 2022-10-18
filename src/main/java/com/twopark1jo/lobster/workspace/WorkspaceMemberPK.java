package com.twopark1jo.lobster.workspace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class WorkspaceMemberPK implements Serializable {
    @Column(name = "email")
    private String email;

    @Column(name = "workspace_id")
    private String workspaceId;
}
