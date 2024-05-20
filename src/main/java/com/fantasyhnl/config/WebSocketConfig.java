package com.fantasyhnl.config;

import com.fantasyhnl.fixture.*;
import com.fantasyhnl.util.BaseController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketService service;

    public WebSocketConfig(WebSocketService service) {
        this.service = service;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/my-websocket");
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new MyWebSocketHandler(service);
    }
}

