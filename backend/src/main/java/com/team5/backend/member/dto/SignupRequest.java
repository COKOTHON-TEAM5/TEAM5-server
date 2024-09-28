package com.team5.backend.member.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignupRequest {

    private String username;    // 사용자명
    private String nickname;    // 닉네임
    private String password;   //비밀번호

}
