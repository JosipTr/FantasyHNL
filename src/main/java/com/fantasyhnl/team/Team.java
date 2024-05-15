package com.fantasyhnl.team;

import com.fantasyhnl.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Team {
    @Id
    private int id;
    private String name;
    private String code;
    private String country;
    private String founded;
    private String logo;
    @OneToMany
    private Set<Player> players = new HashSet<>();

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
