package com.twopark1jo.lobster.bucket.file;

import java.util.List;

public interface BucketFileService {

    void create(BucketFile bucketFile);

    List<BucketFile> getBucketFileByCommitId(String commitId);
}
