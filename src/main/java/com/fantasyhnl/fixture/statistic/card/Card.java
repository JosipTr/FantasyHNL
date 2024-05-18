package com.fantasyhnl.fixture.statistic.card;

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
public class Card {
    @EmbeddedId
    @JsonIgnore
    private StatisticId id;
    private Integer yellow;
    private Integer red;
    @MapsId
    @OneToOne
    @JsonIgnore
    private Statistic statistic;

    public void setCard(Card card) {
        setYellow(card.getYellow());
        setRed(card.getRed());
    }
}
