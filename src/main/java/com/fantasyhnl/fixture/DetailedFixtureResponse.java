package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.event.Event;
import com.fantasyhnl.fixture.fixtureplayer.FixturePlayer;
import com.fantasyhnl.fixture.league.League;
import com.fantasyhnl.fixture.lineup.Lineup;
import com.fantasyhnl.fixture.score.Score;
import com.fantasyhnl.fixture.teams.Teams;
import com.fantasyhnl.player.Player;
import com.fantasyhnl.player.statistic.Statistic;
import com.fantasyhnl.player.statistic.goals.Goals;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DetailedFixtureResponse {
    private Fixture fixture;
    private League league;
    private Teams teams;
    private Goals goals;
    private Score score;
    private ArrayList<Event> events;
    private ArrayList<Lineup> lineups;
    private ArrayList<Statistic> statistics;
    private ArrayList<FixturePlayer> players;
}
