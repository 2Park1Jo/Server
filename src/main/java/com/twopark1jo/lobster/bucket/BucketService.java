package com.twopark1jo.lobster.bucket;


import java.util.List;

public interface BucketService {

    boolean create(Bucket bucket);

    List<Bucket> getLastBucketHistoryByWorkspace(String workspaceId);

    List<Bucket> getBucketHistoryByDepartment(String departmentId);

    Bucket getLastBucketHistoryByDepartment(String departmentId);
}
