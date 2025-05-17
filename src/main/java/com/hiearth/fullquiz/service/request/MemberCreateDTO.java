package com.hiearth.fullquiz.service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberCreateDTO {

    @NotNull(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 7, message = "닉네임은 2자 이상, 7자 이하로 입력해주세요.")
    private String nickname;
}
