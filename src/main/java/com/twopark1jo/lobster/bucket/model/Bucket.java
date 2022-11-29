package com.twopark1jo.lobster.bucket.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "bucket")
@Table(name = "bucket")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Bucket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_id")
    private String commitId;

    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "date")
    private String date;

    @Column(name = "email")
    private String email;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "title")
    private String title;

    @Column(name = "commit")
    private String commit;

    @Column(name = "file_link1")
    private String fileLink1;

    @Column(name = "file_link2")
    private String fileLink2;

    @Column(name = "file_link3")
    private String fileLink3;

    @Column(name = "file_link4")
    private String fileLink4;

    @Column(name = "file_link5")
    private String fileLink5;

    @Column(name = "bucket_progress")
    private String bucketProgress;
}
