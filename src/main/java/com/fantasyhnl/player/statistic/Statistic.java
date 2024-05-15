package com.fantasyhnl.player.statistic;

import com.fantasyhnl.player.statistic.cards.Cards;
import com.fantasyhnl.player.statistic.goals.Goals;
import com.fantasyhnl.player.statistic.penalty.Penalty;
import lombok.Data;

@Data
public class Statistic {
    private Goals goals;
    private Cards cards;
    private Penalty penalty;
}
