package com.hiearth.fullquiz.web.controller;

import com.hiearth.fullquiz.domain.Member;
import com.hiearth.fullquiz.service.MemberService;
import com.hiearth.fullquiz.web.dto.MemberRequest;
import com.hiearth.fullquiz.web.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member/login")
    public ResponseEntity<MemberResponse> login(@RequestBody MemberRequest request) {
        Member member = memberService.findOrCreateMember(request.getNickname());
        return ResponseEntity.ok(new MemberResponse(member));
    }
}
