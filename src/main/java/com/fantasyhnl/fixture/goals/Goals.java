package com.fantasyhnl.fixture.goals;

import com.fantasyhnl.fixture.Fixture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "fixture")
@EqualsAndHashCode(exclude = "fixture")
@Table(name = "fixture_goals")
public class Goals {
    @Id
    @JsonIgnore
    private int id;
    private Integer home;
    private Integer away;
    @MapsId
    @JsonIgnore
    @OneToOne
    private Fixture fixture;

    public void setGoals(Goals goals) {
        setHome(goals.getHome());
        setAway(goals.getAway());
    }
}
