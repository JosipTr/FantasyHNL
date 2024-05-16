package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.status.Status;
import com.fantasyhnl.fixture.teams.TeamsDto;
import lombok.Data;

import java.util.Date;

@Data
public class FixtureDto {
    private int id;
    private String referee;
    private String timezone;
    private Date date;
    private int timestamp;
    private Status status;
    private String round;
    private TeamsDto teams;
}
