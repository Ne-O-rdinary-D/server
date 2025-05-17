package com.hiearth.fullquiz.web.controller;

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

    @PostMapping("/api/members")
    public ResponseEntity<?> registerMember(@RequestBody MemberCreateDTO memberCreateDTO) {
        return ApiResponse.created(memberService.findOrCreateMember(memberCreateDTO.getNickname()));
    }

    @GetMapping("/api/members")
    public ResponseEntity<?> checkDuplicateNickname(@RequestParam String nickname) {
        return ApiResponse.ok(memberService.existsByNickname(nickname));
    }

}
