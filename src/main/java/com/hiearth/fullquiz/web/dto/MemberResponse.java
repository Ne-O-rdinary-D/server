package com.hiearth.fullquiz.web.dto;

import com.hiearth.fullquiz.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private Long id;
    private String nickname;
    private int numOfSolvedQuizzes;

    public static MemberResponse from(Member member) {
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.nickname = member.getNickname();
        memberResponse.id = member.getId();
        memberResponse.numOfSolvedQuizzes = member.getNumOfSolvedQuiz();
        return memberResponse;
    }
}