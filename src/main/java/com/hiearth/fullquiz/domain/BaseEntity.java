package com.hiearth.fullquiz.domain;

import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

public class BaseEntity {

    @CreatedDate
    private LocalDateTime createdDate;

}
