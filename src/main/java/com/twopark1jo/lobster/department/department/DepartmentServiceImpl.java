package com.twopark1jo.lobster.department.department;

import com.twopark1jo.lobster.bucket.Bucket;
import com.twopark1jo.lobster.bucket.BucketRepository;
import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import com.twopark1jo.lobster.department.department.member.DepartmentMemberRepository;
import com.twopark1jo.lobster.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    final private DepartmentRepository departmentRepository;
    final private DepartmentMemberRepository departmentMemberRepository;
    final private BucketRepository bucketRepository;

    private String getLocalDateTime(){
        LocalDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime().withNano(0);
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return date.format(myPattern);
    }

    private String getDepartmentId(String workspaceId){
        String date = getLocalDateTime();
        return workspaceId.substring(0, 3) + date;
    }

    //부서회원 DB에 회원정보 추가
    @Override
    public void addDepartmentMember(DepartmentMember member) {
        if(departmentMemberRepository.existsByDepartmentIdAndEmail(member.getDepartmentId(), member.getEmail())){
            departmentMemberRepository.save(new DepartmentMember(
                    member.getDepartmentId(), member.getEmail(), member.getMemberName(),
                    member.getDepartmentRole(), member.getDepartmentGrade()));   //공지방에 회원정보 추가
        }//부서에 이미 존재하는 회원일 경우 저장X
    }

    //부서생성
    @Override
    public boolean create(Department department) {
        String departmentId = getDepartmentId(department.getWorkspaceId());

        boolean isDuplicatedDepartmentName = isDepartmentNameInWorkspace(
                department.getWorkspaceId(), department.getDepartmentName());

        if(isDuplicatedDepartmentName){                //같은 워크스페이스에 중복 이름 부서 생성 방지
            return !Constants.IS_DATA_SAVED_SUCCESSFULLY;
        }

        department.setDepartmentId(departmentId);      //부서 아이디(워크스페이스아이디 + 시간)
        departmentRepository.save(department);         //DB에 부서 정보 저장

        bucketRepository.save(new Bucket("", departmentId));  //부서 버킷 생성

        return Constants.IS_DATA_SAVED_SUCCESSFULLY;
    }

    @Override
    public List<Department> getDepartmentList() {
        return  departmentRepository.findAll();
    }

    @Override
    public List<Department> getDepartmentListByWorkspace(String workspaceId) {
        return departmentRepository.findAllByWorkspaceId(workspaceId);
    }

    @Override
    public List<Department> getDepartmentListByMember(String workspaceId, String email) {
        return departmentRepository.findDepartmentListByMember(workspaceId, email);
    }

    @Override
    public boolean isExistingDepartment(String departmentId) {
        return departmentRepository.existsById(departmentId);
    }

    @Override
    public boolean isDepartmentNameInWorkspace(String workspaceId, String departmentName) {
        return departmentRepository.existsByWorkspaceIdAndDepartmentName(workspaceId, departmentName);
    }

    @Override
    public boolean isExistingMember(String departmentId, String email) {
        return departmentMemberRepository.existsByDepartmentIdAndEmail(departmentId, email);
    }
}
