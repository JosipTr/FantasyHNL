package com.fantasyhnl.fixture;

import com.fantasyhnl.exception.InvalidIdException;
import com.fantasyhnl.player.PlayerRepository;
import com.fantasyhnl.team.TeamRepository;
import com.fantasyhnl.util.BaseRepository;
import com.fantasyhnl.util.BaseService;
import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fantasyhnl.util.Constants.*;

@Service
public class FixtureService extends BaseService<Fixture, FixtureDto> {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    protected FixtureService(RestService restService, JsonToObjectMapper objectMapper, ModelMapper modelMapper, BaseRepository<Fixture> baseRepository, TeamRepository teamRepository, PlayerRepository playerRepository) {
        super(restService, objectMapper, modelMapper, baseRepository);
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void add() {
        var body = readFromFile(fixturePath);
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
            var savedFixture = baseRepository.save(fixture);
            if (savedFixture.getId() < 1034786) {
                addStatistic(savedFixture);
            }
        }
    }

    @Override
    @Transactional
    public void update() {
        var fixtures = baseRepository.findAll().stream()
                .filter(fix -> fix.getId() < 1034786)
                .toList();

        for (var fixture : fixtures) {
            var response = getFixtureResponse(fixture);
            fixture.removeEvents();
            for (var res : response) {
                updateFixture(res, fixture);
                setFixtureEvents(res, fixture);
            }
        }
    }


    @Transactional
    @Override
    public void updateById(int id) {
        var fixtureOpt = baseRepository.findById(id);
        if (fixtureOpt.isEmpty()) throw new InvalidIdException("Invalid ID");
        var fix = fixtureOpt.get();
        var response = getFixtureResponse(fix);
        fix.removeEvents();
        for (var res : response) {
            updateFixture(res, fix);
            setFixtureEvents(res, fix);
//            setPlayerStatistic(res, fix);
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

            var playerOpt = playerRepository.findById(playerId);
            var assistPlayerOpt = playerRepository.findById(assistPlayerId);
            var team = teamRepository.findById(teamId);

            if (assistPlayerOpt.isEmpty() || playerOpt.isEmpty()) continue;
            var assistPlayer = assistPlayerOpt.get();
            var player = playerOpt.get();
            event.setPlayer(player);
            event.setAssist(assistPlayer);
            team.ifPresent(event::setTeam);

            time.setFixture(fixture);
            event.setFixture(fixture);
            event.setTime(time);
            fixture.addEvent(event);
        }
    }

    private void setPlayerStatistic(FixtureResponse res, Fixture fixture) {
        // Get player statistic
        var statisticResponses = res.getPlayers();
        if (statisticResponses == null) return;
        for (var statResponse : statisticResponses) {
            var statPlayerResponses = statResponse.getPlayers();
            for (var statPlayerResponse : statPlayerResponses) {
                var play = statPlayerResponse.getPlayer();
                var savedPlayerOpt = playerRepository.findById(play.getId());
                if (savedPlayerOpt.isEmpty()) continue;
                var player = savedPlayerOpt.get();
                var statistics = statPlayerResponse.getStatistics();
                for (var stat : statistics) {
                    var games = stat.getGames();
                    var goals = stat.getGoals();
                    var cards = stat.getCards();
                    var penalty = stat.getPenalty();
                    games.setStatistic(stat);
                    goals.setStatistic(stat);
                    cards.setStatistic(stat);
                    penalty.setStatistic(stat);
                    stat.setGames(games);
                    stat.setGoals(goals);
                    stat.setCards(cards);
                    stat.setPenalty(penalty);
                    stat.setFixture(fixture);
                    stat.setPlayer(player);
                    player.addStatistic(stat);
                }
            }
        }
    }

    private void addStatistic(Fixture fixture) {
        var response = getFixtureResponse(fixture);
        for (var res : response) {
            setFixtureEvents(res, fixture);
        }
    }

    private void addEventStatistic(Fixture fixture) {
        var response = getFixtureResponse(fixture);
        for (var res : response) {
            setFixtureEvents(res, fixture);
            setPlayerStatistic(res, fixture);
        }
    }

    private void updateFixture(FixtureResponse res, Fixture fixture) {
        var goals = res.getGoals();
        var referee = res.getFixture().getReferee();
        var status = res.getFixture().getStatus();
        var teams = res.getTeams();
        fixture.updateTeams(teams);
        fixture.updateStatus(status);
        fixture.updateGoals(goals);
        fixture.setReferee(referee);
    }

    private List<FixtureResponse> getFixtureResponse(Fixture fixture) {
        var url = fixtureDetailPath + fixture.getId() + ".json";
        var body = readFromFile(url);
        var root = objectMapper.mapToRootObject(body, FixtureResponse.class);
        return root.getResponse();
    }

    @Override
    protected Class<FixtureDto> getDtoClass() {
        return FixtureDto.class;
    }
}
