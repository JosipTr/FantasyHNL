package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.goals.Goal;
import com.fantasyhnl.fixture.league.League;
import com.fantasyhnl.fixture.teams.Teams;
import com.fantasyhnl.fixture.score.Score;
import lombok.Data;

@Data
public class FixtureResponse {
    private Fixture fixture;
    private League league;
    private Teams teams;
    private Goal goals;
    private Score score;
}
