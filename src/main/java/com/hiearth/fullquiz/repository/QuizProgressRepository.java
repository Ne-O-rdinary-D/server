package com.hiearth.fullquiz.repository;


import com.hiearth.fullquiz.domain.QuizProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizProgressRepository extends JpaRepository<QuizProgress, Long> {
    List<QuizProgress> findByMemberId(Long memberId);
    Optional<QuizProgress> findByMemberIdAndCategoryId(Long memberId, Long categoryId);
}
