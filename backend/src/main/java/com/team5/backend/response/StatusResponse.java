package com.team5.backend.response;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;

@Getter
public class StatusResponse extends ResponseDTO {

    private static final Map<Integer, StatusCode> codeMap = Map.of(
            200, StatusCode.OK,
            400, StatusCode.BAD_REQUEST,
            401, StatusCode.UNAUTHORIZED,
            404, StatusCode.NOT_FOUND,
            500, StatusCode.INTERNAL_SERVER_ERROR
    );

    private final Map<String, Object> data;

    private StatusResponse(StatusCode statusCode) {
        super(statusCode.getStatus(), statusCode.getMessage());
        this.data = Collections.emptyMap();
    }

    private StatusResponse(StatusCode statusCode, Exception e) {
        super(statusCode.getStatus(), statusCode.getMessage(e));
        this.data = Collections.emptyMap();
    }

    private StatusResponse(StatusCode statusCode, String message) {
        super(statusCode.getStatus(), message);
        this.data = Collections.emptyMap();
    }



    public static StatusResponse of(StatusCode statusCode) {
        return new StatusResponse(statusCode);
    }

    public static StatusResponse of(StatusCode statusCode, Exception e) {
        return new StatusResponse(statusCode, e);
    }

    public static StatusResponse of(StatusCode statusCode, String message) {
        return new StatusResponse(statusCode, message);
    }


    public static StatusResponse of(int numCode) {
        return new StatusResponse(codeMap.get(numCode));
    }

    public static StatusResponse of(int numCode, Exception e) {
        return new StatusResponse(codeMap.get(numCode), e);
    }

    public static StatusResponse of(int numCode, String message) {
        return new StatusResponse(codeMap.get(numCode), message);
    }

}