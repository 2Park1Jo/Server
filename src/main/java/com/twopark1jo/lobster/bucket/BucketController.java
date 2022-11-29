package com.twopark1jo.lobster.bucket;

import com.twopark1jo.lobster.bucket.model.Bucket;
import com.twopark1jo.lobster.bucket.model.BucketByDepartment;
import com.twopark1jo.lobster.bucket.model.BucketByMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BucketController{

    private final BucketServiceImpl bucketService;

    //버킷 커밋 생성
    @PostMapping("/workspace/department/bucket/create")
    public ResponseEntity create(@RequestBody Bucket bucket) {
        if(bucketService.create(bucket)){
            return new ResponseEntity(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //워크스페이스의 모든 부서의 가장 마지막 버킷 내역
    @GetMapping("/workspace/{workspaceId}/departments/last-bucket-history")
    public ResponseEntity<List<Bucket>> getLastBucketHistoryByWorkspace(@PathVariable String workspaceId) {
        List<Bucket> bucketHistoryList = bucketService.getLastBucketHistoryByWorkspace(workspaceId);

        if(bucketService.isExistingWorkspace(workspaceId)){
            return new ResponseEntity<>(bucketHistoryList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //부서의 모든 버킷 내역
    @GetMapping("/workspace/department/{departmentId}/bucket-history")
    public ResponseEntity<List<Bucket>> getBucketHistoryByDepartment(@PathVariable String departmentId) {
        List<Bucket> bucketHistoryList = bucketService.getBucketHistoryByDepartment(departmentId);

        if(bucketService.isExistingDepartment(departmentId)){
            return new ResponseEntity<>(bucketHistoryList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //부서의 가장 마지막 버킷 내역
    @GetMapping("/workspace/department/{departmentId}/last-bucket-history")
    public Bucket getLastBucketHistoryByDepartment(@PathVariable String departmentId) {
        return bucketService.getLastBucketHistoryByDepartment(departmentId);
    }

    //버킷 최신화를 가장 많이 한 상위 3명의 회원
    @GetMapping("/workspace/{workspaceId}/bucket/member/top-three-updates")
    public List<BucketByMember> getListOfThreeMostCommittedBucket(@PathVariable String workspaceId){
        List<String> bucketCommitList = bucketService.getListOfThreeMostCommittedBucket(workspaceId);
        List<BucketByMember> bucketByMemberList = new ArrayList<>();
        String bucketCommit[];
        int size = bucketCommitList.size();

        for(int index = 0; index < size; index++){
            //[0] : 이메일, [1] : 회원 이름, [2] : 채팅수
            bucketCommit = bucketCommitList.get(index).split(",");
            bucketByMemberList.add(new BucketByMember(bucketCommit[0], bucketCommit[1], bucketCommit[2]));
        }

        return bucketByMemberList;
    }

    //버킷 최신화를 가장 많이한 부서
    @GetMapping("/workspace/{workspaceId}/bucket/department/commit-count")
    public List<BucketByDepartment> getTopThreeDepartmentWithMostBucketUpdate(@PathVariable String workspaceId){
        List<String> bucketCommitList = bucketService.getTopThreeDepartmentWithMostBucketUpdate(workspaceId);
        List<BucketByDepartment> BucketByDepartmentList = new ArrayList<>();
        String bucketCommit[];
        int size = bucketCommitList.size();

        for(int index = 0; index < size; index++){
            //[0] : 부서 아이디, [1] : 부서명, [2] : 채팅수
            bucketCommit = bucketCommitList.get(index).split(",");
            BucketByDepartmentList.add(new BucketByDepartment(bucketCommit[0], bucketCommit[1], bucketCommit[2]));
        }

        return BucketByDepartmentList;
    }
}
