package com.twopark1jo.lobster.workspace;

import lombok.*;

import javax.persistence.*;

@Entity(name = "workspace_member")
@Table(name = "workspace_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
//@IdClass(WorkspaceMemberPK.class)
public class WorkspaceMember {
    @Id
    @Column(name = "email")
    private String email;

    //@Id
    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "grade")
    private String grade;
}
