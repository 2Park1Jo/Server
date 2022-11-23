package com.twopark1jo.lobster.bucket;

import com.twopark1jo.lobster.bucket.model.BucketDAO;
import com.twopark1jo.lobster.bucket.model.BucketDTO;
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
    public ResponseEntity create(@RequestBody BucketDAO bucketDAO) {
        if(bucketService.create(bucketDAO)){
            return new ResponseEntity(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //워크스페이스의 모든 부서의 가장 마지막 버킷 내역
    @GetMapping("/workspace/{workspaceId}/departments/last-bucket-history")
    public ResponseEntity<List<BucketDAO>> getLastBucketHistoryByWorkspace(@PathVariable String workspaceId) {
        List<BucketDAO> bucketHistoryList = bucketService.getLastBucketHistoryByWorkspace(workspaceId);

        if(bucketService.isExistingWorkspace(workspaceId)){
            return new ResponseEntity<>(bucketHistoryList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //부서의 모든 버킷 내역
    @GetMapping("/workspace/department/{departmentId}/bucket-history")
    public ResponseEntity<List<BucketDAO>> getBucketHistoryByDepartment(@PathVariable String departmentId) {
        List<BucketDAO> bucketDAOHistoryList = bucketService.getBucketHistoryByDepartment(departmentId);

        if(bucketService.isExistingDepartment(departmentId)){
            return new ResponseEntity<>(bucketDAOHistoryList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //부서의 가장 마지막 버킷 내역
    @GetMapping("/workspace/department/{departmentId}/last-bucket-history")
    public BucketDAO getLastBucketHistoryByDepartment(@PathVariable String departmentId) {
        return bucketService.getLastBucketHistoryByDepartment(departmentId);
    }
}
