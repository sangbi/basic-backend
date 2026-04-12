package com.basic.backend.core.exception;

import com.basic.backend.core.response.ErrorCode;

public class BasicException extends RuntimeException {

    private final ErrorCode errorCode;

    public BasicException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public BasicException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}