package com.fantasyhnl.player;

import com.fantasyhnl.exception.EmptyListException;
import com.fantasyhnl.player.statistic.Statistic;
import com.fantasyhnl.player.statistic.cards.Cards;
import com.fantasyhnl.player.statistic.goals.Goals;
import com.fantasyhnl.player.statistic.penalty.Penalty;
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
import java.util.concurrent.TimeUnit;

import static com.fantasyhnl.util.Constants.*;

@Service
public class PlayerService {
    private final RestService restService;
    private final JsonToObjectMapper objectMapper;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public PlayerService(RestService restService, JsonToObjectMapper objectMapper, PlayerRepository playerRepository, TeamRepository teamRepository, ModelMapper modelMapper) {
        this.restService = restService;
        this.objectMapper = objectMapper;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    public List<PlayerDto> getPlayers() {
        var players = playerRepository.findAll();
        if (players.isEmpty()) throw new EmptyListException(emptyList);
        else return players.stream().map(this::convertToDto).toList();
    }

//    public void addPlayers() {
//        var teams = teamRepository.findAll();
//        for (var team : teams) {
//            var body = readFromFile(team.getId());
//            writeToFile(body, team.getId());
//            var root = objectMapper.mapToRootObject(body, BasicPlayerResponse.class);
//            var response = root.getResponse();
//            for (var res : response) {
//                var players = res.getPlayers();
//                for (var player : players) {
//                    player.setTeam(team);
//                    team.addPlayer(player);
//                    playerRepository.save(player);
//                }
//            }
//        }
//    }

    @Transactional
    public void addPlayers() {
        var teams = teamRepository.findAll();
        for (var team : teams) {
            var body = readFromFile(team.getId());
            var root = objectMapper.mapToRootObject(body, BasicPlayerResponse.class);
            var response = root.getResponse();
            for (var res : response) {
                var players = res.getPlayers();
                for (var player : players) {
                    player.setTeam(team);
                    team.addPlayer(player);
                    playerRepository.save(player);
                }
            }
        }
        var players = playerRepository.findAll();
        for (var player : players) {
            for (var i = 1; i <= 28; i++) {
                var body = readFromOtherFile(i);
                var root = objectMapper.mapToRootObject(body, PlayerResponse.class);
                var response = root.getResponse();
                for (var res : response) {
                    if (player.getId() == res.getPlayer().getId()) {
                        player.updatePlayer(res.getPlayer());
                        var stats = res.getStatistics();
                        for (var stat : stats) {
                            var cards = stat.getCards();
                            var penalty = stat.getPenalty();
                            var goals = stat.getGoals();
                            cards.setPlayer(player);
                            penalty.setPlayer(player);
                            goals.setPlayer(player);
                            stat.setPlayer(player);
                            stat.setCards(cards);
                            stat.setGoals(goals);
                            stat.setPenalty(penalty);
                            player.setStatistic(stat);
                        }
                    }
                }
            }
        }
    }

    @Transactional
    public void updatePlayers() {
        var players = playerRepository.findAll();
        for (var player : players) {
            for (var i = 1; i <= 28; i++) {
                var body = readFromOtherFile(i);
                var root = objectMapper.mapToRootObject(body, PlayerResponse.class);
                var response = root.getResponse();
                for (var res : response) {
                    if (player.getId() == res.getPlayer().getId()) {
                        player.updatePlayer(res.getPlayer());
                        var stats = res.getStatistics();
                        for (var stat : stats) {
                            player.updateStatistic(stat);
                        }
                    }
                }
            }
        }
    }

//    public void addPlayers() {
//        var teams = teamRepository.findAll();
//        for (var team : teams) {
//            var body = restService.getResponseBody(playerBasicUrl + team.getId());
//            writeToFile(body, team.getId());
//            var root = objectMapper.mapToRootObject(body, BasicPlayerResponse.class);
//            var response = root.getResponse();
//            for (var res : response) {
//                var players = res.getPlayers();
//                for (var player : players) {
//                    player.setTeam(team);
//                    team.addPlayer(player);
//                    playerRepository.save(player);
//                }
//            }
//            waitSeconds();
//        }
//    }

    private void waitSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(String body, int id) {
        try (FileWriter writer = new FileWriter("./src/main/resources/data/team_players/team_players" + id + ".json")) {
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

    private String readFromFile(int id) {
        try {
            return Files.readString(Path.of("./src/main/resources/data/team_players/team_players" + id + ".json"));
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

    private PlayerDto convertToDto(Player player) {
        return modelMapper.map(player, PlayerDto.class);
    }

}
