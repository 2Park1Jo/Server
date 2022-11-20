package com.twopark1jo.lobster.bucket.history;

import com.twopark1jo.lobster.workspace.member.WorkspaceMemberPK;
import lombok.*;

import javax.persistence.*;

@Entity(name = "bucket_history")
@Table(name = "bucket_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class BucketHistory {
    @Id
    @Column(name = "commit_id")
    private String commitId;

    @Column(name = "bucket_id")
    private String bucketId;

    @Column(name = "date")
    private String date;

    @Column(name = "commit")
    private String commit;
}
