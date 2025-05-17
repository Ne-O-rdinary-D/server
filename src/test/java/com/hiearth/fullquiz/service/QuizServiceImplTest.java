package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.domain.Member;
import com.hiearth.fullquiz.repository.MemberRepository;
import com.hiearth.fullquiz.service.request.CheckAnswerDTO;
import com.hiearth.fullquiz.web.dto.QuizResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuizServiceImplTest {

    @Autowired QuizSevice quizSevice;
    @Autowired
    MemberRepository memberRepository;



    @DisplayName("")
    @Test
    @Rollback(false)
    void test(){

        //given
        Member member = Member.builder()
                .nickname("nickname")
                .numOfSolvedQuiz(0)
                .build();
        memberRepository.save(member);

        //when

        //then
    }


    @DisplayName("")
    @Test
    void getQuizzes() {

        //given
        Member member = Member.builder()
                .nickname("nickname")
                .numOfSolvedQuiz(0)
                .build();
        memberRepository.save(member);

        //when
        List<QuizResponse> responses = quizSevice.getQuizzes(member.getId(), "재활용의 여정");

        //then
    }


    @DisplayName("")
    @Test
    @Rollback(false)
    void checkAnswer(){

        //given
        Member member = Member.builder()
                .nickname("nickname")
                .numOfSolvedQuiz(0)
                .build();

        memberRepository.save(member);

        CheckAnswerDTO checkAnswerDTO = new CheckAnswerDTO(true, 4L, "O");

        quizSevice.getQuizzes(member.getId(), "재활용의 여정");

        //when
        quizSevice.checkAnswer(1L, member.getId(), checkAnswerDTO);

        //then
    }


    @DisplayName("")
    @Test
    void resumeQuiz(){

        //given
        Member member = Member.builder()
                .nickname("nickname")
                .numOfSolvedQuiz(0)
                .build();

        memberRepository.save(member);

        quizSevice.getQuizzes(member.getId(), "재활용의 여정");

        CheckAnswerDTO checkAnswerDTO1 = new CheckAnswerDTO(true, 4L, "O");

        CheckAnswerDTO checkAnswerDTO2 = new CheckAnswerDTO(true, 4L, "O");

        quizSevice.checkAnswer(1L, member.getId(), checkAnswerDTO1);
        quizSevice.checkAnswer(2L, member.getId(), checkAnswerDTO2);

        //when
        List<QuizResponse> responses = quizSevice.resumeQuiz(member.getId());

        //then
    }
}