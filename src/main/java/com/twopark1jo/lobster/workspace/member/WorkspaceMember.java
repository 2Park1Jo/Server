package com.twopark1jo.lobster.workspace.member;

import lombok.*;

import javax.persistence.*;

@Entity(name = "workspace_member")
@Table(name = "workspace_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
@IdClass(WorkspaceMemberPK.class)
public class WorkspaceMember {
    @Id
    @Column(name = "email")
    private String email;

    @Id
    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "workspace_grade")
    private String workspaceGrade;
}
