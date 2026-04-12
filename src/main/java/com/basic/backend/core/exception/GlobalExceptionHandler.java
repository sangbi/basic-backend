package com.basic.backend.core.exception;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.response.ErrorCode;
import com.basic.backend.core.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ValidationErrorResponse>> handleValidation(
            MethodArgumentNotValidException e
    ) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;

        log.warn("Validation error: {}", errors, e);

        return ResponseEntity
                .status(errorCode.httpStatus())
                .body(ApiResponse.err(
                        errorCode.code(),
                        errorCode.message(),
                        ValidationErrorResponse.builder()
                                .errors(errors)
                                .build()
                ));
    }

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ApiResponse<?>> handleBasic(BasicException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.warn(
                "Basic exception occurred: errorCode={} httpStatus={} message={}",
                errorCode.code(),
                errorCode.httpStatus().value(),
                e.getMessage(),
                e
        );

        return ResponseEntity
                .status(errorCode.httpStatus())
                .body(ApiResponse.err(
                        errorCode.code(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateKey(DuplicateKeyException e) {
        ErrorCode errorCode = ErrorCode.DUPLICATE_KEY;

        log.error(
                "Database exception occurred: errorCode={} httpStatus={} exception={}",
                errorCode.code(),
                errorCode.httpStatus().value(),
                e.getClass().getSimpleName(),
                e
        );

        return ResponseEntity
                .status(errorCode.httpStatus())
                .body(ApiResponse.err(
                        errorCode.code(),
                        errorCode.message()
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrity(DataIntegrityViolationException e) {
        ErrorCode errorCode = ErrorCode.DATA_INTEGRITY_ERROR;

        log.error("Data integrity violation occurred", e);

        return ResponseEntity
                .status(errorCode.httpStatus())
                .body(ApiResponse.err(
                        errorCode.code(),
                        errorCode.message()
                ));
    }

    @ExceptionHandler({BadSqlGrammarException.class, MyBatisSystemException.class})
    public ResponseEntity<ApiResponse<?>> handleDatabase(Exception e) {
        ErrorCode errorCode = ErrorCode.DATABASE_ERROR;

        log.error("Database/MyBatis exception occurred", e);

        return ResponseEntity
                .status(errorCode.httpStatus())
                .body(ApiResponse.err(
                        errorCode.code(),
                        errorCode.message()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handle(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        log.error("Unhandled exception occurred", e);

        return ResponseEntity
                .status(errorCode.httpStatus())
                .body(ApiResponse.err(
                        errorCode.code(),
                        errorCode.message()
                ));
    }
}