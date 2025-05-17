package com.hiearth.fullquiz.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContinueQuizResponse {
    private int currentIdx;
    private List<QuizResponse> quizList;
}
