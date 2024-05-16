package com.fantasyhnl.fixture.events;

import com.fantasyhnl.fixture.Fixture;
import com.fantasyhnl.player.Player;
import com.fantasyhnl.team.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

@Entity
@Data
@EqualsAndHashCode(exclude = {"fixture"})
@ToString(exclude = {"fixture"})
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
}
