package com.hiearth.fullquiz.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@MappedSuperclass // JPA에서 상속받는 클래스의 공통 필드들을 테이블 컬럼으로 인식하게 해주는 어노테이션
@Getter
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime createdDate;

}
