package org.example.carebridge.global.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;

@Configuration
public class SecurityWebSocketConfig{

    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.nullDestMatcher().permitAll()
                .simpDestMatchers("/pub/**").permitAll()
                .simpSubscribeDestMatchers("/sub/**").permitAll()
                .anyMessage().denyAll();
    }

    protected boolean sameOriginDisabled() {
        return true;
    }
}
