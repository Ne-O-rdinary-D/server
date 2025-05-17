package com.hiearth.fullquiz.service;

import com.hiearth.fullquiz.domain.Member;

public interface MemberService {
    Member findOrCreateMember(String nickname);

    Member findByNickname(String nickname);
}
