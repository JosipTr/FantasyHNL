package com.fantasyhnl.fixture.event;

import com.fantasyhnl.fixture.Fixture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(exclude = {"fixture"})
@ToString(exclude = {"fixture"})
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private int id;
    private Integer elapsed;
    private Integer extra;
    @ManyToOne
    @JsonIgnore
    private Fixture fixture;

    public void setTime(Time time) {
        setElapsed(time.getElapsed());
        setExtra(time.getExtra());
    }
}
