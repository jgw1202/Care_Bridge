package org.example.carebridge.global.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.carebridge.global.auth.UserDetailsServiceImple;
import org.example.carebridge.global.exception.ExceptionType;
import org.example.carebridge.global.exception.WrongAccessException;
import org.example.carebridge.global.util.JwtUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(final Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authToken = accessor.getFirstNativeHeader("Authorization");

            if(authToken != null && jwtUtil.validToken(authToken)) {
                throw new WrongAccessException(ExceptionType.EXIST_USER); // 임시 예외처리
            }

            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) jwtUtil.getAuthentication(authToken);

            accessor.setUser(authentication);
        }

        return message;
    }

//    @Override
//    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        String sessionId = accessor.getSessionId();
//
//        switch (Objects.requireNonNull(accessor.getCommand())) {
//            case CONNECT -> log.info("CONNECT: " + message);
//            case CONNECTED -> log.info("CONNECTED: " + message);
//            case DISCONNECT -> log.info("DISCONNECT: " + message);
//            case SUBSCRIBE -> log.info("SUBSCRIBE: " + message);
//            case UNSUBSCRIBE -> log.info("UNSUBSCRIBE: " + sessionId);
//            case SEND -> log.info("SEND: " + sessionId);
//            case MESSAGE -> log.info("MESSAGE: " + sessionId);
//            case ERROR -> log.info("ERROR: " + sessionId);
//            default -> log.info("UNKNOWN: " + sessionId);
//        }
//    }
}
