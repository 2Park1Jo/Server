package com.twopark1jo.lobster.workspace.member;

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
    @Column(name = "email")
    private String email;

    @Column(name = "workspace_id")
    private String workspaceId;
}
