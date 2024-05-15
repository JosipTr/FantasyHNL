package com.fantasyhnl.team;

import com.fantasyhnl.exception.EmptyListException;
import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import com.fantasyhnl.util.Root;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.fantasyhnl.util.Constants.emptyList;
import static com.fantasyhnl.util.Constants.teamUrl;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final RestService restService;
    private final JsonToObjectMapper objectMapper;

    public TeamService(TeamRepository teamRepository, RestService restService, JsonToObjectMapper objectMapper) {
        this.teamRepository = teamRepository;
        this.restService = restService;
        this.objectMapper = objectMapper;
    }

    public Team getTeam(int id) {
        var optionalTeam = teamRepository.findById(id);
        return optionalTeam.orElse(null);
    }

    public List<Team> getTeams() {
        var teams = teamRepository.findAll();
        if (teams.isEmpty()) {
            throw new EmptyListException(emptyList);
        } else {
            return teams;
        }
    }

    public void addTeams() {
        String body = readFromFile();
        Root<TeamResponse> root = objectMapper.mapToRootObject(body, TeamResponse.class);
        var response = root.getResponse();

        for (var res : response) {
            teamRepository.save(res.getTeam());
        }
    }

//    public void addTeams() {
//        String body = restService.getResponseBody(teamUrl);
//        writeToFile(body);
//        Root<TeamResponse> root = objectMapper.mapToRootObject(body, TeamResponse.class);
//        var response = root.getResponse();
//
//        for (var res : response) {
//            teamRepository.save(res.getTeam());
//        }
//    }

    private void writeToFile(String body) {
        try (FileWriter writer = new FileWriter("./src/main/resources/data/team/teams.json")) {
            writer.write(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFromFile() {
        try {
            return Files.readString(Path.of("./src/main/resources/data/team/teams.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
