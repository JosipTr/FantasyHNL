package com.fantasyhnl.team;

import com.fantasyhnl.player.PlayerNonTeamDto;
import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private int id;
    private String name;
    private String code;
    private String country;
    private String founded;
    private String logo;
    private List<PlayerNonTeamDto> players;
}
