package com.fantasyhnl.player;

import com.fantasyhnl.exception.EmptyListException;
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

import static com.fantasyhnl.util.Constants.emptyList;
import static com.fantasyhnl.util.Constants.playerBasicUrl;

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

    public void addPlayers() {
        var teams = teamRepository.findAll();
        for (var team : teams) {
            var body = readFromFile(team.getId());
            writeToFile(body, team.getId());
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

    private void waitSeconds() {
        try {
            TimeUnit.SECONDS.sleep(5);
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

    private String readFromFile(int id) {
        try {
            return Files.readString(Path.of("./src/main/resources/data/team_players/team_players" + id + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PlayerDto convertToDto(Player player) {
        return modelMapper.map(player, PlayerDto.class);
    }
}
