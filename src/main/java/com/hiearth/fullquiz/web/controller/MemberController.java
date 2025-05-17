package com.hiearth.fullquiz.web.controller;

import com.hiearth.fullquiz.domain.Member;
import com.hiearth.fullquiz.global.response.ApiResponse;
import com.hiearth.fullquiz.service.MemberService;
import com.hiearth.fullquiz.service.request.MemberCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/api/members")
    public ResponseEntity<?> checkNickName(@RequestParam String nickname){
        boolean exists = memberService.existsByNickname(nickname);
        if (exists) {
            return ApiResponse.ok("이미 존재하는 닉네임이에요");
        } else {
            return ApiResponse.notFound("사용 가능한 닉네임이에요");
        }
    }

    @PostMapping("/api/members")
    public ResponseEntity<?> registerMember(@RequestBody MemberCreateDTO memberCreateDTO) {
        memberService.findOrCreateMember(memberCreateDTO.getNickname());
        return ApiResponse.created();
    }
}
