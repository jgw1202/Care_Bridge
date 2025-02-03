package org.example.carebridge.global.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}") //테스트를위한 토큰시간 30분 설정(추후 수정 필요)
    private Long accessTokenExpiryMillis;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiryMillis;

    private final UserDetailsService userDetailsService;

    //Jwt 검증 로직
    public boolean validToken(String token) throws JwtException {
        try {
            return !this.tokenExpired(token);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported", e.getMessage());
        }
        return false;
    }


    //AccessToken 발급 로직
    public String generateAccessToken(Long id) {

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + accessTokenExpiryMillis);

        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
                .compact();
    }

    //RefreshToken 발급 로직
    public String generateRefreshToken(Long id) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + refreshTokenExpiryMillis);

        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
                .compact();
    }


    public String getUserName(String token) {
        Claims claims = this.getClaims(token);
        return claims.getSubject();
    }

    private boolean tokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.resolveClaims(token, Claims::getExpiration);
    }

    private <T> T resolveClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.getClaims(token);
        return claimsResolver.apply(claims);
    }



    private Claims getClaims(String token) {
        if(!StringUtils.hasText(token)) {
            throw new MalformedJwtException("토큰이 없거나, 검증되지 않았습니다.");
        }
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.getClaims(token);
        String username = claims.getSubject();
        log.info("인증된 사용자 id: {}", username);

        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserName(token));

        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}
