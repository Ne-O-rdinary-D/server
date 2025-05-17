package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.service.request.CheckAnswerDTO;
import com.hiearth.fullquiz.web.dto.CategoriesResponse;
import com.hiearth.fullquiz.web.dto.QuizResponse;
import com.hiearth.fullquiz.web.dto.StatusResponse;

import java.util.List;

public interface QuizSevice {

    List<CategoriesResponse> getCategories();
    List<QuizResponse> getQuizzes(Long memberId, String category);
    void checkAnswer(Long quizId, Long memberId, CheckAnswerDTO checkAnswerDTO);
    List<QuizResponse> resumeQuiz(Long quizProgressId);

    List<StatusResponse> getMyStatus(String nickname);
}