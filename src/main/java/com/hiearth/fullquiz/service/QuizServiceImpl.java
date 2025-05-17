package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.domain.*;
import com.hiearth.fullquiz.domain.mapping.MemberQuiz;
import com.hiearth.fullquiz.global.error.ErrorType;
import com.hiearth.fullquiz.global.exception.FullquizException;
import com.hiearth.fullquiz.repository.*;
import com.hiearth.fullquiz.service.request.CheckAnswerDTO;
import com.hiearth.fullquiz.web.dto.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
                        .isCorrect(checkAnswerDTO.getIsCorrect())
                        .build()
        );

        QuizProgress quizProgress = quizProgressRepository
                .findByMemberIdAndCategoryId(memberId, checkAnswerDTO.getCategoryId())
                .orElseThrow();

        quizProgress.solve(checkAnswerDTO.getUserAnswer(), checkAnswerDTO.getIsCorrect());
    }

    @Override
    public List<QuizResponse> resumeQuiz(Long quizProgressId) {
        QuizProgress quizProgress = quizProgressRepository.findById(quizProgressId).orElseThrow();
        List<Quiz> quizzes = quizRepository.findAllById(quizProgress.getQuizIds());

        List<QuizResponse> responses = new ArrayList<>();

        for(int i = 0; i < quizzes.size(); i++) {
            responses.add(
                    QuizResponse.forResume(quizzes.get(i), quizProgress.getQuizAnswers().get(i))
            );
        }
        return responses;
    }

    @Override
    public List<StatusResponse> getMyStatus(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new FullquizException(ErrorType.MEMBER_NOT_FOUND));

        List<Category> topCategories = categoryRepository.findAll().stream()
                .filter(c -> c.getParent() == null)
                .toList();

        return topCategories.stream()
                .map(parent -> {
                    List<StatusResponse.ChildrenCategories> childStatuses = parent.getChildren().stream()
                            .map(child -> {
                                Optional<QuizProgress> progressOpt = quizProgressRepository
                                        .findByMemberIdAndCategoryId(member.getId(), child.getId());

                                Status status = mapProgressToStatus(progressOpt);

                                return new StatusResponse.ChildrenCategories(
                                        child.getId().intValue(),
                                        child.getName(),
                                        status
                                );
                            })
                            .toList();

                    StatusResponse response = new StatusResponse();
                    response.setCategoryId(parent.getId().intValue());
                    response.setParentTitle(parent.getName());
                    response.setChildrenCategoriesList(childStatuses);
                    return response;
                })
                .toList();
    }

    private Status mapProgressToStatus(Optional<QuizProgress> progressOpt) {
        if (progressOpt.isEmpty()) {
            return Status.INACTIVATED;
        }

        QuizProgress progress = progressOpt.get();
        if (progress.isCompleted()) {
            return Status.ENDED;
        }

        if (progress.getCurrentIndex() > 0) {
            return Status.ACTIVATED;
        }

        return Status.INACTIVATED;
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

}