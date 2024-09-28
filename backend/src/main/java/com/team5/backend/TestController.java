package com.team5.backend;

import com.team5.backend.member.domain.MemberEntity;
import com.team5.backend.member.repository.MemberRepository;
import com.team5.backend.response.StatusResponse;
import com.team5.backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;


    @GetMapping("/protected")
    public String protectedEndpoint(@RequestAttribute("username") String username) {
        return username;
    }

    @GetMapping("/token")
    public String tokenEndpoint() {
        return jwtUtil.generateToken(1L, "TokenTest");
    }

    @GetMapping("/member/test")
    public StatusResponse responsetest() {
        return StatusResponse.of(200);
    }

    @GetMapping("/member/login")
    public void dbTest() {
        memberRepository.save(MemberEntity.builder()
                .username("test")
                .password("test1234")
                .build());
    }

}
