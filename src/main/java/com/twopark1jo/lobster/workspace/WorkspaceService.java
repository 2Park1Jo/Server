package com.twopark1jo.lobster.workspace;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WorkspaceService {

    boolean create(Workspace workspace);
    boolean isExistingWorkspace(String workspaceId);

    Workspace getWorkspace(String workspaceId);

    List<Workspace> getAllWorkspace();

}
