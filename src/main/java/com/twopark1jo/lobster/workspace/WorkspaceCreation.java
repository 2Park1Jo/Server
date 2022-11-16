package com.twopark1jo.lobster.workspace;

import com.twopark1jo.lobster.workspace.member.WorkspaceMember;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceCreation {
    private Workspace workspace;

    private List<WorkspaceMember> workspaceMemberList;
}
