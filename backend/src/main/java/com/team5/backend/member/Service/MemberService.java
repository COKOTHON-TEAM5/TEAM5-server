package com.team5.backend.member.Service;

import com.team5.backend.exception.NotFoundException;
import com.team5.backend.exception.UnauthorizedException;
import com.team5.backend.member.domain.MemberEntity;
import com.team5.backend.member.dto.CheckUsernameResponse;
import com.team5.backend.member.dto.LoginRequest;
import com.team5.backend.member.dto.LoginResponse;
import com.team5.backend.member.dto.SignupRequest;
import com.team5.backend.member.repository.MemberRepository;
import com.team5.backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    //DTO 받아서 entity로 변환 후 다시 DTO로 반환하는 회원가입 메소드
    public LoginResponse signup(SignupRequest requestDto) {
        MemberEntity memberEntity = MemberEntity.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .nickname(requestDto.getNickname())
                .build(); //Entity로 변환 완료

        memberRepository.save(memberEntity);

        LoginResponse loginResponse = new LoginResponse(); //Entity에서 getter로 dto 만들기
        loginResponse.setUsername(memberEntity.getUsername());
        loginResponse.setNickname(memberEntity.getNickname());

        MemberEntity byUsername = memberRepository.findByUsername(requestDto.getUsername())
                .orElseThrow( () -> new NotFoundException("Username not found"));
        //DB에서 닉네임으로 아이디 조회
        loginResponse.setToken(jwtUtil.generateToken( byUsername.getId(), requestDto.getUsername()));

        return loginResponse;
    }

    //DTO (username, password) 받아서 entity로 변환 후 다시 DTO로 반환하는 로그인 메소드
    public LoginResponse login(LoginRequest requestDto) {
        MemberEntity memberEntity = MemberEntity.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .build(); //Entity로 변환 완료

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(memberEntity.getUsername());

        MemberEntity byUsername = memberRepository.findByUsername(requestDto.getUsername())
                .orElseThrow( () -> new NotFoundException("Username not found"));
        loginResponse.setNickname(byUsername.getNickname());
        //DB에서 닉네임으로 아이디 조회

        if (!(byUsername.getPassword().equals(requestDto.getPassword()))) {
            throw new UnauthorizedException("Wrong password");
        }

        loginResponse.setToken(jwtUtil.generateToken( byUsername.getId(), requestDto.getUsername()));
        return loginResponse;
    }

    // 아이디 중복 확인
    public CheckUsernameResponse isUsernameTaken(String username) {
        CheckUsernameResponse checkUsernameResponse = new CheckUsernameResponse();

        if (memberRepository.countByUsername(username) == 0) {
            checkUsernameResponse.setUniqueness(true);
        }
        else checkUsernameResponse.setUniqueness(false);
        return checkUsernameResponse;
    }
}
