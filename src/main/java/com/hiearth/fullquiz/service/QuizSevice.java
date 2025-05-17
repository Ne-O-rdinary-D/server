package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.service.request.CheckAnswerDTO;
import com.hiearth.fullquiz.web.dto.CategoriesResponse;
import com.hiearth.fullquiz.web.dto.ContinueQuizResponse;
import com.hiearth.fullquiz.web.dto.QuizResponse;

import java.util.List;

public interface QuizSevice {

    List<CategoriesResponse> getCategories();
    List<QuizResponse> getQuizzes(Long memberId, String category);
    void checkAnswer(Long quizId, Long memberId, CheckAnswerDTO checkAnswerDTO);

    ContinueQuizResponse continueQuiz(Long memberId, String category);
}