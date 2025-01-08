package org.example.carebridge.domain.user.controller;

//import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.user.dto.login.UserLoginRequestDto;
import org.example.carebridge.domain.user.dto.login.UserLoginResponseDto;
import org.example.carebridge.domain.user.dto.signup.UserDoctorSignupRequestDto;
import org.example.carebridge.domain.user.dto.signup.UserPatientSignupRequestDto;
import org.example.carebridge.domain.user.dto.signup.UserSignupResponseDto;
import org.example.carebridge.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //환자 회원가입
    @PostMapping("/signup-patient")
    public ResponseEntity<UserSignupResponseDto> createPatient(
            @RequestBody UserPatientSignupRequestDto userPatientSignupRequestDto) {

        UserSignupResponseDto userSignupResponseDto = userService.patientSignup(userPatientSignupRequestDto);

        return new ResponseEntity<>(userSignupResponseDto, HttpStatus.CREATED);
    }

    //의사 회원가입
    @PostMapping("/signup-doctor")
    public ResponseEntity<UserSignupResponseDto> createDoctor(
             @RequestBody UserDoctorSignupRequestDto userDoctorSignupRequestDto) {

        UserSignupResponseDto userSignupResponseDto = userService.doctorSignup(userDoctorSignupRequestDto);

        return new ResponseEntity<>(userSignupResponseDto, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(
             @RequestBody UserLoginRequestDto userLoginRequestDto,
             HttpServletResponse response) {

        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto, response);

        return new ResponseEntity<>(userLoginResponseDto, HttpStatus.OK);
    }

    //소셜 로그인 --보류

    //프로필 사진 등록

    //의사 면허증 등록

    //회원 탈퇴

    //사용자 정보 수정

    //로그아웃
}
