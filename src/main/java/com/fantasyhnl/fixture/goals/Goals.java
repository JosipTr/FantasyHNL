package com.fantasyhnl.fixture.goals;

import com.fantasyhnl.fixture.Fixture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "fixture")
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
}
