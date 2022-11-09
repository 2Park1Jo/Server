package com.twopark1jo.lobster.department.department.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class DepartmentMemberPK implements Serializable {

    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "email")
    private String email;
}
