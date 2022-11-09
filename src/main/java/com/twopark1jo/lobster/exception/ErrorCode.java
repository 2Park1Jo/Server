package com.twopark1jo.lobster.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EXISTED_EMAIL(409, "이미 존재하는 회원입니다."),
    EXISTED_DEPARTMENT_NAME(409, "이미 존재하는 부서명입니다"),
    EXISTED_DEPARTMENT_ID(409, "이미 존재하는 부서아이디입니다");

    private final int status;
    private final String message;
}
