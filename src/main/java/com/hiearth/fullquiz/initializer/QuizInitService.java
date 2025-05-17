package com.hiearth.fullquiz.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiearth.fullquiz.domain.Category;
import com.hiearth.fullquiz.domain.Quiz;
import com.hiearth.fullquiz.initializer.dto.QuizDto;
import com.hiearth.fullquiz.repository.CategoryRepository;
import com.hiearth.fullquiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizInitService {
    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;
    private final ObjectMapper objectMapper;
    private final ResourcePatternResolver resourcePatternResolver;

    @Transactional
    public void loadQuizData() throws IOException {

        if (quizRepository.count() == 0) {
            Resource[] files = resourcePatternResolver.getResources("classpath:quiz/*.json");

            for (Resource file : files) {
                String filename = file.getFilename(); // 예: "재활용_분리배출.json"
                String[] parts = filename.replace(".json", "").split("_");
                String rootName = parts[0]; // 재활용
                String chapterName = parts[1]; // 분리배출

                Category category = categoryRepository.findByName(chapterName)
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리 없음"));

                List<QuizDto> dtos = objectMapper.readValue(file.getInputStream(), new TypeReference<>() {});
                List<Quiz> quizzes = dtos.stream()
                        .map(dto -> dto.toEntity(category))
                        .toList();

                quizRepository.saveAll(quizzes);
            }
        }
    }
}
