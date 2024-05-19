//package com.fantasyhnl.config;
//
//import com.fantasyhnl.fixture.Fixture;
//import com.fantasyhnl.fixture.FixtureDto;
//import com.fantasyhnl.fixture.FixtureService;
//import com.fantasyhnl.fixture.MyWebSocketHandler;
//import com.fantasyhnl.util.BaseController;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//    private final BaseController<Fixture, FixtureDto> baseController;
//
//    public WebSocketConfig(BaseController<Fixture, FixtureDto> baseController) {
//        this.baseController = baseController;
//    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler(), "/my-websocket");
//    }
//
//    @Bean
//    public WebSocketHandler webSocketHandler() {
//        return new MyWebSocketHandler(baseController);
//    }
//}
//
