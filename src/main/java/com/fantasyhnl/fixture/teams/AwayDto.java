package com.fantasyhnl.fixture.teams;

import com.fantasyhnl.team.TeamNonPlayersDto;
import lombok.Data;

@Data
public class AwayDto {
    private Boolean winner;
    private TeamNonPlayersDto team;
}
