package org.example.carebridge.domain.user.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    private Long id;

    private String email;

    private String accessToken;

    private String refreshToken;

    public UserLoginResponseDto(Long id, String email, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
