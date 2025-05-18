package com.hiearth.fullquiz.web.controller;

import com.hiearth.fullquiz.global.response.ApiResponse;
import com.hiearth.fullquiz.service.RankingService;
import com.hiearth.fullquiz.web.dto.CategoriesResponse;
import com.hiearth.fullquiz.web.dto.RankingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Ranking", description = "랭킹 관련 API")
@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @Operation(summary = "내 정보 및 랭킹 보기", description = "나의 퀴즈 정보 및 전체 랭킹 정보를 반환합니다.")
    @GetMapping("/rank")
    public ResponseEntity<ApiResponse<RankingResponse>> getRanking (@RequestParam @Size(min = 2, max = 7, message = "닉네임은 2자 이상, 7자 이하로 입력해주세요.") String nickname) {
        return ApiResponse.ok(rankingService.getRanking(nickname));
    }
}
