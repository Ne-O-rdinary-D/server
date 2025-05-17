package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.global.error.ErrorType;
import com.hiearth.fullquiz.global.exception.FullquizException;
import com.hiearth.fullquiz.repository.MemberRepository;
import com.hiearth.fullquiz.repository.RankingRepository;
import com.hiearth.fullquiz.web.dto.RankingResponse;
import com.hiearth.fullquiz.web.dto.UserRanking;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RankingServiceImpl implements RankingService{

    private final RankingRepository rankingRepository;
    private final MemberRepository memberRepository;
    @Override
    public RankingResponse getRanking(String targetNickname) {

        memberRepository.findByNickname(targetNickname).orElseThrow(() -> new FullquizException(ErrorType.MEMBER_NOT_FOUND));


        List<Object[]> rawResults = rankingRepository.findRankingRaw();

        List<UserRanking> rankings = new ArrayList<>();

        long rankCounter = 1;
        long prevAnswerCnt = -1;
        long displayRank = 1;

        for (Object[] row : rawResults) {
            String nickname = (String) row[0];
            int answerCnt = ((Number) row[1]).intValue();
            int wrongCnt = ((Number) row[2]).intValue();

            if (prevAnswerCnt != answerCnt) {
                displayRank = rankCounter;
            }

            rankings.add(new UserRanking(displayRank, nickname, answerCnt, wrongCnt));

            prevAnswerCnt = answerCnt;
            rankCounter++;
        }

        // 내 정보 찾기
        UserRanking myInfo = rankings.stream()
                .filter(r -> r.getNickname().equals(targetNickname))
                .findFirst()
                .orElse(new UserRanking(0L, targetNickname, 0, 0));

        return new RankingResponse(myInfo, rankings);
    }

}
