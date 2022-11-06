package com.twopark1jo.lobster.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse().builder()
                .timestamp(new Date())
                .message(exception.getMessage())
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class) //모든 예외 처리
    protected final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse().builder()
                .timestamp(new Date())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MemberException.class})
    protected ResponseEntity handleMemberException(MemberException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse().builder()
                .timestamp(new Date())
                .message(exception.getErrorCode().getMessage())
                .details(request.getDescription(false))
                .build();
        
        return new ResponseEntity(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler({DepartmentException.class})
    protected ResponseEntity handleDepartmentException(DepartmentException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse().builder()
                .timestamp(new Date())
                .message(exception.getErrorCode().getMessage())
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity(errorResponse, HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }
}
