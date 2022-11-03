package com.twopark1jo.lobster.exception;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Getter
public class ErrorResponse {

    private Date timestamp;
    private String message;
    private String details;
}
