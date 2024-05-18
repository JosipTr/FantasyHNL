package com.fantasyhnl.fixture.statistic;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class StatisticId {
    private int fixtureId;
    private int playerId;
}
