package com.hiearth.fullquiz.web.dto;

import com.hiearth.fullquiz.domain.Category;
import com.hiearth.fullquiz.domain.QuizProgress;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QuizProgressDTO {

    private Long quizProgressId;

    private String parentCategory;

    private String currentCategory;

    private List<String> childCategories = new ArrayList<>();

    public static QuizProgressDTO create(Long quizProgressId, String currentCategory, List<Category> children) {
        QuizProgressDTO quizProgressDTO = new QuizProgressDTO();
        quizProgressDTO.currentCategory = currentCategory;
        quizProgressDTO.quizProgressId = quizProgressId;
        quizProgressDTO.parentCategory = children.get(0).getParent().getName();
        for (Category category : children) {
            quizProgressDTO.childCategories.add(category.getName());
        }
        return quizProgressDTO;
    }
}
