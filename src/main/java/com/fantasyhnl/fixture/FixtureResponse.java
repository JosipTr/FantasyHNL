package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.league.League;
import com.fantasyhnl.fixture.teams.Teams;
import com.fantasyhnl.fixture.goals.Goals;
import com.fantasyhnl.fixture.events.Events;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FixtureResponse {
    private Fixture fixture;
    private League league;
    private Teams teams;
    private Goals goals;
//    private Score score;
    private ArrayList<Events> events;
//    private ArrayList<Lineup> lineups;
//    private ArrayList<Statistic> statistics;
//    private ArrayList<Player> players;
}
