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
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Quiz", description = """

  퀴즈 API 컨트롤러
 
  ✅ [서비스 개요]
  - 본 서비스는 '환경 교육 퀴즈'를 통해 사용자가 학습할 수 있도록 구성되어 있습니다.
  - 사용자는 카테고리 → 세부 단계 → 문제 풀이 순서로 퀴즈를 진행하게 됩니다.
 
  ✅ [카테고리 구조]
  1. 재활용
     - 재활용의 여정
     - 분리배출
     - 재활용 장소
     - 재활용 결과
 
  2. 기후변화
     - 기후의 변화
     - 지구 오염
     - 피해
     - 변화
 
  3. 멸종 위기 동물
     - 동물의 특징과 분류
     - 멸종 위기
     - 생태계 관계
     - 지표종
 
  ✅ [진행 규칙]
  1. 사용자는 반드시 카테고리별 순서에 따라 문제를 풀어야 하며,
     진행 중인 카테고리를 완료하지 않으면 다른 카테고리로 이동할 수 없습니다.
  2. 퀴즈는 단계별 5문제가 있으며, 문제를 풀 때마다 결과가 저장됩니다.
  3. 도중 이탈 시, 반드시 퀴즈 재개 API를 통해 이어서 풀어야 합니다.
  4. 새로운 퀴즈 시작은 진행 중인 퀴즈가 없을 때만 허용됩니다.
 

""")
@RestController
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    private final QuizSevice quizService;
    private final MemberService memberService;


    @Operation(
            summary = "퀴즈 시작 - 선택한 카테고리의 퀴즈 리스트 조회",
            description = """
    사용자가 카테고리를 선택해 퀴즈를 시작합니다.  
    이미 진행 중인 퀴즈가 있다면, 새로운 퀴즈 시작은 불가능하며 퀴즈 재개 API를 이용해야 합니다.

    ✅ [진행 조건]
    - 현재 진행 중인 퀴즈가 없는 상태여야 시작 가능

    ✅ [요청 파라미터]
    - nickname (String, 필수, 2자 이상 7자 이하) : 사용자의 닉네임
    - category (String, 필수) : 선택한 카테고리명 (예: '재활용')

    ✅ [반환]
    - 선택한 카테고리의 단계(5문제)의 퀴즈 리스트
    """
    )
    @PostMapping("/api/members/{memberId}/quizzes")
    public ResponseEntity<?> getQuizzes(@RequestParam @Size(min = 2, max = 7, message = "닉네임은 2자 이상, 7자 이하로 입력해주세요.") String nickname,
                                        @RequestParam("category") String category) {
        log.info("nickname : {} " + nickname);
        return ApiResponse.ok(quizService.getQuizzes(memberService.findByNickname(nickname).getId(), category));
    }

    @Operation(
            summary = "퀴즈 풀이 결과 저장",
            description = """
    사용자가 퀴즈를 풀고 선택한 답을 저장합니다.  
    문제를 하나씩 풀 때마다 즉시 결과가 저장되며, 도중 이탈해도 재개가 가능합니다.

    ✅ [요청 경로]
    - quizId (Path Variable, 필수) : 푼 퀴즈의 ID

    ✅ [요청 파라미터]
    - nickname (String, 필수) : 사용자의 닉네임

    ✅ [요청 Body - CheckAnswerDTO]
    - userAnswer (String, 필수) : 사용자가 선택한 정답
    - isCorrect (boolean, 필수) : userAnswer의 정답 여부

    ✅ [반환]
    - 성공 여부 (성공 시 200 OK)
    """
    )
    @PostMapping("/api/members/{memberId}/quizzes/{quizId}")
    public ResponseEntity<?> checkAnswer(@RequestParam String nickname,
                                         @PathVariable("quizId") Long quizId,
                                         @RequestBody CheckAnswerDTO checkAnswerDTO) {
        quizService.checkAnswer(quizId, memberService.findByNickname(nickname).getId(), checkAnswerDTO);
        return ApiResponse.ok(null);
    }

    @Operation(
            summary = "현재 진행 중인 퀴즈 조회",
            description = """
    사용자가 도중에 중단한 퀴즈가 있다면, 이어서 풀 수 있도록 현재 진행 상태를 조회합니다.

    ✅ [요청 파라미터]
    - nickname (String, 필수) : 사용자의 닉네임

    ✅ [반환]
    - 현재 진행 중인 퀴즈 정보 (현재 단계, 푼 문제 수, 남은 문제 수 등)
    """
    )
    @GetMapping("/api/quizzes")
    public ResponseEntity<?> getProgress(@RequestParam String nickname) {
        QuizProgressDTO quizProgress = quizService.getQuizProgress(memberService.findByNickname(nickname).getId());
        return ApiResponse.ok(quizProgress);
    }


    @Operation(
            summary = "퀴즈 재개",
            description = """
    도중에 중단한 퀴즈를 이어서 풀기 위한 API입니다.  
    진행 중인 카테고리의 퀴즈를 다 풀지 않으면, 반드시 이 API를 사용해 재개해야 합니다.

    ✅ [요청 경로]
    - quizProgressId (Path Variable, 필수) : 진행 중인 퀴즈의 ID

    ✅ [반환]
    - 이어서 풀 퀴즈 리스트
    """
    )
    @GetMapping("/api/quizzes/{quizProgressId}")
    public ResponseEntity<?> resumeQuiz(@PathVariable("quizProgressId") Long quizProgressId) {
        return ApiResponse.ok(quizService.resumeQuiz(quizProgressId));
    }

    @Operation(
            summary = "전체 카테고리 보기",
            description = """
    퀴즈를 시작할 수 있는 전체 카테고리와 단계 목록을 반환합니다.

    ✅ [반환]
    - 카테고리 리스트 (상위 카테고리와 그에 속한 4개의 단계)
    """
    )
    @GetMapping("/api/quizes/getCategories")
    public ResponseEntity<ApiResponse<List<CategoriesResponse>>> getCategories () {
        return ApiResponse.ok(quizService.getCategories());
    }

    @Operation(
            summary = "유저 퀴즈 진행 상태 조회",
            description = """
    사용자의 퀴즈 진행 상태를 전체 카테고리 기준으로 반환합니다.
    각 카테고리에 대해 다음 상태 중 하나를 반환합니다:
    - 미완료 (한 번도 시작하지 않음)
    - 진행 중 (문제를 푸는 도중)
    - 완료 (모든 문제 풀이 완료)

    ✅ [요청 파라미터]
    - nickname (String, 필수) : 사용자의 닉네임

    ✅ [반환]
    - 카테고리별 진행 상태 목록
    """
    )
    @GetMapping("/api/quiz/mystatus")
    public ResponseEntity<ApiResponse<List<StatusResponse>>> getStatus(@RequestParam String nickname) {
        return ApiResponse.ok(quizService.getMyStatus(nickname));
    }

}