package com.hiearth.fullquiz.web.controller;

import com.hiearth.fullquiz.global.response.ApiResponse;
import com.hiearth.fullquiz.service.MemberService;
import com.hiearth.fullquiz.service.request.MemberCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "유저 회원가입", description = "유저를 닉네임으로 등록합니다.")
    @PostMapping("/api/members")
    public ResponseEntity<?> registerMember(@RequestBody MemberCreateDTO memberCreateDTO) {
        return ApiResponse.created(memberService.findOrCreateMember(memberCreateDTO.getNickname()));
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임의 중복 여부를 확인합니다.")
    @GetMapping("/api/members")
    public ResponseEntity<?> checkDuplicateNickname(@RequestParam @Size(min = 2, max = 7, message = "닉네임은 2자 이상, 7자 이하로 입력해주세요.") String nickname) {
        return ApiResponse.ok(memberService.existsByNickname(nickname));
    }

}
