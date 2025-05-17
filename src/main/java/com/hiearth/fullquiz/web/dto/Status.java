package com.hiearth.fullquiz.web.dto;

import com.hiearth.fullquiz.domain.QuizProgress;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVATED("진행"),
    INACTIVATED("미완료"),
    ENDED("완료");

    private final String value;

}