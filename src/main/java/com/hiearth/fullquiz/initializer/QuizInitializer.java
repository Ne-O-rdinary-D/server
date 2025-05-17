package com.hiearth.fullquiz.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiearth.fullquiz.domain.Category;
import com.hiearth.fullquiz.domain.Quiz;
import com.hiearth.fullquiz.domain.QuizType;
import com.hiearth.fullquiz.initializer.dto.QuizDto;
import com.hiearth.fullquiz.repository.CategoryRepository;
import com.hiearth.fullquiz.repository.QuizRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//@Component
//@RequiredArgsConstructor
//public class QuizInitializer {
//
//    private final QuizRepository quizRepository;
//    private final ObjectMapper objectMapper;
//    private final CategoryRepository categoryRepository;
//
//    @PostConstruct
//    public void loadQuizData() throws IOException {
//        if (quizRepository.count() == 0) {
//            InputStream inputStream = getClass().getResourceAsStream("/quiz.json");
//
//            List<QuizDto> quizDtos = objectMapper.readValue(inputStream, new TypeReference<>() {});
//
//            // ✅ 이 Category만 한 번 조회해서 재사용
//            Category category = categoryRepository.findByName("재활용")
//                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다"));
//
//            List<Quiz> quizzes = quizDtos.stream()
//                    .map(dto -> Quiz.builder()
//                            .quizType(QuizType.valueOf(dto.getQuizType()))
//                            .answer(dto.getAnswer())
//                            .content(dto.getContent())
//                            .firstOption(dto.getFirstOption())
//                            .secondOption(dto.getSecondOption())
//                            .explanation(dto.getExplanation())
//                            .category(category)
//                            .build())
//                    .toList();
//
//            quizRepository.saveAll(quizzes);
//        }
//    }
//
//}
