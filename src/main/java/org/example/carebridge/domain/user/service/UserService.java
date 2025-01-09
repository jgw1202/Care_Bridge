package org.example.carebridge.domain.user.service;

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
import org.example.carebridge.domain.user.dto.update.UserUpdateRequestDto;
import org.example.carebridge.domain.user.dto.update.UserUpdateResponseDto;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.enums.UserRole;
import org.example.carebridge.domain.user.enums.UserStatus;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.example.carebridge.global.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
            if (findUser.get().getUserStatus() == UserStatus.DELETE
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
            if (findUser.get().getUserStatus() == UserStatus.DELETE
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
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {

        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmailOrElseThrow(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호 오류");
        }

        String accessToken = this.jwtUtil.generateAccessToken(user);
        log.info("Access 토큰 생성 : {}", accessToken);

        String refreshToken = this.jwtUtil.generateRefreshToken(user);
        log.info("Refresh 토큰 생성 : {}", refreshToken);

        return new UserLoginResponseDto(user.getId(), user.getEmail(), accessToken, refreshToken);

    }

    //Update
    @Transactional
    public UserUpdateResponseDto updateUser(UserUpdateRequestDto requestDto, User user) {

        user.updateProfile(requestDto.getAddress(), requestDto.getPhone(), requestDto.getImageUrl());

        return new UserUpdateResponseDto(user);

    }

    private User buildUser(UserSignupRequestDto userSignupRequestDto) {
        return User.builder()
                .email(userSignupRequestDto.getEmail())
                .password(getEncodingPassword(userSignupRequestDto.getPassword()))
                .userName(userSignupRequestDto.getName())
                .phoneNum(userSignupRequestDto.getPhone())
                .address(userSignupRequestDto.getAddress())
                .birthday(userSignupRequestDto.getBirth())
                .profileImageUrl(userSignupRequestDto.getProfileImage())
                .build();
    }

    private String getEncodingPassword(String password) {
        return passwordEncoder.encode(password);
    }


}

