package com.twopark1jo.lobster.workspace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class WorkspaceMemberPK implements Serializable {
    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "email")
    private String email;

    @Builder
    public WorkspaceMemberPK(String workspaceId, String email) {
        this.workspaceId = workspaceId;
        this.email = email;
    }
}
