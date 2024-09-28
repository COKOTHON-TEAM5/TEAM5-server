package com.team5.backend.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDTO {

    private final int code;

    private final String message;

    public ResponseDTO(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }

    public static ResponseDTO build(StatusCode statusCode) {
        return new ResponseDTO(statusCode.getStatus(), statusCode.getMessage());
    }
}