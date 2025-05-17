package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.web.dto.CategoriesResponse;

import java.util.List;

public interface QuizSevice {

    List<CategoriesResponse> getCategories();

}