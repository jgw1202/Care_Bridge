package org.example.carebridge.global.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.global.dto.TokenResponseDto;
import org.example.carebridge.global.entity.RefreshToken;
import org.example.carebridge.global.repository.RefreshTokenRepository;
import org.example.carebridge.global.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TokenResponseDto generateAndStoreRefreshToken(User user) {

        String accessToken = jwtUtil.generateAccessToken(user.getId());
        String refreshTokenKey = jwtUtil.generateRefreshToken(user.getId());
        RefreshToken oldRefreshToken = refreshTokenRepository.findByUserOrElseThrow(user);
        oldRefreshToken.updateRefreshToken(refreshTokenKey);

        return new TokenResponseDto(accessToken, refreshTokenKey);
    }

    public void deleteRefreshToken(User user) {
        Long userId = user.getId();
        refreshTokenRepository.deleteRefreshTokenById(userId);
    }

}
