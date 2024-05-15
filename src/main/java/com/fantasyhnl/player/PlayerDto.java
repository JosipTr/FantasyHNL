package com.fantasyhnl.player;

import com.fantasyhnl.team.TeamNonPlayersDto;
import lombok.Data;

@Data
public class PlayerDto {
    private int id;
    private String name;
    private String firstname;
    private String lastname;
    private Integer age;
    private Integer number;
    private String position;
    private Boolean injured;
    private String photo;
    private TeamNonPlayersDto team;
}
