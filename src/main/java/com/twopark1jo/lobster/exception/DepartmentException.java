package com.twopark1jo.lobster.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DepartmentException extends RuntimeException{
    private ErrorCode errorCode;
}
