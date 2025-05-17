package com.hiearth.fullquiz.web.dto;

import com.hiearth.fullquiz.domain.Quiz;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuizResponse {

    private Long id;
    private String content;
    private String firstOption;
    private String secondOption;
    private String answer;
    private String explanation;

    public static QuizResponse from(Quiz quiz) {
        return QuizResponse.builder()
                .id(quiz.getId())
                .content(quiz.getContent())
                .firstOption(quiz.getFirstOption())
                .secondOption(quiz.getSecondOption())
                .answer(quiz.getAnswer())
                .explanation(quiz.getExplanation())
                .build();
    }
}
