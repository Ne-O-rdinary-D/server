package com.hiearth.fullquiz.repository;

import com.hiearth.fullquiz.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll(); // 테스트 독립성 보장
    }

    @Test
    void 로그인_하면_회원이_없으면_생성되고_리턴된다() throws Exception {
        // given
        String nickname = "testUser";
        String requestBody = """
        {
            "nickname": "%s"
        }
        """.formatted(nickname);

        // when & then
        mockMvc.perform(post("/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void 로그인_하면_이미_있는_회원이면_재사용된다() throws Exception {
        // given
        String nickname = "existingUser";
        Member saved = memberRepository.save(Member.builder()
                .nickname(nickname)
                .numOfSolvedQuiz(0)
                .build());

        String requestBody = """
        {
            "nickname": "%s"
        }
        """.formatted(nickname);

        // when & then
        mockMvc.perform(post("/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.id").value(saved.getId()));
    }
}