package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.events.Events;
import com.fantasyhnl.fixture.events.EventsDto;
import com.fantasyhnl.fixture.goals.Goals;
import com.fantasyhnl.fixture.status.Status;
import com.fantasyhnl.fixture.teams.Teams;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fixture")
    private Set<Events> events = new HashSet<>();

    public void addEvent(Events events) {
        this.events.add(events);
    }
}
