package com.hiearth.fullquiz.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckAnswerDTO {
    private Boolean isCorrect;
    private String userAnswer;
}
