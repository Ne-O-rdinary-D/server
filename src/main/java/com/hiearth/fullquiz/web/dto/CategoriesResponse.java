package com.hiearth.fullquiz.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriesResponse {

    private int category_id;
    private String category;
    private List<CategoriesResponse> children;

    public CategoriesResponse(int category_id, String category, List<CategoriesResponse> children) {
        this.category_id = category_id;
        this.category = category;
        this.children = children;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public List<CategoriesResponse> getChildren() {
        return children;
    }
}