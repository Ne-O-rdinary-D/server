package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.domain.Member;
import com.hiearth.fullquiz.global.error.ErrorType;
import com.hiearth.fullquiz.global.exception.FullquizException;
import com.hiearth.fullquiz.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Member findOrCreateMember(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isPresent()) {
            throw new FullquizException(ErrorType.NICKNAME_DUPLICATED);
        }
        return memberRepository.save(
                Member.builder()
                        .nickname(nickname)
                        .numOfSolvedQuiz(0)
                        .build()
        );
    }

    @Override
    public Member findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).orElseThrow();
    }
}
