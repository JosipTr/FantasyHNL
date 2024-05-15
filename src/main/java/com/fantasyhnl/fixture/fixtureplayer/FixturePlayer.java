package com.fantasyhnl.fixture.fixtureplayer;

import com.fantasyhnl.player.Player;
import com.fantasyhnl.team.Team;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FixturePlayer {
    private Team team;
    private ArrayList<StatPlayer> players;
}
