package com.fantasyhnl.player.statistic.penalty;

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
public class Penalty {
    @Id
    private int id;
    private Integer won;
    private Integer commited;
    private Integer scored;
    private Integer missed;
    private Integer saved;
    @MapsId
    @OneToOne
    @JsonIgnore
    private Player player;
}
