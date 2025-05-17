package com.hiearth.fullquiz.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TotalQuizResponse {
    private Long quizProgressId;
    private List<QuizResponse> quizResponses;

    public static TotalQuizResponse of(Long quizProgressId, List<QuizResponse> quizResponses) {
        TotalQuizResponse totalQuizResponse = new TotalQuizResponse();
        totalQuizResponse.quizProgressId = quizProgressId;
        totalQuizResponse.quizResponses = quizResponses;
        return totalQuizResponse;
    }
}
