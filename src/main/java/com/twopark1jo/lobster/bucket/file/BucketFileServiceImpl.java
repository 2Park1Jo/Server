package com.twopark1jo.lobster.bucket.file;

import com.twopark1jo.lobster.bucket.BucketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketFileServiceImpl implements BucketFileService{
    final private BucketFileRepository bucketFileRepository;

    @Override
    public void create(BucketFile bucketFile) {
        bucketFileRepository.save(bucketFile);
    }

    @Override
    public List<BucketFile> getBucketFileByCommitId(String commitId) {
        return bucketFileRepository.findAllByCommitId(commitId);
    }
}
