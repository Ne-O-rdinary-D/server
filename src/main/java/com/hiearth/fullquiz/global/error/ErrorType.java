package com.hiearth.fullquiz.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    // global 에러 (00)
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "00-001", "현재 앱에 문제가 발생했으니 관리자에게 문의해주세요."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "00-002", "요청한 리소스를 찾을 수 없습니다."),

    // member (01)
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "01-001", "사용자를 찾을 수 없습니다."),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "01-002", "이미 존재하는 닉네임입니다."),

    ; // 커스텀 에러 작성

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}