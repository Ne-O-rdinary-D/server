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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitService {

    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;
    private final ObjectMapper objectMapper;
    private final ResourcePatternResolver resourcePatternResolver;

    @PostConstruct
    public void init() throws IOException{
        initCategory();
        loadQuizData();
    }

    private void loadQuizData() throws IOException {

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

    private void initCategory() {
        if (categoryRepository.count() == 0) {

            Category rcategory1 = Category.builder()
                    .name("재활용")
                    .build();
            Category rcategory2 = Category.builder()
                    .name("기후변화")
                    .build();
            Category rcategory3 = Category.builder()
                    .name("멸종 위기 동물")
                    .build();

            categoryRepository.saveAll(List.of(rcategory1, rcategory2, rcategory3));

            // 1. 재활용
            Category category1 = Category.builder()
                    .name("재활용의 여정")
                    .parent(rcategory1)
                    .build();
            Category category2 = Category.builder()
                    .name("분리배출")
                    .parent(rcategory1)
                    .build();
            Category category3 = Category.builder()
                    .name("재활용 장소")
                    .parent(rcategory1)
                    .build();
            Category category4 = Category.builder()
                    .name("재활용 결과")
                    .parent(rcategory1)
                    .build();

            List<Category> categoriesForSave = List.of(category1, category2, category3, category4);

            categoriesForSave
                    .forEach(child -> rcategory1.getChildren().add(child));

            categoryRepository.save(rcategory1);

            // 2. 기후변화

            Category category5 = Category.builder()
                    .name("기후의 변화")
                    .parent(rcategory2)
                    .build();
            Category category6 = Category.builder()
                    .name("지구 오염")
                    .parent(rcategory2)
                    .build();
            Category category7 = Category.builder()
                    .name("피해")
                    .parent(rcategory2)
                    .build();
            Category category8 = Category.builder()
                    .name("변화")
                    .parent(rcategory2)
                    .build();

            List<Category> childWeatherCategories = List.of(category5, category6, category7, category8);

            childWeatherCategories
                    .forEach(child -> rcategory2.getChildren().add(child));
            categoryRepository.save(rcategory2);

            // 3. 멸종 위기 동물

            Category category9 = Category.builder()
                    .name("멸종 위기")
                    .parent(rcategory3)
                    .build();
            Category category10 = Category.builder()
                    .name("위협")
                    .parent(rcategory3)
                    .build();
            Category category11 = Category.builder()
                    .name("생태계")
                    .parent(rcategory3)
                    .build();
            Category category12 = Category.builder()
                    .name("실천")
                    .parent(rcategory3)
                    .build();

            List.of(category9, category10, category11, category12)
                    .forEach(child -> rcategory3.getChildren().add(child));

            categoryRepository.save(rcategory3);
        }
    }
}
