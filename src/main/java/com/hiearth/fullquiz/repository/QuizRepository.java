package com.hiearth.fullquiz.repository;

import com.hiearth.fullquiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> getQuizByCategoryId(Long categoryId);
}
