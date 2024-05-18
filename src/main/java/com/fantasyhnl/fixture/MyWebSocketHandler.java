package com.fantasyhnl.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fantasyhnl.util.BaseController;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MyWebSocketHandler implements WebSocketHandler {

    private final BaseController<Fixture, FixtureDto> controller;
    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());

    public MyWebSocketHandler(BaseController<Fixture, FixtureDto> controller) {
        this.controller = controller;
    }

    @Scheduled(fixedRate = 10000)
    private void sendDataToClients() {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                try {
                    var fixtures = filterFixtures();
                    checkDate(fixtures);
                    var jsonData = ow.writeValueAsString(fixtures);
                    session.sendMessage(new TextMessage(jsonData));
                } catch (Exception e) {
                    logger.error("Error sending data to client: {}", e.getMessage());
                }
            }
        }
    }

    private void sendFixtureToClients() {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                try {
                    var fixtures = filterFixtures();
                    var jsonData = ow.writeValueAsString(fixtures);
                    session.sendMessage(new TextMessage(jsonData));
                } catch (Exception e) {
                    logger.error("Error sending fixture to client: {}", e.getMessage());
                }
            }
        }
    }

    private void updateFixture(int id) {
        try {
            controller.updateById(id);
            logger.info("Updated fixture with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error updating fixture with ID {}: {}", id, e.getMessage());
        }
    }

    private List<FixtureDto> fetchFixtures() {
        var responseEntity = controller.getAll();
        return responseEntity.getBody();
    }

    private List<FixtureDto> filterFixtures() {
        return fetchFixtures().stream()
                .filter(fixture -> "Regular Season - 1".equals(fixture.getRound()))
                .collect(Collectors.toList());
    }

    private void checkDate(List<FixtureDto> fixtures) {
        var currentTimestamp = (int) Instant.now().getEpochSecond();

        for (var fixture : fixtures) {
            var fixtureTimestamp = (int) fixture.getTimestamp();
            var halfTimestamp = fixtureTimestamp + (46 * 60);
            var pauseTimestamp = halfTimestamp + (16 * 60);
            var fullTimestamp = halfTimestamp + (46 * 60);

            if (currentTimestamp >= halfTimestamp && currentTimestamp <= pauseTimestamp) continue;

            if ((currentTimestamp >= fixtureTimestamp && currentTimestamp <= halfTimestamp) ||
                    (currentTimestamp >= halfTimestamp && currentTimestamp <= fullTimestamp)) {
                updateFixture(fixture.getId());
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("WebSocket connection established");
        sessions.add(session);
        sendFixtureToClients();
        session.sendMessage(new TextMessage("Hello, client! Welcome to the WebSocket server."));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        logger.info("Received message: {}", message.getPayload());
        session.sendMessage(new TextMessage("Hello, client!"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("WebSocket transport error: {}", exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("WebSocket connection closed");
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
