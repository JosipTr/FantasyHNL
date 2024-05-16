package com.fantasyhnl.fixture.teams;

import com.fantasyhnl.fixture.Fixture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = {"fixture"})
public class Teams {
    @Id
    private int id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Home home;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Away away;
    @MapsId
    @JsonIgnore
    @OneToOne
    private Fixture fixture;
}
