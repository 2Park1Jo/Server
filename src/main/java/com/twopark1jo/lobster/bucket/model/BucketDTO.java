package com.twopark1jo.lobster.bucket.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BucketDTO {
    private String commitId;

    private String departmentId;

    private String workspaceId;

    private String date;

    private String email;

    private String memberName;

    private String title;

    private String commit;

    private List<String> fileLink;
}
