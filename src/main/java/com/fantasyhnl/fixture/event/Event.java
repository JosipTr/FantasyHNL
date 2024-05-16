package com.fantasyhnl.fixture.event;

import com.fantasyhnl.fixture.Fixture;
import com.fantasyhnl.player.Player;
import com.fantasyhnl.team.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(exclude = {"fixture"})
@ToString(exclude = {"fixture"})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private int id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Time time;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Team team;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Player player;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Player assist;
    private String type;
    private String detail;
    private String comments;
    @ManyToOne
    @JsonIgnore
    private Fixture fixture;

    public void setEvent(Event event) {
        this.time.setTime(event.getTime());
    }
}
