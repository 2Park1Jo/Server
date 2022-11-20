package com.twopark1jo.lobster.bucket;

import com.twopark1jo.lobster.bucket.history.BucketHistory;
import com.twopark1jo.lobster.bucket.history.BucketHistoryRepository;
import com.twopark1jo.lobster.department.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl {//implements BucketService{

    /*final private BucketRepository bucketRepository;
    final private BucketHistoryRepository bucketHistoryRepository;

    private String getBucketId(){
        LocalDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().withNano(0);
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        return date.format(myPattern);
    }

    //버킷 생성
    @Override
    public void create(Bucket bucket) {
        bucket.setBucketId(getBucketId());
        bucketRepository.save(bucket);
    }

    //버킷 커밋 생성
    @Override
    public void addToBucketHistory(BucketHistory bucketHistory) {
        bucketHistory.setBucketId(getBucketId());
        bucketHistoryRepository.save(bucketHistory);
    }

    //모든 버킷 목록
    @Override
    public List<Bucket> getBucketList() {
        return bucketRepository.findAll();
    }

    //버킷 커밋내역
    @Override
    public List<BucketHistory> getBucketHistoryByDepartment(String departmentId) {
        return bucketHistoryRepository.findAllByDepartmentId(departmentId);
    }*/

}
