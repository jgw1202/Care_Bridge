package org.example.carebridge.global.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.domain.user.service.UserService;
import org.example.carebridge.global.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {
        //
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        Long userId = user.getAttribute("id");
        String tryType = user.getAttribute("type");

        Map<String, Object> userInfo = new HashMap<>();

        //type = register 일 경우, 회원가입
        if (tryType.equals("register")) {
            userInfo.put("email", user.getAttribute("email"));
            userInfo.put("name", user.getAttribute("name"));
        }

        //type = login 일 경우, 로그인 진행
        else {
            userInfo.put("id", userId);
            String accessToken = jwtUtil.generateAccessToken(userId);
            userInfo.put("accessToken", accessToken);
            String refreshToken = jwtUtil.generateRefreshToken(userId);
            userInfo.put("refreshToken", refreshToken);
        }
        response.getWriter().write(new ObjectMapper().writeValueAsString(userInfo));
    }
}
