package org.example.carebridge.global.controller;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.global.auth.UserDetailsImple;
import org.example.carebridge.global.dto.TokenRequestDto;
import org.example.carebridge.global.dto.TokenResponseDto;
import org.example.carebridge.global.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refresh-token")
public class AuthController {

    private final TokenService tokenService;

    //Access Token 재발급 로직
    @PostMapping("/regenerations")
    public ResponseEntity<TokenResponseDto> regenerateToken(
            @AuthenticationPrincipal UserDetailsImple userDetailsImple) {

        User user = userDetailsImple.getUser();
        TokenResponseDto tokenResponseDto = tokenService.generateAndStoreRefreshToken(user);

        return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);


    }
}
