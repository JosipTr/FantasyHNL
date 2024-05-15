package com.fantasyhnl.player;

import com.fantasyhnl.player.statistic.Statistic;
import com.fantasyhnl.team.Team;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
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
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Statistic statistic;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Statistic> fixtureStatistics = new HashSet<>();

    public void updatePlayer(Player player) {
        this.setFirstname(player.getFirstname());
        this.setLastname(player.getLastname());
        this.setInjured(player.getInjured());
    }

    public void updateStatistic(Statistic statistic) {
        this.setStatistic(this.statistic.updateStatistic(statistic));
    }
}
