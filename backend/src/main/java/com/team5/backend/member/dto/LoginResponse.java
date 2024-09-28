package com.team5.backend.member.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginResponse {
    private String username;    // 사용자명
    private String nickname;    // 닉네임
    private String token; //토큰
}
