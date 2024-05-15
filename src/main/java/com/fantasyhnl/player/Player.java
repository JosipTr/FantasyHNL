package com.fantasyhnl.player;

import com.fantasyhnl.team.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude = "team")
public class Player {
    @Id
    private int id;
    private String name;
    private int age;
    private int number;
    private String position;
    private String photo;
    @ManyToOne
    private Team team;
}
