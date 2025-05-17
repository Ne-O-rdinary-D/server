package com.hiearth.fullquiz.initializer.dto;

import com.hiearth.fullquiz.domain.Category;
import com.hiearth.fullquiz.domain.Quiz;
import com.hiearth.fullquiz.domain.QuizType;
import lombok.Getter;

@Getter
public class QuizDto {
    private QuizType quizType;
    private String answer;
    private String content;
    private String firstOption;
    private String secondOption;
    private String explanation;

    public Quiz toEntity(Category category) {
        return Quiz.builder()
                .quizType(quizType)
                .answer(answer)
                .content(content)
                .firstOption(firstOption)
                .secondOption(secondOption)
                .explanation(explanation)
                .category(category)
                .build();
    }
}
