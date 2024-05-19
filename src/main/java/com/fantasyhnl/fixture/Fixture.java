package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.event.Event;
import com.fantasyhnl.fixture.goals.Goals;
import com.fantasyhnl.fixture.statistic.Statistic;
import com.fantasyhnl.fixture.status.Status;
import com.fantasyhnl.fixture.teams.Teams;
import com.fantasyhnl.team.Team;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Data
public class Fixture {
    @Id
    private int id;
    private String referee;
    private String timezone;
    private String date;
    private int timestamp;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "fixture")
    private Status status;
    private String round;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "fixture")
    private Teams teams;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "fixture")
    private Goals goals;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "fixture", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private Set<Event> events = new HashSet<>();
//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "fixture", fetch = FetchType.EAGER)
//    private Set<Statistic> statistics = new HashSet<>();

    public void setFixture(FixtureResponse response) {
        var status = response.getFixture().getStatus();
        var goals = response.getGoals();
        goals.setFixture(this);
        status.setFixture(this);
        setReferee(response.getFixture().getReferee());
        setTimezone(response.getFixture().getTimezone());
        setDate(response.getFixture().getDate());
        setTimestamp(response.getFixture().getTimestamp());
        setRound(response.getLeague().getRound());

        setStatus(status);
        setGoals(goals);
    }

    public void updateFixture(FixtureResponse response) {
        setReferee(response.getFixture().getReferee());
        setTimezone(response.getFixture().getTimezone());
        setDate(response.getFixture().getDate());
        setTimestamp(response.getFixture().getTimestamp());
        setRound(response.getLeague().getRound());
        this.status.setStatus(response.getFixture().getStatus());
        this.teams.setTeams(response.getTeams());
        this.goals.setGoals(response.getGoals());
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

//    public void addStatistic(Statistic statistic) {
//        this.statistics.add(statistic);
//    }

    public void removeEvents() {
        Set<Event> tmp = new HashSet<>(this.events);
        for (Iterator<Event> eventIterator = tmp.iterator(); eventIterator.hasNext();) {
            Event statistic = eventIterator.next();
            statistic.setFixture(null);
            eventIterator.remove();
        }
        this.getEvents().addAll(tmp);
    }
}
