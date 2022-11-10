package com.twopark1jo.lobster.workspace.member;

import com.twopark1jo.lobster.workspace.member.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, String> {

    Optional<WorkspaceMember> findByWorkspaceIdAndEmail (String workspaceId, String email);

    List<WorkspaceMember> findAllByEmail (String email);

    List<WorkspaceMember> findAllByWorkspaceId(String workspaceId);
}
