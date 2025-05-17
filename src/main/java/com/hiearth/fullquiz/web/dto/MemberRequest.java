package com.hiearth.fullquiz.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // getNickname() 자동 생성
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 7, message = "닉네임은 2자 이상, 7자 이하로 입력해주세요.")
    private String nickname;
}
