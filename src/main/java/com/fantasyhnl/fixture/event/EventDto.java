package com.fantasyhnl.fixture.event;

import com.fantasyhnl.player.PlayerFixtureDto;
import com.fantasyhnl.player.PlayerNonTeamDto;
import com.fantasyhnl.team.TeamNonPlayersDto;
import lombok.Data;

@Data
public class EventDto {
    private Time time;
    private TeamNonPlayersDto team;
    private PlayerFixtureDto player;
    private PlayerFixtureDto assist;
    private String type;
    private String detail;
    private String comments;
}
