package com.fantasyhnl.fixture.teams;

import com.fantasyhnl.fixture.Fixture;
import com.fantasyhnl.team.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Away {
    @Id
    private int id;
    private Boolean winner;
    @ManyToOne
    private Team team;
    @MapsId
    @JsonIgnore
    @OneToOne
    private Fixture fixture;
}
