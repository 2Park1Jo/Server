package com.twopark1jo.lobster.bucket.file;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "bucket_file")
@Table(name = "bucket_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class BucketFile {

    @Id
    @Column(name = "file_id")
    private String fileId;

    @Column(name = "commit_id")
    private String commitId;

    @Column(name = "file_link")
    private String fileLink;

    @Column(name = "file_name")
    private String fileName;

}
