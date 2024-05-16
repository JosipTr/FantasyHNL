package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.events.Events;
import com.fantasyhnl.fixture.events.EventsDto;
import com.fantasyhnl.fixture.goals.Goals;
import com.fantasyhnl.fixture.teams.TeamsDto;
import com.fantasyhnl.player.Player;
import com.fantasyhnl.player.PlayerDto;
import com.fantasyhnl.player.PlayerRepository;
import com.fantasyhnl.team.TeamRepository;
import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import jakarta.transaction.Transactional;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.fantasyhnl.util.Constants.detailedFixture;

@Service
public class FixtureService {
    private final FixtureRepository fixtureRepository;
    private final JsonToObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final RestService restService;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public FixtureService(FixtureRepository fixtureRepository, JsonToObjectMapper objectMapper, ModelMapper modelMapper, RestService restService, TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.fixtureRepository = fixtureRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.restService = restService;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<FixtureDto> getFixtures() {
        var fixtures = fixtureRepository.findAll();
        return fixtures.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public FixtureDto convertToDto(Fixture fixture) {
        // Map events to EventsDto before mapping Fixture to FixtureDto
        List<EventsDto> eventsDtoList = fixture.getEvents().stream()
                .map(events -> modelMapper.map(events, EventsDto.class))
                .collect(Collectors.toList());

        // Create a new FixtureDto object and set its events field
        FixtureDto fixtureDto = new FixtureDto();
        fixtureDto.setEvents(eventsDtoList);

        // Map individual fields from Fixture to FixtureDto
        fixtureDto.setId(fixture.getId());
        fixtureDto.setReferee(fixture.getReferee());
        fixtureDto.setTimezone(fixture.getTimezone());
        fixtureDto.setDate(fixture.getDate());
        fixtureDto.setTimestamp(fixture.getTimestamp());
        fixtureDto.setStatus(fixture.getStatus());
        fixtureDto.setRound(fixture.getRound());
        fixtureDto.setTeams(modelMapper.map(fixture.getTeams(), TeamsDto.class));
        fixtureDto.setGoals(fixture.getGoals());

        return fixtureDto;
    }



    public void addFixtures() {
        var body = readFromFile();
        var root = objectMapper.mapToRootObject(body, FixtureResponse.class);
        var response = root.getResponse();
        for (var res : response) {
            var fixture = res.getFixture();
            var status = fixture.getStatus();
            var league = res.getLeague();
            var round = league.getRound();
            var goals = res.getGoals();

            goals.setFixture(fixture);
            status.setFixture(fixture);

            fixture.setStatus(status);
            fixture.setRound(round);
            fixture.setGoals(goals);
            setFixtureTeams(res);
            fixtureRepository.save(fixture);
        }
    }

    @Transactional
    public void updateFixtures() {
        var fixtures = fixtureRepository.findAll();
        for (var fix : fixtures) {
            if (fix.getId() >= 1034786) break;
            var body = readFromOtherFile(fix.getId());
            var root = objectMapper.mapToRootObject(body, FixtureResponse.class);
            var response = root.getResponse();
            for (var res : response) {
                var fixtureId = res.getFixture().getId();
                var fixtureOpt = fixtureRepository.findById(fixtureId);
                if (fixtureOpt.isEmpty()) continue;
                var fixture = fixtureOpt.get();
                setFixtureEvents(res, fixture);
            }
        }
    }

    private void waitSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(String body, int id) {
        try (FileWriter writer = new FileWriter("./src/main/resources/data/fixture/fixture.json")) {
            writer.write(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToOtherFile(String body, int id) {
        try (FileWriter writer = new FileWriter("./src/main/resources/data/fixture_detail/fixture_detail" + id + ".json")) {
            writer.write(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromFile() {
        try {
            return Files.readString(Path.of("./src/main/resources/data/fixture/fixture.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromOtherFile(int id) {
        try {
            return Files.readString(Path.of("./src/main/resources/data/fixture_detail/fixture_detail" + id + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFixtureTeams(FixtureResponse res) {
        var fixture = res.getFixture();
        var teams = res.getTeams();
        var home = teams.getHome();
        var away = teams.getAway();
        var savedAwayTeamOpt = teamRepository.findById(away.getId());
        var savedHomeTeamOpt = teamRepository.findById(home.getId());
        if (savedAwayTeamOpt.isPresent() && savedHomeTeamOpt.isPresent()) {
            var savedAwayTeam = savedAwayTeamOpt.get();
            var savedHomeTeam = savedHomeTeamOpt.get();
            home.setTeam(savedHomeTeam);
            away.setTeam(savedAwayTeam);
            home.setFixture(fixture);
            away.setFixture(fixture);
            teams.setFixture(fixture);
            teams.setAway(away);
            teams.setHome(home);
            fixture.setTeams(teams);
        }
    }

    private void setFixtureEvents(FixtureResponse res, Fixture fixture) {
        var events = res.getEvents();
        if (events == null) return;
        for (var event : events) {
            var time = event.getTime();
            var teamId = event.getTeam().getId();
            var playerId = event.getPlayer().getId();
            var assistPlayerId = event.getAssist().getId();

            var player = playerRepository.findById(playerId);
            var assistPlayer = playerRepository.findById(assistPlayerId);
            var team = teamRepository.findById(teamId);

            if (assistPlayer.isEmpty() || player.isEmpty()) continue;
            var assistPlayerP = assistPlayer.get();
            var playerp = player.get();
            event.setPlayer(playerp);
            event.setAssist(assistPlayerP);
            team.ifPresent(event::setTeam);

            time.setFixture(fixture);
            event.setFixture(fixture);
            event.setTime(time);
            fixture.addEvent(event);
        }
    }
}
