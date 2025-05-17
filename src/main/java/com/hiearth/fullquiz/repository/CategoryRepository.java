package com.hiearth.fullquiz.repository;

import com.hiearth.fullquiz.domain.Category;
import com.hiearth.fullquiz.global.error.ErrorType;
import com.hiearth.fullquiz.global.exception.FullquizException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    default List<Category> getAllCategories() {
        return findAll();
    }
}
