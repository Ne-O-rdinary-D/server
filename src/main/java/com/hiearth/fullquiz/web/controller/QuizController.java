package com.hiearth.fullquiz.web.controller;

import com.hiearth.fullquiz.global.response.ApiResponse;
import com.hiearth.fullquiz.service.MemberService;
import com.hiearth.fullquiz.service.QuizSevice;
import com.hiearth.fullquiz.service.request.CheckAnswerDTO;
import com.hiearth.fullquiz.web.dto.CategoriesResponse;
import com.hiearth.fullquiz.web.dto.ContinueQuizResponse;
import com.hiearth.fullquiz.web.dto.QuizResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizSevice quizService;
    private final MemberService memberService;

    @PostMapping("/api/members/{memberId}/quizzes")
    public ResponseEntity<?> getQuizzes(@RequestParam String nickname,
                                        @RequestParam("category") String category) {
        return ApiResponse.ok(quizService.getQuizzes(memberService.findByNickname(nickname).getId(), category));
    }

    @PostMapping("/api/members/{memberId}/quizzes/{quizId}")
    public ResponseEntity<?> checkAnswer(@RequestParam String nickname,
                                         @PathVariable("quizId") Long quizId,
                                         @RequestBody CheckAnswerDTO checkAnswerDTO) {
        quizService.checkAnswer(quizId, memberService.findByNickname(nickname).getId(), checkAnswerDTO);
        return ApiResponse.ok(null);
    }

    @GetMapping("/api/quizzes/{quizProgressId}")
    public ResponseEntity<?> resumeQuiz(@PathVariable("quizProgressId") Long quizProgressId) {
        return ApiResponse.ok(quizService.resumeQuiz(quizProgressId));
    }

    @GetMapping("/api/quizes/getCategories")
    public ResponseEntity<ApiResponse<List<CategoriesResponse>>> getCategories () {
        return ApiResponse.ok(quizService.getCategories());
    }

    @GetMapping("/api/quizes/continue")
    public ResponseEntity<ApiResponse<ContinueQuizResponse>> continueQuiz(@RequestParam String nickname, @RequestParam String category) {
        return ApiResponse.ok(quizService.continueQuiz(memberService.findByNickname(nickname).getId(), category));
    }


}