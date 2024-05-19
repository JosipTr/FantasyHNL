package com.fantasyhnl.fixture.statistic.game;

import com.fantasyhnl.fixture.statistic.Statistic;
import com.fantasyhnl.fixture.statistic.StatisticId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "statistic")
@EqualsAndHashCode(exclude = "statistic")
public class Game {
    @EmbeddedId
    @JsonIgnore
    private StatisticId id;
    private Integer minutes;
    @MapsId
    @OneToOne
    @JsonIgnore
    private Statistic statistic;

    public void setGame(Game game) {
        setMinutes(game.getMinutes());
    }
}
