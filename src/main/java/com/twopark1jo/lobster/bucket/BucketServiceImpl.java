package com.twopark1jo.lobster.bucket;

import com.twopark1jo.lobster.bucket.model.BucketDAO;
import com.twopark1jo.lobster.department.department.DepartmentRepository;
import com.twopark1jo.lobster.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService{

    final private BucketRepository bucketRepository;
    final private DepartmentRepository departmentRepository;

    //버킷 생성
    @Override
    public boolean create(BucketDAO bucketDAO) {
        boolean isExistingDepartment = departmentRepository.existsById(bucketDAO.getDepartmentId());

        if (isExistingDepartment) {
            bucketRepository.save(bucketDAO);
            return Constants.IS_DATA_SAVED_SUCCESSFULLY;
        }

        return !Constants.IS_DATA_SAVED_SUCCESSFULLY;
    }

    //워크스페이스의 모든 부서의 가장 마지막 버킷내역
    @Override
    public List<BucketDAO> getLastBucketHistoryByWorkspace(String workspaceId) {
        //return bucketRepository.findAllBucketHistoryByWorkspace(workspaceId);
        return null;
    }

    //부서의 모든 버킷 내역
    @Override
    public List<BucketDAO> getBucketHistoryByDepartment(String departmentId) {
        return bucketRepository.findAllByDepartmentId(departmentId);
    }

    //부서의 가장 마지막 버킷 내역
    @Override
    public BucketDAO getLastBucketHistoryByDepartment(String departmentId) {
        return bucketRepository.findLastBucketHistoryByDepatmentId(departmentId);
    }


    boolean isExistingDepartment(String departmentId){
        if(bucketRepository.existsByDepartmentId(departmentId)){
            return Constants.IS_EXISTING_DEPARTMENT;
        }

        return !Constants.IS_EXISTING_DEPARTMENT;
    }

    boolean isExistingWorkspace(String workspaceId){
        if(bucketRepository.existsByWorkspaceId(workspaceId)){
            return Constants.IS_EXISTING_WORKSPACE;
        }

        return !Constants.IS_EXISTING_WORKSPACE;
    }
}
