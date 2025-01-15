package org.example.carebridge.domain.user.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.carebridge.domain.user.enums.OAuth;
import org.example.carebridge.domain.user.enums.UserRole;

@Getter
@AllArgsConstructor
public class UserSignupResponseDto {

    private Long id;

    private String email;

    private UserRole userRole;

    private OAuth oAuth;
}
