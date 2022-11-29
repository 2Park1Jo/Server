package com.twopark1jo.lobster.bucket;

import com.twopark1jo.lobster.bucket.model.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BucketRepository extends JpaRepository<Bucket, String> {

    @Query(value =
            "SELECT * FROM bucket " +
            "WHERE department_id=:department_id " +
            "ORDER BY date DESC LIMIT 1", nativeQuery = true)
    Bucket findLastBucketHistoryByDepatmentId(@Param("department_id") String departmentId);

    List<Bucket> findAllByDepartmentId(String departmentId);

    boolean existsByDepartmentId(String departmentId);

    boolean existsByWorkspaceId(String workspaceId);

    @Query(value =
            "SELECT * FROM bucket " +
            "WHERE workspace_id=:workspace_id AND date IN(" +
                "SELECT MAX(date) AS date FROM bucket " +
                "GROUP BY department_id" +
            ")" , nativeQuery = true)
    List<Bucket> findAllBucketHistoryByWorkspace(@Param("workspace_id") String workspaceId);

    @Query(value =
            "SELECT email, member_name, count(*) FROM bucket " +
            "WHERE workspace_id=:workspace_id " +
            "GROUP BY email " +
            "ORDER BY count(*) DESC LIMIT 3", nativeQuery = true)
    List<String> getTopThreeMemberWithMostBucketUpdate(@Param("workspace_id") String workspaceId);

    @Query(value =
            "SELECT department_id, (SELECT department_name FROM department WHERE bucket.department_id=department.department_id) " +
            "AS department_name, COUNT(*) FROM bucket WHERE workspace_id=:workspace_id " +
            "GROUP BY department_id ORDER BY COUNT(*) DESC LIMIT 3", nativeQuery = true)
    List<String> getTopThreeDepartmentWithMostBucketUpdate(@Param("workspace_id") String workspaceId);
}