package com.fantasyhnl.player;

import com.fantasyhnl.team.Team;
import lombok.Data;

import java.util.ArrayList;

@Data
public class BasicPlayerResponse {
    public Team team;
    public ArrayList<Player> players;
}
