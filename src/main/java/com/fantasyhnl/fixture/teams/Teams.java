package com.fantasyhnl.fixture.teams;

import com.fantasyhnl.fixture.Fixture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Teams {
    @Id
    private int id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Home home;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Away away;
    @MapsId
    @JsonIgnore
    @OneToOne
    private Fixture fixture;
}
