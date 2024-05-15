package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.goals.Goal;
import com.fantasyhnl.fixture.status.Status;
import com.fantasyhnl.fixture.teams.Away;
import com.fantasyhnl.fixture.teams.Home;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Fixture {
    @Id
    private int id;
    private String referee;
    private String timezone;
    private Date date;
    private Integer timestamp;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE}, mappedBy = "fixture")
    private Status status;
    private String round;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE}, mappedBy = "fixture")
    private Home home;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "fixture")
    private Away away;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "fixture")
    private Goal goals;
}
