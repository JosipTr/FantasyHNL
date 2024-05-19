package com.fantasyhnl.player;

import com.fantasyhnl.fixture.event.Event;
import com.fantasyhnl.fixture.statistic.Statistic;
import com.fantasyhnl.team.Team;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = "team")
public class Player {
    @Id
    private int id;
    private String name;
    private String firstname;
    private String lastname;
    private Integer age;
    private Integer number;
    private String position;
    private Boolean injured;
    private String photo;
    @ManyToOne
    private Team team;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "player", fetch = FetchType.LAZY)
    private Set<Statistic> statistics = new HashSet<>();

    public void updatePlayer(Player player) {
        this.setFirstname(player.getFirstname());
        this.setLastname(player.getLastname());
        this.setInjured(player.getInjured());
    }

    public void addStatistic(Statistic statistic) {
        this.statistics.add(statistic);
    }

    public void removeStatistics() {
        Set<Statistic> tmp = new HashSet<>(this.statistics);
        for (Iterator<Statistic> statisticIterator = tmp.iterator(); statisticIterator.hasNext();) {
            Statistic statistic = statisticIterator.next();
            statistic.setFixture(null);
            statisticIterator.remove();
        }
        this.getStatistics().addAll(tmp);
    }
}
