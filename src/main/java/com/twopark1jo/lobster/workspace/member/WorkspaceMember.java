package com.twopark1jo.lobster.workspace.member;

import lombok.*;

import javax.persistence.*;

@Entity(name = "workspace_member")
@Table(name = "workspace_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@IdClass(WorkspaceMemberPK.class)
public class WorkspaceMember {
    @Id
    @Column(name = "workspace_id")
    private String workspaceId;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "workspace_grade")
    private String workspaceGrade;
}
