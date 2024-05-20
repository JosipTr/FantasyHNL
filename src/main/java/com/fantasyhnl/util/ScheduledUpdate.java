package com.fantasyhnl.util;

import com.fantasyhnl.fixture.FixtureService;
import com.fantasyhnl.player.PlayerService;
import com.fantasyhnl.team.TeamService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class ScheduledUpdate {
    private final TeamService teamService;
    private final PlayerService playerService;
    private final FixtureService fixtureService;

    public ScheduledUpdate(TeamService teamService, PlayerService playerService, FixtureService fixtureService) {
        this.teamService = teamService;
        this.playerService = playerService;
        this.fixtureService = fixtureService;
    }

    @Scheduled(cron = "* * 2 * * *")
    public void updateAll() {
        teamService.add();
        fixtureService.update();
    }
}
