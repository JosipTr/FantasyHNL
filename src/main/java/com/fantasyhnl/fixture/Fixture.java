package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.goals.Goals;
import com.fantasyhnl.fixture.status.Status;
import com.fantasyhnl.fixture.teams.Teams;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Fixture {
    @Id
    private int id;
    private String referee;
    private String timezone;
    private Date date;
    private int timestamp;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fixture")
    private Status status;
    private String round;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fixture")
    private Teams teams;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fixture")
    private Goals goals;
//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fixture")
//    private Set<Events> events = new HashSet<>();

//    public void addEvent(Events events) {
//        this.events.add(events);
//    }
}
