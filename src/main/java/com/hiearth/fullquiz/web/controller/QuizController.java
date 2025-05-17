package com.hiearth.fullquiz.web.controller;

import com.hiearth.fullquiz.global.response.ApiResponse;
import com.hiearth.fullquiz.service.QuizSevice;
import com.hiearth.fullquiz.web.dto.response.CategoriesResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizSevice quizService;

    @GetMapping("/getCategories")
    public ResponseEntity<ApiResponse<List<CategoriesResponse>>> getCategories () {
        return ApiResponse.ok(quizService.getCategories());
    }


}
