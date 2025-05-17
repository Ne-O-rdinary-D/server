package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.domain.*;
import com.hiearth.fullquiz.domain.mapping.MemberQuiz;
import com.hiearth.fullquiz.repository.*;
import com.hiearth.fullquiz.service.request.CheckAnswerDTO;
import com.hiearth.fullquiz.web.dto.CategoriesResponse;
import com.hiearth.fullquiz.web.dto.ContinueQuizResponse;
import com.hiearth.fullquiz.web.dto.QuizResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizSevice{

    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;
    private final QuizProgressRepository quizProgressRepository;
    private final MemberQuizRepository memberQuizRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public List<QuizResponse> getQuizzes(Long memberId, String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        List<Quiz> quizzes = quizRepository.getQuizByCategoryId(category.getId());
        List<Quiz> selectedQuizzes = getRandomQuizzes(quizzes);

        QuizProgress quizProgress = QuizProgress.builder()
                .memberId(memberId)
                .categoryId(category.getId())
                .currentIndex(0)
                .isCompleted(false)
                .build();

        List<QuizAnswer> answers = new ArrayList<>();
        List<QuizResponse> responses = new ArrayList<>();

        for (int i = 0; i < selectedQuizzes.size(); i++) {
            Quiz quiz = selectedQuizzes.get(i);

            answers.add(QuizAnswer.builder()
                    .quizId(quiz.getId())
                    .indexNumber(i)
                    .isCorrect(null)
                    .userAnswer(null)
                    .answer(quiz.getAnswer())
                    .quizProgress(quizProgress)
                    .build());

            quizProgress.getQuizIds().add(quiz.getId());
            responses.add(QuizResponse.of(quiz, i));
        }

        quizProgress.getQuizAnswers().addAll(answers);
        quizProgressRepository.save(quizProgress);

        return responses;
    }

    @Override
    @Transactional
    public void checkAnswer(Long quizId, Long memberId, CheckAnswerDTO checkAnswerDTO) {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        memberQuizRepository.save(
                MemberQuiz.builder()
                        .member(member)
                        .quiz(quiz)
                        .isCorrect(checkAnswerDTO.isCorrect())
                        .build()
        );

        QuizProgress quizProgress = quizProgressRepository
                .findByMemberIdAndCategoryId(memberId, checkAnswerDTO.getCategoryId())
                .orElseThrow();

        quizProgress.solve(checkAnswerDTO.getUserAnswer(), checkAnswerDTO.isCorrect());
    }


    private List<Quiz> getRandomQuizzes(List<Quiz> quizzes) {
        Collections.shuffle(quizzes);

        List<Quiz> selectedQuizzes = quizzes.stream()
                .limit(5)
                .toList();
        return selectedQuizzes;
    }

    @Override
    public List<CategoriesResponse> getCategories() {
        List<Category> topCategories = categoryRepository.findAll()
                .stream()
                .filter(c -> c.getParent() == null)  // 부모 없는 최상위 카테고리만
                .toList();

        return topCategories.stream()
                .map(this::toResponse)
                .toList();
    }

    private CategoriesResponse toResponse(Category parent) {
        List<CategoriesResponse> children = parent.getChildren().stream()
                .map(child -> new CategoriesResponse(
                        child.getId().intValue(),
                        child.getName(),
                        List.of()  // 자식의 자식이 더 필요하면 재귀 가능
                ))
                .toList();

        return new CategoriesResponse(parent.getId().intValue(), parent.getName(), children);
    }

    @Override
    public ContinueQuizResponse continueQuiz(Long memberId, String category) {
        QuizProgress qp = quizProgressRepository.findByMemberIdAndCategoryId(memberId, categoryRepository.findByName(category).get().getId()).orElseThrow();
        int currentIdx = qp.getCurrentIndex();
        List<Long> quiz = qp.getQuizIds();
        List<QuizResponse> returnList = new ArrayList<>();
        for(int i=0;i<5;i++) {
            QuizResponse qr = QuizResponse.of(quizRepository.findById(quiz.get(i)).orElseThrow(), i);

            if(currentIdx >= i) {
                // 푼 문제
                qr.setCorrect(memberQuizRepository.findByMemberIdAndQuizId(memberId, qr.getId()).isCorrect());
            }
            returnList.add(qr);
        }

        return new ContinueQuizResponse(currentIdx, returnList);
    }

}