package org.example.carebridge.global.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.WrongAccessException;
import org.example.carebridge.global.util.JwtUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        log.info("WebSocketInterceptor - 메시지 타입: {}", accessor.getCommand());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // "Bearer " 제거
                log.info("토큰 추출: {}", token);

                try {
                    log.info("검증 시작");  // ✅ 이 로그가 찍히지 않는다면 내부에서 예외 발생 가능
                    UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) jwtUtil.getAuthentication(token);

                    if (authentication != null) {
                        accessor.setUser(authentication);
                        log.info("JWT 인증 성공. 사용자: {}", authentication.getName());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("인증 정보 : {}", SecurityContextHolder.getContext().getAuthentication());
                    } else {
                        log.error("JWT 인증 실패.");
                        return null; // WebSocket 연결 차단
                    }
                } catch (Exception e) {
                    log.error("JWT 검증 중 예외 발생", e);
                    return null; // WebSocket 연결 차단
                }
            } else {
                log.error("Authorization 헤더 없음.");
                return null; // WebSocket 연결 차단
            }
        }
        return message;
    }
}
