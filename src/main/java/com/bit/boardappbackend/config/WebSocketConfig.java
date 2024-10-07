package com.bit.boardappbackend.config;

import com.bit.boardappbackend.handler.ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트에서 구독할 경로 설정 (브로커)
        config.enableSimpleBroker("/topic");
        // 클라이언트가 메시지를 보낼 때 사용할 경로 설정
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 WebSocket을 연결할 엔드포인트 설정
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000").withSockJS();
    }
}