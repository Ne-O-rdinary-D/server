package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.domain.Category;
import com.hiearth.fullquiz.web.dto.response.CategoriesResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuizSevice {

    List<CategoriesResponse> getCategories();

}
