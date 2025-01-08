package org.example.carebridge.domain.user.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    private Long id;

    private String email;

    private String message;

    private String tokenAuthScheme;

    public UserLoginResponseDto(Long id, String email, String message, String tokenAuthScheme) {
        this.id = id;
        this.email = email;
        this.message = message;
        this.tokenAuthScheme = tokenAuthScheme;
    }
}
