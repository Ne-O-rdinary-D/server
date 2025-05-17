package com.hiearth.fullquiz.web.dto;

import com.hiearth.fullquiz.domain.Quiz;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class QuizResponse {

    private Long id;
    private String content;
    private String firstOption;
    private String secondOption;
    private String answer;
    @Setter
    private boolean isCorrect;
    private Integer index;
    private String explanation;

    public static QuizResponse of(Quiz quiz, int index) {
        return QuizResponse.builder()
                .id(quiz.getId())
                .content(quiz.getContent())
                .firstOption(quiz.getFirstOption())
                .secondOption(quiz.getSecondOption())
                .answer(quiz.getAnswer())
                .isCorrect(false)
                .index(index)
                .explanation(quiz.getExplanation())
                .build();
    }
}
