package com.hiearth.fullquiz.global.response;

import com.hiearth.fullquiz.global.error.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder(access = lombok.AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

    // OK 응답 생성 메서드
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<T>builder()
                        .status(HttpStatus.OK.value())
                        .data(data)
                        .build());
    }

    // CREATED 응답 생성 메서드
    public static <T> ResponseEntity<ApiResponse<T>> created() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<T>builder()
                        .status(HttpStatus.CREATED.value())
                        .build());
    }

    // CREATED 응답 생성 메서드
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<T>builder()
                        .status(HttpStatus.CREATED.value())
                        .data(data)
                        .build());
    }

    // 에러 응답 생성 메서드
    public static ResponseEntity<ApiResponse<Void>> error(ErrorType errorType) {
        return ResponseEntity.status(errorType.getStatus())
                .body(ApiResponse.<Void>builder()
                        .status(errorType.getStatus().value())
                        .errorCode(errorType.getErrorCode())
                        .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.<T>builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(message)
                        .build());
    }

}