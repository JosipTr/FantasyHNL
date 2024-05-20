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
        var response = getRootResponse(fixturePath, FixtureResponse.class);
        for (var res : response) {
            var fixture = res.getFixture();

            fixture.setFixture(res);
            setFixtureTeams(res);

            var savedFixture = baseRepository.save(fixture);
            addEventStatistic(savedFixture);
        }
    }

    @Override
    @Transactional
    public void update() {
        baseRepository.findAll().stream()
                .toList().forEach(fixture -> {
                    var response = getFixtureResponse(fixture);
                    fixture.removeEvents();
                    for (var res : response) {
                        fixture.updateFixture(res);
                        setFixtureEvents(res, fixture);
                        updatePlayerStatistic(res, fixture);
                    }
                });
    }


    @Transactional
    @Override
    public void updateById(int id) {
        baseRepository.findById(id).ifPresentOrElse(
                fixture -> {
                    var response = getFixtureResponse(fixture);
                    fixture.removeEvents();
                    for (var res : response) {
                        fixture.updateFixture(res);
                        setFixtureEvents(res, fixture);
                        updatePlayerStatistic(res, fixture);
                    }
                }, () -> {
                    throw new InvalidIdException("");
                }
        );

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

    private void updatePlayerStatistic(FixtureResponse res, Fixture fixture) {
        var statisticResponses = res.getPlayers();
        if (statisticResponses == null) return;
        for (var statResponse : statisticResponses) {
            var statPlayerResponses = statResponse.getPlayers();
            for (var statPlayerResponse : statPlayerResponses) {
                var play = statPlayerResponse.getPlayer();
                var savedPlayerOpt = playerRepository.findById(play.getId());
                if (savedPlayerOpt.isEmpty()) continue;
                var player = savedPlayerOpt.get();
                var savedStats = player.getStatistics();
                var statistics = statPlayerResponse.getStatistics();
                for (var stat : statistics) {
                    for (var savedStat : savedStats) {
                        savedStat.setStatistic(stat);
                    }
                }
            }
        }
    }

    private void addEventStatistic(Fixture fixture) {
        var response = getFixtureResponse(fixture);
        for (var res : response) {
            setFixtureEvents(res, fixture);
            setPlayerStatistic(res, fixture);
        }
    }

    private List<FixtureResponse> getFixtureResponse(Fixture fixture) {
        var url = fixtureDetailPath + fixture.getId() + ".json";
        return getRootResponse(url, FixtureResponse.class);
    }

    @Override
    protected Class<FixtureDto> getDtoClass() {
        return FixtureDto.class;
    }
}
