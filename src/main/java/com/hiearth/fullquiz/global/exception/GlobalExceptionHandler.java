package com.hiearth.fullquiz.global.exception;

import com.hiearth.fullquiz.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import com.hiearth.fullquiz.global.error.ErrorType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FullquizException.class)
    public ResponseEntity<ApiResponse<Void>> handlePhotorizeException(FullquizException ex) {
        log.error("PhotorizeException: {}", ex.getMessage(), ex);
        return ApiResponse.error(ex.getErrorType());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.warn("NoResourceFoundException: {}", ex.getMessage(), ex);
        return ApiResponse.error(ErrorType.RESOURCE_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return ApiResponse.error(ErrorType.DEFAULT_ERROR);
    }
}