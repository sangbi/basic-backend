package com.basic.backend.core.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // COMMON
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_404", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 내부 오류입니다."),

    // AUTH
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_403", "접근 권한이 없습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_401_LOGIN", "로그인에 실패했습니다."),
    LOGOUT_FAILED(HttpStatus.BAD_REQUEST, "AUTH_400_LOGOUT", "로그아웃 처리에 실패했습니다."),

    // TOKEN
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_401_INVALID", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_401_EXPIRED", "만료된 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_401_REFRESH_INVALID", "유효하지 않은 리프레시 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN_401_REFRESH_NOT_FOUND", "저장된 리프레시 토큰이 없습니다."),

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "사용자가 존재하지 않습니다."),
    DUPLICATE_USER_ID(HttpStatus.BAD_REQUEST, "USER_400_DUPLICATE", "이미 존재하는 사용자입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "USER_401_PASSWORD", "비밀번호가 일치하지 않습니다."),

    // DB
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB_500", "데이터베이스 처리 중 오류가 발생했습니다."),
    DUPLICATE_KEY(HttpStatus.BAD_REQUEST, "DB_400_DUPLICATE", "중복 데이터가 존재합니다."),
    DATA_INTEGRITY_ERROR(HttpStatus.BAD_REQUEST, "DB_400_INTEGRITY", "데이터 무결성 오류가 발생했습니다."),
    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, "DB_100", "해당 데이터가 존재하지않습니다."),

    UPLOAD_FILE_SIZE_OVER(HttpStatus.BAD_REQUEST,"FILE_400", "업로드 가능한 파일 크기를 초과했습니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}