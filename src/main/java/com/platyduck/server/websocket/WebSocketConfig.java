package com.platyduck.server.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        // 구글링에서는 setAllowedOrigins("*") : 모든 도메인에 대해 접근 허용 를 사용하였으나
        // spring boot 에서는 해당 메서드 사용시 오류가 나고
        // setAllowedOriginPatterns("*") : 모든 도메인에 대해 접근 허용
        // setAllowedOriginPatterns 메서드를 권장함
        registry.addHandler(chatHandler, "/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setClientLibraryUrl("https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js");
    }
}
