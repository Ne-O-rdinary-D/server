package com.hiearth.fullquiz.web.controller;

import com.hiearth.fullquiz.global.response.ApiResponse;
import com.hiearth.fullquiz.service.MemberService;
import com.hiearth.fullquiz.service.QuizSevice;
import com.hiearth.fullquiz.service.request.CheckAnswerDTO;
import com.hiearth.fullquiz.web.dto.CategoriesResponse;
import com.hiearth.fullquiz.web.dto.QuizProgressDTO;
import com.hiearth.fullquiz.web.dto.StatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Quiz", description = "퀴즈 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    private final QuizSevice quizService;
    private final MemberService memberService;

    @Operation(summary = "퀴즈 불러오기", description = "카테고리 선택 시 퀴즈 리스트를 반환합니다.")
    @PostMapping("/api/members/{memberId}/quizzes")
    public ResponseEntity<?> getQuizzes(@RequestParam String nickname,
                                        @RequestParam("category") String category) {
        log.info("nickname : {} " + nickname);
        return ApiResponse.ok(quizService.getQuizzes(memberService.findByNickname(nickname).getId(), category));
    }

    @Operation(summary = "푼 문제 저장", description = "유저가 문제 내 답 선택 시 결과값을 저장합니다.")
    @PostMapping("/api/members/{memberId}/quizzes/{quizId}")
    public ResponseEntity<?> checkAnswer(@RequestParam String nickname,
                                         @PathVariable("quizId") Long quizId,
                                         @RequestBody CheckAnswerDTO checkAnswerDTO) {
        quizService.checkAnswer(quizId, memberService.findByNickname(nickname).getId(), checkAnswerDTO);
        return ApiResponse.ok(null);
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<?> getProgress(@RequestParam String nickname) {
        QuizProgressDTO quizProgress = quizService.getQuizProgress(memberService.findByNickname(nickname).getId());
        return ApiResponse.ok(quizProgress);
    }


    @Operation(summary = "퀴즈 재개", description = "이전에 진행하던 카테고리 선택 시 퀴즈 리스트를 반환합니다.")
    @GetMapping("/api/quizzes/{quizProgressId}")
    public ResponseEntity<?> resumeQuiz(@PathVariable("quizProgressId") Long quizProgressId) {
        return ApiResponse.ok(quizService.resumeQuiz(quizProgressId));
    }

    @Operation(summary = "카테고리 보기", description = "전체 카테고리를 반환합니다.")
    @GetMapping("/api/quizes/getCategories")
    public ResponseEntity<ApiResponse<List<CategoriesResponse>>> getCategories () {
        return ApiResponse.ok(quizService.getCategories());
    }

    @Operation(summary = "퀴즈 진행 상태 전체 보기", description = "마이 퀴즈 내 유저의 퀴즈 진행도를 반환합니다.")
    @GetMapping("/api/quiz/mystatus")
    public ResponseEntity<ApiResponse<List<StatusResponse>>> getStatus(@RequestParam String nickname) {
        return ApiResponse.ok(quizService.getMyStatus(nickname));
    }

}