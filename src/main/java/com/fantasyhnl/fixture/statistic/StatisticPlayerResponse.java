package com.fantasyhnl.fixture.statistic;

import com.fantasyhnl.player.Player;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StatisticPlayerResponse {
    private Player player;
    private ArrayList<Statistic> statistics;
}
