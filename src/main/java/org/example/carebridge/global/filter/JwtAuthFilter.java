package org.example.carebridge.global.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.carebridge.global.util.AuthenticationScheme;
import org.example.carebridge.global.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private static final List<String> WHITE_LIST =
            List.of("/api/users/login", "/api/users/signup-patient","/api/users/signup-doctor",
                    "/login/oauth2/code/google","/chat", "/chat/**",
                    "/login", "/signup",
                    "/api/payments/kakaopay", "/api/payments/kakaopay/success");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //화이트리스트 미 포함 시, 인증 수행
        if(!WHITE_LIST.contains(request.getRequestURI())) {
            this.authenticate(request);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(HttpServletRequest request) {
        String token = this.getTokenFromRequest(request);
        if(token == null) {
            token = this.getTokenFromCookie(request);
        }

        if(token != null && jwtUtil.validToken(token)) {
            String userId = this.jwtUtil.getUserName(token);

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);
            this.setAuthentication(request, userDetails);
        }
    }


    //쿠키에서 JWT 추출 메서드
    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            System.out.println("요청에서 쿠키를 찾을 수 없음.");
            return null;
        }
        return Arrays.stream(request.getCookies())
                //쿠키 이름이 "Authorization" 인 것을 가져옴
                .filter(cookie -> "Authorization".equals(cookie.getName()))
                //쿠키 값 가져오기.
                .map(cookie-> cookie.getValue())
                .findFirst()
                .orElse(null);
    }

    // Authorization 헤더에서 JWT 추출 메서드
    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String headerPrefix = AuthenticationScheme.generateType(AuthenticationScheme.BEARER);

        boolean tokenFound = StringUtils.hasText(bearerToken) && bearerToken.startsWith(headerPrefix);
        if(tokenFound) {
            return bearerToken.substring(headerPrefix.length());
        }
        return null;
    }

    //인증 객체 설정
    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
