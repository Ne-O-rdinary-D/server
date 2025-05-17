package com.hiearth.fullquiz.repository;

import com.hiearth.fullquiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
