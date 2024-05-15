package com.fantasyhnl.fixture.goals;

import com.fantasyhnl.fixture.Fixture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = {"fixture"})
@EqualsAndHashCode(exclude = {"fixture"})
public class Goal {
    @Id
    private int id;
    private Integer home;
    private Integer away;
    @MapsId
    @JsonIgnore
    @OneToOne
    private Fixture fixture;
}
