package com.twopark1jo.lobster.workspace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberPK implements Serializable {
    private String memberId;
    private String workspaceId;
}
