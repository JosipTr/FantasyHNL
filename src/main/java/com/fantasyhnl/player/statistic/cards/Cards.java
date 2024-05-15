package com.fantasyhnl.player.statistic.cards;

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
public class Cards {
    @Id
    private int id;
    private Integer yellow;
    private Integer yellowred;
    private Integer red;
    @MapsId
    @OneToOne
    @JsonIgnore
    private Player player;
}
