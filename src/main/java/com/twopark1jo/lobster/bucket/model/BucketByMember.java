package com.twopark1jo.lobster.bucket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BucketByMember {

    private String email;

    private String memnberName;

    private String commitCount;
}
