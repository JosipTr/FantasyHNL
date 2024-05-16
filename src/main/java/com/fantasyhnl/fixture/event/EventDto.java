package com.fantasyhnl.fixture.event;

import com.fantasyhnl.player.PlayerNonTeamDto;
import com.fantasyhnl.team.TeamNonPlayersDto;
import lombok.Data;

@Data
public class EventDto {
    private int id;
    private Time time;
    private TeamNonPlayersDto team;
    private PlayerNonTeamDto player;
    private PlayerNonTeamDto assist;
    private String type;
    private String detail;
    private String comments;
}
