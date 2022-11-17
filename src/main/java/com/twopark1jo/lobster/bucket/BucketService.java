package com.twopark1jo.lobster.bucket;

import com.twopark1jo.lobster.bucket.history.BucketHistory;

import java.util.List;

public interface BucketService {

    void create(Bucket bucket);

    void addToBucketHistory(BucketHistory bucketHistory);

    List<Bucket> getBucketList();

    List<BucketHistory> getBucketHistoryByDepartment(String departmentId);

}
