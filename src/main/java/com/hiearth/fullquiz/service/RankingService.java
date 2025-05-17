package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.web.dto.RankingResponse;


public interface RankingService {
    RankingResponse getRanking(String nickname);
}
