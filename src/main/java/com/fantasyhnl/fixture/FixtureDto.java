package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.goals.Goal;
import com.fantasyhnl.fixture.status.Status;
import com.fantasyhnl.fixture.teams.AwayDto;
import com.fantasyhnl.fixture.teams.HomeDto;
import lombok.Data;

import java.util.Date;

@Data
public class FixtureDto {
    private int id;
    private String referee;
    private String timezone;
    private Date date;
    private Integer timestamp;
    private Status status;
    private String round;
    private HomeDto home;
    private AwayDto away;
    private Goal goals;
}
