package com.team5.backend.member.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequest {
    private String username;    // 사용자명
    private String password;    //비밀번호
}
