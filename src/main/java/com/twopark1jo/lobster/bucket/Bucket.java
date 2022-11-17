package com.twopark1jo.lobster.bucket;

import lombok.*;

import javax.persistence.*;

@Entity(name = "bucket")
@Table(name = "bucket")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@IdClass(BucketPK.class)
public class Bucket {
    @Id
    @Column(name = "bucket_id")
    private String bucketId;

    @Id
    @Column(name = "department_id")
    private String departmentId;
}
