package com.fantasyhnl.team;

import com.fantasyhnl.player.statistic.Statistic;
import lombok.Data;

@Data
public class TeamNonPlayersDto {
    private int id;
    private String name;
    private String code;
    private String country;
    private String founded;
    private String logo;
}
