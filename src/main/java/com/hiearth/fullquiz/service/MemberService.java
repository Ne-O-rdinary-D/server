package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.domain.Member;
import com.hiearth.fullquiz.web.dto.MemberResponse;

public interface MemberService {
    MemberResponse findOrCreateMember(String nickname);

    Member findByNickname(String nickname);

    boolean existsByNickname(String nickname);
}
