package com.fantasyhnl.team;

import com.fantasyhnl.venue.Venue;
import lombok.Data;

@Data
public class TeamResponse {
    private Team team;
    private Venue venue;
}
