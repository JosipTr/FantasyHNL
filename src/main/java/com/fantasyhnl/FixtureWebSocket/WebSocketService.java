package com.fantasyhnl.FixtureWebSocket;

import com.fantasyhnl.exception.EmptyListException;
import com.fantasyhnl.exception.InvalidIdException;
import com.fantasyhnl.fixture.Fixture;
import com.fantasyhnl.fixture.FixtureDto;
import com.fantasyhnl.fixture.FixtureRepository;
import com.fantasyhnl.fixture.FixtureResponse;
import com.fantasyhnl.player.PlayerRepository;
import com.fantasyhnl.team.TeamRepository;
import com.fantasyhnl.util.JsonToObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.fantasyhnl.util.Constants.emptyList;
import static com.fantasyhnl.util.Constants.fixtureDetailPath;

@Transactional
@Service
public class WebSocketService {
    private final FixtureRepository baseRepository;
    private final JsonToObjectMapper objectMapper;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public WebSocketService(FixtureRepository fixtureRepository, FixtureRepository baseRepository, JsonToObjectMapper objectMapper, PlayerRepository playerRepository, TeamRepository teamRepository, ModelMapper modelMapper) {
        this.baseRepository = baseRepository;
        this.playerRepository = playerRepository;
        this.objectMapper = objectMapper;
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    protected List<FixtureDto> getAll() {
        var results = baseRepository.findAll();
        if (results.isEmpty()) throw new EmptyListException(emptyList);
        return results.stream().map(this::convertToDto).toList();
    }

    public void updateById(int id) {
        baseRepository.findById(id).ifPresentOrElse(
                fixture -> {
                    var response = getFixtureResponse(fixture);
                    fixture.removeEvents();
                    for (var res : response) {
                        fixture.updateFixture(res);
                        setFixtureEvents(res, fixture);
//            setPlayerStatistic(res, fix);
                    }
                }, () -> {
                    throw new InvalidIdException("");
                }
        );

    }

    private List<FixtureResponse> getFixtureResponse(Fixture fixture) {
        var url = fixtureDetailPath + fixture.getId() + ".json";
        var body = readFromFile(url);
        var root = objectMapper.mapToRootObject(body, FixtureResponse.class);
        return root.getResponse();
    }


    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFixtureEvents(FixtureResponse res, Fixture fixture) {
        var events = res.getEvents();
        if (events == null) return;
        events.forEach(event -> {
            var teamId = event.getTeam().getId();
            var playerId = event.getPlayer().getId();
            var assistPlayerId = event.getAssist().getId();

            var playerOpt = playerRepository.findById(playerId);
            var assistPlayerOpt = playerRepository.findById(assistPlayerId);
            var teamOpt = teamRepository.findById(teamId);

            if (playerOpt.isPresent() && assistPlayerOpt.isPresent() && teamOpt.isPresent()) {
                var time = event.getTime();

                time.setFixture(fixture);
                event.setPlayer(playerOpt.get());
                event.setAssist(assistPlayerOpt.get());
                event.setTeam(teamOpt.get());
                event.setFixture(fixture);
                event.setTime(time);

                fixture.addEvent(event);
            }
        });
    }
    protected FixtureDto convertToDto(Fixture entity) {
        return modelMapper.map(entity, FixtureDto.class);
    }
}
