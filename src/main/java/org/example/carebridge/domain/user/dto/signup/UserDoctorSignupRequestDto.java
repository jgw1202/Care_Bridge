package org.example.carebridge.domain.user.dto.signup;

import lombok.Getter;

import java.util.Date;

@Getter
public class UserDoctorSignupRequestDto extends UserSignupRequestDto {

    private String hospitalName;
    private String address;
    private String category;
    private String portfolio;
    private String doctorLicenseFile;

}
