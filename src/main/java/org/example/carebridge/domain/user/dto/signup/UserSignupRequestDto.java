package org.example.carebridge.domain.user.dto.signup;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.carebridge.domain.user.enums.OAuth;

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

    @JsonProperty
    private OAuth oAuth;
}
