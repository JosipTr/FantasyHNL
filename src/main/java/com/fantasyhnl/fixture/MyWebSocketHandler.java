package com.fantasyhnl.fixture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fantasyhnl.util.BaseController;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class MyWebSocketHandler implements WebSocketHandler {

    private final BaseController<Fixture, FixtureDto> controller;
    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());

    public MyWebSocketHandler(BaseController<Fixture, FixtureDto> controller) {
        this.controller = controller;
    }

    @Scheduled(fixedRate = 10000)
    private void sendDataToClients() {
        for (WebSocketSession session : sessions) {
            try {
                var fixtures = filterFixtures();
                checkDate(fixtures);
                var jsonData = ow.writeValueAsString(fixtures);
                session.sendMessage(new TextMessage(jsonData));
            } catch (Exception e) {
                // Handle exception
            }
        }
    }

    private void sentFixtureToClients() {
        for (WebSocketSession session : sessions) {
            try {
                var fixtures = filterFixtures();
                var jsonData = ow.writeValueAsString(fixtures);
                session.sendMessage(new TextMessage(jsonData));
            } catch (Exception e) {
                // Handle exception
            }
        }
    }

    private void updateFixture(int id) {
        controller.updateById(id);
    }

    private List<FixtureDto> fetchFixtures() {
        var responseEntity = controller.getAll();
        return responseEntity.getBody();
    }

    private FixtureDto fetchFixtureById(int id) {
        var responseEntity = controller.getById(id);
        return responseEntity.getBody();
    }

    private List<FixtureDto> filterFixtures() {
        var fixtures = fetchFixtures();
        List<FixtureDto> fixtureDtos = new ArrayList<>(Collections.emptyList());
        for (var fixture : fixtures) {
            if (!fixture.getRound().equals("Regular Season - 35")) continue;
            fixtureDtos.add(fixture);
        }
        return fixtureDtos;
    }

    private void checkDate(List<FixtureDto> fixtures) {
        for (var fixture : fixtures) {
            var instant = Instant.now();
            var localTimestamp = instant.getEpochSecond();  // Convert to Unix timestamp in seconds
//            ZonedDateTime zonedDateTime = ZonedDateTime.parse(date);
//            ZonedDateTime localZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Zagreb"));
//            LocalDateTime localDateTime = localZonedDateTime.toLocalDateTime();

            var startTimestamp = fixture.getTimestamp();
            var halfTimestamp = startTimestamp + (46 * 60);
            var pauseTimestamp = halfTimestamp + (16 * 60);
            var fullTimestamp = halfTimestamp + (46 * 60);
            logger.info("Timestamp {}", halfTimestamp);
            logger.info("PRIJE");
            if (localTimestamp >= halfTimestamp && localTimestamp <= pauseTimestamp) continue;
            if ((localTimestamp >= startTimestamp && localTimestamp <= halfTimestamp) || (localTimestamp >= halfTimestamp && localTimestamp <= fullTimestamp)) {
                logger.info("DADADADADA");
                updateFixture(fixture.getId());
                logger.info("Updated");
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("WebSocket connection established");
        sessions.add(session);
        sentFixtureToClients();
        session.sendMessage(new TextMessage("Hello, client! Welcome to the WebSocket server."));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        logger.info("Received message: {}", message.getPayload());
        session.sendMessage(new TextMessage("Hello, client!"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info("WebSocket transport error: {}", exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("WebSocket connection closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
