package com.fantasyhnl.fixture.statistic.goal;

import com.fantasyhnl.fixture.goals.Goals;
import com.fantasyhnl.fixture.statistic.Statistic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "statistic")
@EqualsAndHashCode(exclude = "statistic")
public class Goal {
    @Id
    private int id;
    private Integer total;
    private Integer conceded;
    private Integer assists;
    private Integer saves;
    @MapsId
    @OneToOne
    @JsonIgnore
    private Statistic statistic;

    public void setGoal(Goal goal) {
        setTotal(goal.getTotal());
        setConceded(goal.getConceded());
        setAssists(goal.getAssists());
        setSaves(goal.getSaves());
    }
}
