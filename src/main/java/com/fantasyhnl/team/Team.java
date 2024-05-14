package com.fantasyhnl.team;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

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
}
