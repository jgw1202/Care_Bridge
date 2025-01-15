package org.example.carebridge.domain.user.dto.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginRequestDto {
    private Long id;

    private String email;

    private String password;
}
