package com.twopark1jo.lobster.department.department;

import com.twopark1jo.lobster.department.department.member.DepartmentMember;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCreation {
    private Department department;

    private List<DepartmentMember> departmentMemberList;
}
