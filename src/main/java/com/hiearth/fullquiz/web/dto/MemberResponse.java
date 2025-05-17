package com.hiearth.fullquiz.web.dto;

import com.hiearth.fullquiz.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private Long id;
    private String nickname;
    private int quizNum;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.quizNum = member.getNumOfSolvedQuiz();
    }
}