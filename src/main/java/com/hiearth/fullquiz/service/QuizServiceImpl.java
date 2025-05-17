package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.domain.Category;
import com.hiearth.fullquiz.repository.CategoryRepository;
import com.hiearth.fullquiz.web.dto.response.CategoriesResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizSevice{

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoriesResponse> getCategories() {
        List<Category> topCategories = categoryRepository.findAll()
                .stream()
                .filter(c -> c.getParent() == null)  // 부모 없는 최상위 카테고리만
                .toList();

        return topCategories.stream()
                .map(this::toResponse)
                .toList();
    }

    private CategoriesResponse toResponse(Category parent) {
        List<CategoriesResponse> children = parent.getChildren().stream()
                .map(child -> new CategoriesResponse(
                        child.getId().intValue(),
                        child.getName(),
                        List.of()  // 자식의 자식이 더 필요하면 재귀 가능
                ))
                .toList();

        return new CategoriesResponse(parent.getId().intValue(), parent.getName(), children);
    }

}
