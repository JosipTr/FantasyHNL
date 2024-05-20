package com.fantasyhnl.team;

import com.fantasyhnl.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.fantasyhnl.util.Constants.*;

@Service
public class TeamService extends BaseService<Team, TeamDto> {

    public TeamService(BaseRepository<Team> teamRepository, JsonToObjectMapper mapper, ModelMapper modelMapper, RestService restService) {
        super(restService, mapper, modelMapper, teamRepository);
    }

    @Override
    public void add() {
        var response = getRootResponse(teamFilePath, TeamResponse.class);
        response.forEach(teamResponse -> {
            baseRepository.save(teamResponse.getTeam());
        });
    }

    @Override
    public void update() {
    }

    @Override
    protected Class<TeamDto> getDtoClass() {
        return TeamDto.class;
    }
}
