package org.example.carebridge.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.user.doctor.entity.DoctorLicense;
import org.example.carebridge.domain.user.doctor.entity.Portfolio;
import org.example.carebridge.domain.user.doctor.repository.DoctorLicenseRepository;
import org.example.carebridge.domain.user.doctor.repository.PortfolioRepository;
import org.example.carebridge.domain.user.dto.login.UserLoginRequestDto;
import org.example.carebridge.domain.user.dto.login.UserLoginResponseDto;
import org.example.carebridge.domain.user.dto.signup.UserDoctorSignupRequestDto;
import org.example.carebridge.domain.user.dto.signup.UserPatientSignupRequestDto;
import org.example.carebridge.domain.user.dto.signup.UserSignupRequestDto;
import org.example.carebridge.domain.user.dto.signup.UserSignupResponseDto;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.enums.UserRole;
import org.example.carebridge.domain.user.enums.UserStatus;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.util.AuthenticationScheme;
import org.example.carebridge.global.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// test

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final DoctorLicenseRepository doctorLicenseRepository;
    private final PortfolioRepository portfolioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserSignupResponseDto patientSignup(
            UserPatientSignupRequestDto requestDto) {

        User user = buildUser(requestDto);

        //유효성 검사
        Optional<User> findUser = userRepository.findByEmail(requestDto.getEmail());

        if (findUser.isPresent()) {
            if(findUser.get().getUserStatus() == UserStatus.DELETE
                    || findUser.get().getUserStatus() == UserStatus.ACTIVE) {
                throw new RuntimeException("이미 존재하는 유저 입니다.");
            }
        }

        userRepository.save(user);

        return new UserSignupResponseDto(user.getId(), user.getEmail(), user.getUserRole());

    }

    @Transactional
    public UserSignupResponseDto doctorSignup(
            UserDoctorSignupRequestDto requestDto) {

        //유효성 검사
        Optional<User> findUser = userRepository.findByEmail(requestDto.getEmail());

        if (findUser.isPresent()) {
            if(findUser.get().getUserStatus() == UserStatus.DELETE
                    || findUser.get().getUserStatus() == UserStatus.ACTIVE) {
                throw new RuntimeException("이미 존재하는 유저 입니다.");
            }
        }

        User user = buildUser(requestDto);
        user.updateUserRole(UserRole.DOCTOR);
        User savedUser = userRepository.save(user);

        DoctorLicense doctorLicense =
                new DoctorLicense(savedUser, requestDto.getHospitalName(), requestDto.getDoctorLicenseFile());
        DoctorLicense savedLicense = doctorLicenseRepository.save(doctorLicense);

        Portfolio portfolio = new Portfolio(savedLicense, requestDto.getPortfolio());
        portfolioRepository.save(portfolio);

        return new UserSignupResponseDto(savedUser.getId(), savedUser.getEmail(), savedUser.getUserRole());

    }

    //Login
    @Transactional
    public UserLoginResponseDto login(
            @Valid UserLoginRequestDto requestDto, HttpServletResponse response) {

        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmailOrElseThrow(email);

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호 오류");
        }

        Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String accessToken = this.jwtUtil.generateToken(auth);
        log.info("토큰 생성 : {}" , accessToken);

        ResponseCookie cookie = ResponseCookie.from("Authorization", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return new UserLoginResponseDto(user.getId(), user.getEmail(),
                "login Success", AuthenticationScheme.BEARER.getName());


    }

    private User buildUser(UserSignupRequestDto userSignupRequestDto) {
        return User.builder()
                .email(userSignupRequestDto.getEmail())
                .password(getEncodingPassword(userSignupRequestDto.getPassword()))
                .userName(userSignupRequestDto.getName())
                .phone_num(userSignupRequestDto.getPhone())
                .address(userSignupRequestDto.getAddress())
                .birthday(userSignupRequestDto.getBirth())
                .profile_image_url(userSignupRequestDto.getProfileImage())
                .build();
    }

    private String getEncodingPassword(String password) {
        return passwordEncoder.encode(password);
    }


}

