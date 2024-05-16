package com.fantasyhnl.fixture;

import com.fantasyhnl.player.Player;
import com.fantasyhnl.player.PlayerDto;
import com.fantasyhnl.team.TeamRepository;
import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FixtureService {
    private final FixtureRepository fixtureRepository;
    private final JsonToObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final RestService restService;
    private final TeamRepository teamRepository;

    public FixtureService(FixtureRepository fixtureRepository, JsonToObjectMapper objectMapper, ModelMapper modelMapper, RestService restService, TeamRepository teamRepository) {
        this.fixtureRepository = fixtureRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.restService = restService;
        this.teamRepository = teamRepository;
    }

    public List<FixtureDto> getFixtures() {
        return fixtureRepository.findAll().stream().map(this::convertToDto).toList();
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
            var teams = res.getTeams();
            var home = teams.getHome();
            var away = teams.getAway();
            var savedAwayTeamOpt = teamRepository.findById(away.getId());
            var savedHomeTeamOpt = teamRepository.findById(home.getId());
            if (savedAwayTeamOpt.isEmpty() || savedHomeTeamOpt.isEmpty()) continue;
            var savedAwayTeam = savedAwayTeamOpt.get();
            var savedHomeTeam = savedHomeTeamOpt.get();
            home.setTeam(savedHomeTeam);
            away.setTeam(savedAwayTeam);
            home.setFixture(fixture);
            away.setFixture(fixture);
            teams.setFixture(fixture);
            teams.setAway(away);
            teams.setHome(home);
            status.setFixture(fixture);
            fixture.setStatus(status);
            fixture.setRound(round);
            fixture.setTeams(teams);
            fixtureRepository.save(fixture);
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
        try (FileWriter writer = new FileWriter("./src/main/resources/data/players/players" + id + ".json")) {
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
            return Files.readString(Path.of("./src/main/resources/data/players/players" + id + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FixtureDto convertToDto(Fixture fixture) {
        return modelMapper.map(fixture, FixtureDto.class);
    }
}
