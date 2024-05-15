package com.fantasyhnl.fixture;

import com.fantasyhnl.exception.EmptyListException;
import com.fantasyhnl.team.Team;
import com.fantasyhnl.team.TeamDto;
import com.fantasyhnl.team.TeamRepository;
import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.fantasyhnl.util.Constants.*;
import static com.fantasyhnl.util.Constants.detailedFixture;

@Service
public class FixtureService {
    private final FixtureRepository fixtureRepository;
    private final JsonToObjectMapper objectMapper;
    private final RestService restService;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public FixtureService(FixtureRepository fixtureRepository, JsonToObjectMapper objectMapper, RestService restService, TeamRepository teamRepository, ModelMapper modelMapper) {
        this.fixtureRepository = fixtureRepository;
        this.objectMapper = objectMapper;
        this.restService = restService;
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    public List<FixtureDto> getFixtures() {
        var fixtures = fixtureRepository.findAll();
        if (fixtures.isEmpty()) {
            throw new EmptyListException(emptyList);
        } else {
            return fixtures.stream().map(this::convertToDto).toList();
        }
    }

    public void deleteFixtures() {
        fixtureRepository.deleteAll();
    }

//    @Transactional
//    public void addFixtures() {
//        var body = restService.getResponseBody(fixturesUrl);
//        writeToFile(body);
//        var root = objectMapper.mapToRootObject(body, FixtureResponse.class);
//        var response = root.getResponse();
//        for (var res : response) {
//            var awayTeam = res.getTeams().getAway();
//            var homeTeam = res.getTeams().getHome();
//            System.out.println(awayTeam.getId());
//            System.out.println(homeTeam.getId());
//            var savedAwayTeam = teamRepository.getReferenceById(awayTeam.getId());
//            var savedHomeTeam = teamRepository.getReferenceById(homeTeam.getId());
//            var fixture = res.getFixture();
//            var goals = res.getGoals();
//            var league = res.getLeague();
////            var score = res.getScore();
//            awayTeam.setFixture(fixture);
//            awayTeam.setTeam(savedAwayTeam);
//            homeTeam.setFixture(fixture);
//            homeTeam.setTeam(savedHomeTeam);
//            fixture.setAwayGoals(goals.getAway());
//            fixture.setHomeGoals(goals.getHome());
//            fixture.setHomeTeam(homeTeam);
//            fixture.setAwayTeam(awayTeam);
//            fixture.setRound(league.getRound());
//            fixtureRepository.save(fixture);
//        }
//    }

    //    @Transactional
    public void addFixtures() {
        var body = readFromFile();
        var root = objectMapper.mapToRootObject(body, FixtureResponse.class);
        var response = root.getResponse();
        for (var res : response) {
            var fixture = res.getFixture();
            var status = fixture.getStatus();
            var goals = res.getGoals();
            var away = res.getTeams().getAway();
            var home = res.getTeams().getHome();

            var awayTeamOpt = teamRepository.findById(away.getId());
            var homeTeamOpt = teamRepository.findById(home.getId());
            if (awayTeamOpt.isEmpty() || homeTeamOpt.isEmpty()) continue;
            away.setFixture(fixture);
            away.setTeam(awayTeamOpt.get());
            home.setFixture(fixture);
            home.setTeam(homeTeamOpt.get());
            goals.setFixture(fixture);
            fixture.setRound(res.getLeague().getRound());
            fixture.setGoals(goals);
            status.setFixture(fixture);
            fixture.setStatus(status);
            fixture.setAway(away);
            fixture.setHome(home);
            var savedFixture = fixtureRepository.save(fixture);
//            savedFixture.setStatus(status);
        }
        addStatistic();
    }
//    @Transactional
//    private void addStatistic() {
//        var fixtures = fixtureRepository.findAll();
////        for (var fixture : fixtures) {
//        var body = restService.getResponseBody(detailedFixture + 1034680);
//        writeToOtherFile(body, 1034680);
//        var root = objectMapper.mapToRootObject(body, DetailedFixtureResponse.class);
////        }
//    }

//        @Transactional
    private void addStatistic() {
        var body = readFromOtherFile(1034680);
        var root = objectMapper.mapToRootObject(body, DetailedFixtureResponse.class);
        var response = root.getResponse();
        for (var res : response) {
            var d = res.getPlayers().get(0);
            System.out.println(d.getPlayers().get(0).getStatistics());
        }
    }

    private void writeToFile(String body) {
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

    private String readFromOtherFile(int id) {
        try {
            return Files.readString(Path.of("./src/main/resources/data/fixture_detail/fixture_detail" + id + ".json"));
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

    private FixtureDto convertToDto(Fixture fixture) {
        return modelMapper.map(fixture, FixtureDto.class);
    }
}
