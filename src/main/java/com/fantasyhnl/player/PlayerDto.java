package com.fantasyhnl.player;

import com.fantasyhnl.team.TeamNonPlayersDto;
import lombok.Data;

@Data
public class PlayerDto {
    private int id;
    private String name;
    private int age;
    private int number;
    private String position;
    private String photo;
    private TeamNonPlayersDto team;
}
