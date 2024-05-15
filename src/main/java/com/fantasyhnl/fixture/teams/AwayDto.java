package com.fantasyhnl.fixture.teams;

import com.fantasyhnl.team.TeamNonPlayersDto;
import lombok.Data;

@Data
public class AwayDto {
    private int id;
    private Boolean winner;
    private TeamNonPlayersDto team;
}
