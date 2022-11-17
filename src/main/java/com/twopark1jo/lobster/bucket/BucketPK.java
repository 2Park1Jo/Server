package com.twopark1jo.lobster.bucket;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class BucketPK implements Serializable {
    @Column(name = "bucket_id")
    private String bucketId;

    @Column(name = "department_id")
    private String departmentId;
}
