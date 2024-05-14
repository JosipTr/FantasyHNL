package com.fantasyhnl.team;

import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import com.fantasyhnl.util.Root;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return teamRepository.findAll();
    }

    public void addTeams() {
        String body = restService.getResponseBody(teamUrl);
        Root<TeamResponse> root = objectMapper.mapToRootObject(body, TeamResponse.class);
        var response = root.getResponse();

        for (var res : response) {
            teamRepository.save(res.getTeam());
        }
    }
}
