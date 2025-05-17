package com.hiearth.fullquiz.repository;

import com.hiearth.fullquiz.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;


    @DisplayName("")
    @Test
    @Rollback(false)
    void init() {

        //given
        Category category = Category.builder()
                .name("재활용")
                .build();

        //when
        categoryRepository.save(category);

        //then
    }


    @DisplayName("")
    @Test
    void test(){

        //given
        Category cate = categoryRepository.findByName("재활용의 여정").orElseThrow();

        //when

        //then
    }
}