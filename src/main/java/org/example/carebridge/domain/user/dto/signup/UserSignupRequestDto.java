package org.example.carebridge.domain.user.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
public class UserSignupRequestDto {

    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private Date birth;
    private String profileImage;
}
