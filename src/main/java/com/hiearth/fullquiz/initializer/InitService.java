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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitService {

    private final CategoryInitService categoryInitService;
    private final QuizInitService quizInitService;

    @PostConstruct
    public void init() throws IOException{
        categoryInitService.initCategory();
        quizInitService.loadQuizData();
    }


}
