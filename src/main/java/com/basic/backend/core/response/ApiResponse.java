package com.basic.backend.core.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> result(T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = true;
        res.code = "SUCCESS";
        res.message = "요청 성공";
        res.data = data;
        return res;
    }

    public static <T> ApiResponse<T> err(String code, String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = false;
        res.code = code;
        res.message = message;
        res.data = null;
        return res;
    }

    public static <T> ApiResponse<T> err(String code, String message, T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = false;
        res.code = code;
        res.message = message;
        res.data = data;
        return res;
    }
}