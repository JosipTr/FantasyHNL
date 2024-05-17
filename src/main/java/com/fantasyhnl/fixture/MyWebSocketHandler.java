package com.fantasyhnl.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fantasyhnl.util.BaseController;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;

@Component
public class MyWebSocketHandler implements WebSocketHandler {

    private final BaseController<Fixture, FixtureDto> controller;

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter().forType(FixtureDto.class);

    private final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());

    public MyWebSocketHandler(BaseController<Fixture, FixtureDto> controller) {
        this.controller = controller;
    }

    @Scheduled(fixedRate = 5000)
    public void sendDataToClients() {
        for (WebSocketSession session : sessions) {
            try {
                var responseEntity = fetchFixtureFromDatabase();
                var jsonData = ow.writeValueAsString(responseEntity);
                session.sendMessage(new TextMessage(jsonData));
            } catch (Exception e) {
                // Handle exception
            }
        }
    }

    private FixtureDto fetchFixtureFromDatabase() {
        var responseEntity = controller.getById(1034680);
        return responseEntity.getBody();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established");
        sessions.add(session);
        session.sendMessage(new TextMessage("Hello, client! Welcome to the WebSocket server."));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
        session.sendMessage(new TextMessage("Hello, client!"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket transport error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("WebSocket connection closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
