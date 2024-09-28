package com.team5.backend.member.controller;

import com.team5.backend.member.Service.MemberService;
import com.team5.backend.member.dto.*;
import com.team5.backend.response.DataResponse;
import com.team5.backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping("/signup") //회원가입 메소드
    public DataResponse<LoginResponse> signup(@RequestBody SignupRequest requestDto) {

        LoginResponse responseDto = memberService.signup(requestDto);
        // 등록된 멤버 정보를 다시 DTO로 변환해서 반환 (비밀번호 제외)
        return DataResponse.of(responseDto);
    }

    @PostMapping("/login") //로그인 메소드
    @ResponseBody
    public DataResponse<LoginResponse> login(@RequestBody LoginRequest requestDto) {

        LoginResponse responseDto = memberService.login(requestDto);

        return DataResponse.of(responseDto);
    }

    @GetMapping("/check-username") //아이디 중복확인
    @ResponseBody
    public DataResponse<Boolean> checkNickname(@RequestParam String username) {
        Boolean isusernameTaken = memberService.isUsernameTaken(username);
        return DataResponse.of(isusernameTaken);
    }
}