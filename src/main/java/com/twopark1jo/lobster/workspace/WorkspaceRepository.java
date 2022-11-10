package com.twopark1jo.lobster.workspace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, String> {

    @Query(value = "SELECT * FROM workspace w " +
            "LEFT JOIN workspace_member wm " +
            "ON w.workspace_id = wm.workspace_id " +
            "WHERE wm.email=:email", nativeQuery = true)
    List<Workspace> findAllByWorkspaceMember(@Param("email") String email);

}
