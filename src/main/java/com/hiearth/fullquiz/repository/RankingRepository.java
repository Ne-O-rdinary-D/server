package com.hiearth.fullquiz.repository;

import com.hiearth.fullquiz.domain.Category;
import com.hiearth.fullquiz.domain.mapping.MemberQuiz;
import com.hiearth.fullquiz.web.dto.RankingResponse;
import com.hiearth.fullquiz.web.dto.UserRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<MemberQuiz, Long> {

    @Query("""
    SELECT m.nickname,
           COALESCE(SUM(CASE WHEN mq.isCorrect = true THEN 1 ELSE 0 END), 0),
           COALESCE(SUM(CASE WHEN mq.isCorrect = false THEN 1 ELSE 0 END), 0)
    FROM Member m
    LEFT JOIN MemberQuiz mq ON mq.member = m
    GROUP BY m.nickname
    ORDER BY COALESCE(SUM(CASE WHEN mq.isCorrect = true THEN 1 ELSE 0 END), 0) DESC
""")
    List<Object[]> findRankingRaw();






}
