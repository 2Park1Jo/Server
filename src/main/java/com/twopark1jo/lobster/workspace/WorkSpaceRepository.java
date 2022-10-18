package com.twopark1jo.lobster.workspace;

import com.twopark1jo.lobster.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace, String> {

    //워크스페이스 정보 조회
    @Query(value = "SELECT * FROM workspace w WHERE w.workspace_id=:workspace_id", nativeQuery = true)
    public WorkSpace getWorkSpace(@Param("workspace_id") String workspaceId);




}
