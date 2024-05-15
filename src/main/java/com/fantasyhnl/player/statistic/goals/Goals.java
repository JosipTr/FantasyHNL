package com.fantasyhnl.player.statistic.goals;

import com.fantasyhnl.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "player")
@EqualsAndHashCode(exclude = "player")
public class Goals {
    @Id
    private int id;
    private Integer total;
    private Integer conceded;
    private Integer assists;
    private Integer saves;
    @MapsId
    @OneToOne
    @JsonIgnore
    private Player player;

    public void setGoals(Goals goals) {
        this.setTotal(goals.getTotal());
        this.setConceded(goals.getConceded());
        this.setAssists(goals.getAssists());
        this.setSaves(goals.getSaves());
    }
}
