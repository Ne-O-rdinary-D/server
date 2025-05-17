package com.hiearth.fullquiz.web.dto;

import com.hiearth.fullquiz.domain.Quiz;
import com.hiearth.fullquiz.domain.QuizAnswer;
import com.hiearth.fullquiz.domain.QuizType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuizResponse {

    private Long id;
    private String content;
    private String firstOption;
    private String secondOption;
    private QuizType quizType;
    private String answer;
    private Boolean isCorrect;
    private Integer index;
    private String explanation;

    public static QuizResponse of(Quiz quiz, int index) {
        return QuizResponse.builder()
                .id(quiz.getId())
                .content(quiz.getContent())
                .firstOption(quiz.getFirstOption())
                .quizType(quiz.getQuizType())
                .secondOption(quiz.getSecondOption())
                .answer(quiz.getAnswer())
                .isCorrect(false)
                .index(index)
                .explanation(quiz.getExplanation())
                .build();
    }

    public static QuizResponse forResume(Quiz quiz, QuizAnswer quizAnswer) {
        return QuizResponse.builder()
                .id(quiz.getId())
                .content(quiz.getContent())
                .firstOption(quiz.getFirstOption())
                .secondOption(quiz.getSecondOption())
                .answer(quiz.getAnswer())
                .explanation(quiz.getExplanation())
                .isCorrect(quizAnswer.getIsCorrect())
                .index(quizAnswer.getIndexNumber())
                .build();
    }
}
