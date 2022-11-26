package com.twopark1jo.lobster.department.chat.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TopThreeChats {
    private String memberName;

    private String departmentNameList;

    private String numberOfChats;
}
