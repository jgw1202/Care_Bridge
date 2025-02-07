package org.example.carebridge.global.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().permitAll()
                .simpDestMatchers("/pub/**").authenticated() // 메시지 전송 경로는 인증 필요
                .simpSubscribeDestMatchers("/sub/**").authenticated() // 퍼블릭 구독은 허용
                .anyMessage().permitAll(); // 나머지 차단
    }

    // test
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}

