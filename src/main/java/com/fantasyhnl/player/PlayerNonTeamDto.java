package com.fantasyhnl.player;

import com.fantasyhnl.player.statistic.Statistic;
import lombok.Data;

@Data
public class PlayerNonTeamDto {
    private String name;
    private String firstname;
    private String lastname;
    private Integer age;
    private Integer number;
    private String position;
    private Boolean injured;
    private String photo;
    private Statistic statistic;
}
