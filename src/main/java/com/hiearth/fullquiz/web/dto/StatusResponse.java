package com.hiearth.fullquiz.web.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {
    private int categoryId;
    private String parentTitle;
    private List<ChildrenCategories> childrenCategoriesList;

    @Getter
    @AllArgsConstructor
    public static class ChildrenCategories {
        private int category_id;
        private String category_name;
        private Status status;
    }
}
