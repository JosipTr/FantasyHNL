package com.fantasyhnl.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fantasyhnl.util.BaseController;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
public class MyWebSocketHandler implements WebSocketHandler {

    private final WebSocketService service;
    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public MyWebSocketHandler(WebSocketService service) {

        this.service = service;
    }

    @Scheduled(fixedRate = 5000)
    private void sendDataToClients() {
        if (sessions.isEmpty()) return;
        checkDate();
        sendFixtureToClients(filterFixtures());
    }

    private void sendFixtureToClients(List<FixtureDto> fixtures) {
        for (WebSocketSession session : sessions) {
            try {
                var jsonData = ow.writeValueAsString(fixtures);
                session.sendMessage(new TextMessage(jsonData));
            } catch (Exception e) {
                logger.error("Error sending fixture to client: {}", e.getMessage());
            }
        }
    }

    private List<FixtureDto> filterFixtures() {
        var myLocalDate = LocalDate.now(ZoneId.of("Europe/Zagreb"));
        var fixtures = service.getAll().stream()
                .sorted(Comparator.comparing(FixtureDto::getTimestamp))
                .toList();

        String round = fixtures.stream()
                .filter(fixture -> {
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(fixture.getDate());
                    ZonedDateTime localZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Zagreb"));
                    var localDate = localZonedDateTime.toLocalDate();
                    return !myLocalDate.isAfter(localDate);
                })
                .findFirst()
                .map(FixtureDto::getRound)
                .orElse("");

        return fixtures.stream()
                .filter(fixture -> round.equals(fixture.getRound()))
                .collect(Collectors.toList());
    }

    private void checkDate() {
        var fixtures = filterFixtures();
        var currentTimestamp = (int) Instant.now().getEpochSecond();

        for (var fixture : fixtures) {
            var fixtureTimestamp = (int) fixture.getTimestamp();
            var halfTimestamp = fixtureTimestamp + (46 * 60);
            var pauseTimestamp = halfTimestamp + (16 * 60);
            var fullTimestamp = halfTimestamp + (46 * 60);
            if (currentTimestamp >= halfTimestamp && currentTimestamp <= pauseTimestamp) continue;

            if ((currentTimestamp >= fixtureTimestamp && currentTimestamp <= halfTimestamp) ||
                    (currentTimestamp >= halfTimestamp && currentTimestamp <= fullTimestamp)) {
                service.updateById(fixture.getId());
                logger.info("Fixture with ID {} updated", fixture.getId());
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("WebSocket connection established");
        sessions.add(session);
        sendFixtureToClients(filterFixtures());
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
