package com.fantasyhnl.fixture;

import com.fantasyhnl.exception.EmptyListException;
import com.fantasyhnl.team.TeamRepository;
import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.fantasyhnl.util.Constants.emptyList;
import static com.fantasyhnl.util.Constants.fixturesUrl;

@Service
public class FixtureService {
    private final FixtureRepository fixtureRepository;
    private final JsonToObjectMapper objectMapper;
    private final RestService restService;
    private final TeamRepository teamRepository;

    public FixtureService(FixtureRepository fixtureRepository, JsonToObjectMapper objectMapper, RestService restService, TeamRepository teamRepository) {
        this.fixtureRepository = fixtureRepository;
        this.objectMapper = objectMapper;
        this.restService = restService;
        this.teamRepository = teamRepository;
    }

    public List<Fixture> getFixtures() {
        var fixtures = fixtureRepository.findAll();
        if (fixtures.isEmpty()) {
            throw new EmptyListException(emptyList);
        } else {
            return fixtures;
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

    @Transactional
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
    }

    private void writeToFile(String body) {
        try (FileWriter writer = new FileWriter("./src/main/resources/data/fixture/fixture.json")) {
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

}
