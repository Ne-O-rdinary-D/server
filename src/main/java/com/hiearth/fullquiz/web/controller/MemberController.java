package com.hiearth.fullquiz.web.controller;

import com.hiearth.fullquiz.global.response.ApiResponse;
import com.hiearth.fullquiz.service.MemberService;
import com.hiearth.fullquiz.service.request.MemberCreateDTO;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<?> registerMember(@RequestBody MemberCreateDTO memberCreateDTO) {
        return ApiResponse.created(memberService.findOrCreateMember(memberCreateDTO.getNickname()));
    }

    @GetMapping("/api/members")
    public ResponseEntity<?> checkDuplicateNickname(@RequestParam @Size(min = 2, max = 7, message = "닉네임은 2자 이상, 7자 이하로 입력해주세요.") String nickname) {
        return ApiResponse.ok(memberService.existsByNickname(nickname));
    }

}
