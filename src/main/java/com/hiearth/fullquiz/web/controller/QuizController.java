package com.hiearth.fullquiz.web.controller;

import com.hiearth.fullquiz.domain.Quiz;
import com.hiearth.fullquiz.global.response.ApiResponse;
import com.hiearth.fullquiz.service.QuizSevice;
import com.hiearth.fullquiz.service.request.CheckAnswerDTO;
import com.hiearth.fullquiz.web.dto.CategoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizSevice quizService;

    @PostMapping("/api/members/{memberId}/quizzes")
    public ResponseEntity<?> getQuizzes(@PathVariable Long memberId,
                                        @RequestParam("category") String category) {
        return ApiResponse.ok(quizService.getQuizzes(memberId, category));
    }

    @PostMapping("/api/members/{memberId}/quizzes/{quizId}")
    public ResponseEntity<?> checkAnswer(@PathVariable("memberId") Long memberId,
                                         @PathVariable("quizId") Long quizId,
                                         @RequestBody CheckAnswerDTO checkAnswerDTO) {
        quizService.checkAnswer(quizId, memberId, checkAnswerDTO);
        return ApiResponse.ok(null);
    }

    @GetMapping("/api/quizes/getCategories")
    public ResponseEntity<ApiResponse<List<CategoriesResponse>>> getCategories () {
        return ApiResponse.ok(quizService.getCategories());
    }


}