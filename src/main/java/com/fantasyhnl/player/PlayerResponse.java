package com.fantasyhnl.player;

import com.fantasyhnl.player.statistic.Statistic;
import lombok.Data;

import java.util.ArrayList;

@Data
public class PlayerResponse {
    public Player player;
    public ArrayList<Statistic> statistics;
}
