package com.fantasyhnl.fixture.statistic.penalty;

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
public class Penalty {
    @EmbeddedId
    @JsonIgnore
    private StatisticId id = new StatisticId();
    private Integer total;
    private Integer conceded;
    private Integer assists;
    private Integer saves;
    @MapsId
    @OneToOne
    @JsonIgnore
    private Statistic statistic;
}
