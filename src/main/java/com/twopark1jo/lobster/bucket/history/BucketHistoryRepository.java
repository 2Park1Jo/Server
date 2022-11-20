package com.twopark1jo.lobster.bucket.history;

import com.twopark1jo.lobster.bucket.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BucketHistoryRepository extends JpaRepository<BucketHistory, String> {

}
