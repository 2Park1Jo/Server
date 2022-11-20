package com.twopark1jo.lobster.bucket.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BucketFileRepository extends JpaRepository<BucketFile, String> {

    List<BucketFile> findAllByCommitId(String commitId);
}
