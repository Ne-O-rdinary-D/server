package com.hiearth.fullquiz.repository;

import com.hiearth.fullquiz.domain.mapping.MemberQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberQuizRepository extends JpaRepository<MemberQuiz, Long> {
}
