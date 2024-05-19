package com.fantasyhnl.fixture.statistic;

import com.fantasyhnl.fixture.Fixture;
import com.fantasyhnl.fixture.statistic.card.Card;
import com.fantasyhnl.fixture.statistic.game.Game;
import com.fantasyhnl.fixture.statistic.goal.Goal;
import com.fantasyhnl.fixture.statistic.penalty.Penalty;
import com.fantasyhnl.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = {"fixture", "player"})
@EqualsAndHashCode(exclude = {"fixture", "player"})
public class Statistic {
    @EmbeddedId
    @JsonIgnore
    private StatisticId id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "statistic")
    private Game games;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "statistic")
    private Goal goals;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "statistic")
    private Card cards;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "statistic")
    private Penalty penalty;
    @MapsId("fixtureId")
    @OneToOne
    @JsonIgnore
    private Fixture fixture;
    @MapsId("playerId")
    @OneToOne
    @JsonIgnore
    private Player player;

    public Statistic() {
        this.id = new StatisticId();
    }

    public void setStatistic(Statistic statistic) {
        this.games.setGame(statistic.getGames());
        this.goals.setGoal(statistic.getGoals());
        this.cards.setCard(statistic.getCards());
        this.penalty.setPenalty(statistic.getPenalty());
    }
}
