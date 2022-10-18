package com.twopark1jo.lobster.workspace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkspaceRepository extends JpaRepository<Workspace, String> {

    //워크스페이스 정보 조회
    @Query(value = "SELECT * FROM workspace w WHERE w.workspace_id=:workspace_id", nativeQuery = true)
    public Workspace getWorkSpace(@Param("workspace_id") String workspaceId);

}
