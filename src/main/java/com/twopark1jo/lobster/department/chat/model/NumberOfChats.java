package com.twopark1jo.lobster.department.chat.model;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NumberOfChats {

    String departmentId;

    String departmentName;

    String messageCount;
}
