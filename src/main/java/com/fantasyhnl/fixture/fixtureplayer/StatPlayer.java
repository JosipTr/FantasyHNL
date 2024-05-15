package com.fantasyhnl.fixture.fixtureplayer;

import com.fantasyhnl.player.Player;
import com.fantasyhnl.player.statistic.Statistic;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StatPlayer {
    private Player player;
    private ArrayList<Statistic> statistics;
}
