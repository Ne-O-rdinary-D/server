package com.hiearth.fullquiz.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RankingResponse {
    private UserRanking myInfo;
    private List<UserRanking> userRankingList;
}
