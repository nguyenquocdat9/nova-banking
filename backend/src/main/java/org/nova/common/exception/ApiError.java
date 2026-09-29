package org.nova.common.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ApiError {

    private int status;

    private String message;

    private Map<String, String> errors;

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.errors = null;
    }

    public ApiError(
            int status,
            String message,
            Map<String, String> errors
    ) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

}
