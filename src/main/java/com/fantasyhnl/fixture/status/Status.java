package com.fantasyhnl.fixture.status;

import com.fantasyhnl.fixture.Fixture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "fixture")
public class Status {
    @Id
    @JsonIgnore
    private int id;
    @JsonProperty("long")
    private String myLong;
    @JsonProperty("short")
    private String myShort;
    private int elapsed;
    @OneToOne
    @MapsId
    @JsonIgnore
    private Fixture fixture;
}
