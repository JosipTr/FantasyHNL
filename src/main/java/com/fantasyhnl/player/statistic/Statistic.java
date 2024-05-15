package com.fantasyhnl.player.statistic;

import com.fantasyhnl.player.Player;
import com.fantasyhnl.player.statistic.cards.Cards;
import com.fantasyhnl.player.statistic.goals.Goals;
import com.fantasyhnl.player.statistic.penalty.Penalty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "player")
@EqualsAndHashCode(exclude = "player")
public class Statistic {
    @Id
    private int id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Goals goals;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Cards cards;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Penalty penalty;
    @MapsId
    @OneToOne
    @JsonIgnore
    private Player player;

    public void updateStatistic(Statistic statistic) {
        this.goals.setGoals(statistic.getGoals());
        this.cards.setCards(statistic.getCards());
        this.penalty.setPenalty(statistic.getPenalty());
    }
}
