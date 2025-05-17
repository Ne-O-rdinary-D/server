package com.hiearth.fullquiz.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        name = "quiz_progress",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "category_id"})
        }
)
public class QuizProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long categoryId;

    private Integer currentIndex;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "quiz_progress_quiz",
            joinColumns = @JoinColumn(name = "quiz_progress_id")
    )
    @Column(name = "quiz_id")
    private List<Long> quizIds = new ArrayList<>();

    private boolean isCompleted;

    @Builder.Default
    @OneToMany(mappedBy = "quizProgress", cascade = CascadeType.ALL)
    private List<QuizAnswer> quizAnswers = new ArrayList<>();

    public void solve(String userAnswer, boolean isCorrect) {
        if(currentIndex >= quizIds.size()){
            isCompleted = true;
        }
        else{
            QuizAnswer quizAnswer = quizAnswers.get(currentIndex++);
            quizAnswer.setUserAnswer(userAnswer);
            quizAnswer.setIsCorrect(isCorrect);
        }
    }
}
