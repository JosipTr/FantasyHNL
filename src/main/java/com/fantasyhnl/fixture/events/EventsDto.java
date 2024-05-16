package com.fantasyhnl.fixture.events;

import com.fantasyhnl.fixture.Fixture;
import com.fantasyhnl.player.Player;
import com.fantasyhnl.player.PlayerNonTeamDto;
import com.fantasyhnl.team.Team;
import com.fantasyhnl.team.TeamNonPlayersDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class EventsDto {
    private int id;
    private Time time;
    private TeamNonPlayersDto team;
    private PlayerNonTeamDto player;
    private PlayerNonTeamDto assist;
    private String type;
    private String detail;
    private String comments;
}
