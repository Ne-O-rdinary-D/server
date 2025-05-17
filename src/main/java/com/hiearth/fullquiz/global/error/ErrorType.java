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

    // category (02)
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "02-001", "해당하는 카테고리가 존재하지 않습니다."),
    CHILD_CATEGORY_DOES_NOT_EXIXT(HttpStatus.NOT_FOUND, "02-002", "하위 카테고리가 존재하지 않습니다."),

    // quiz (03)
    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "03-001", "해당하는 퀴즈가 존재하지 않습니다."),

    // quiz progress(04)
    NO_QUIZ_IN_PROGRESS(HttpStatus.BAD_REQUEST, "04-001", "진행중인 퀴즈가 없습니다."),
    ; // 커스텀 에러 작성

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}