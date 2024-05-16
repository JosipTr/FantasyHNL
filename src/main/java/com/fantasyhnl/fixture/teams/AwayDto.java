package com.fantasyhnl.fixture.teams;

import com.fantasyhnl.fixture.Fixture;
import com.fantasyhnl.team.Team;
import com.fantasyhnl.team.TeamNonPlayersDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class AwayDto {
    private int id;
    private Boolean winner;
    private TeamNonPlayersDto team;
}
