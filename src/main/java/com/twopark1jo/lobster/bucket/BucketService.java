package com.twopark1jo.lobster.bucket;


import com.twopark1jo.lobster.bucket.model.BucketDAO;

import java.util.List;

public interface BucketService {

    boolean create(BucketDAO bucketDAO);

    List<BucketDAO> getLastBucketHistoryByWorkspace(String workspaceId);

    List<BucketDAO> getBucketHistoryByDepartment(String departmentId);

    BucketDAO getLastBucketHistoryByDepartment(String departmentId);
}
