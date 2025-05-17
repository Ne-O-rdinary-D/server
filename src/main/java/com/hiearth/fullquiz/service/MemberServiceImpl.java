package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.domain.Member;
import com.hiearth.fullquiz.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Member findOrCreateMember(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .nickname(nickname)
                                .numOfSolvedQuiz(0)
                                .build()
                ));
    }
}
