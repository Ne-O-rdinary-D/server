package com.hiearth.fullquiz.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRanking {
    @Setter
    private Long nowRank;
    private String nickname;
    private int answerCnt;
    private int wrongCnt;

    public UserRanking(String nickname, int answerCnt, int wrongCnt) {
        this.nickname = nickname;
        this.answerCnt = answerCnt;
        this.wrongCnt = wrongCnt;
    }
}
