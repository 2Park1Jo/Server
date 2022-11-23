package com.twopark1jo.lobster.bucket;

import com.twopark1jo.lobster.bucket.model.BucketDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BucketRepository extends JpaRepository<BucketDAO, String> {

    @Query(value =
            "SELECT * FROM bucket " +
            "WHERE department_id=:department_id " +
            "ORDER BY date DESC LIMIT 1", nativeQuery = true)
    BucketDAO findLastBucketHistoryByDepatmentId(@Param("department_id") String departmentId);

    List<BucketDAO> findAllByDepartmentId(String departmentId);

    boolean existsByDepartmentId(String departmentId);

    boolean existsByWorkspaceId(String workspaceId);

    /*@Query(value = "SELECT * FROM(" +
                    "SELECT * FROM bucket " +
            "WHERE workspace_id=:workspace_id AND date IN(
            select max(date) as date
            from bucket group by department_id
    )
            order by date desc
)bucket_list group by bucket_list.department_id",
    List<BucketDAO> findAllBucketHistoryByWorkspace(@Param("") String workspaceId);*/
}