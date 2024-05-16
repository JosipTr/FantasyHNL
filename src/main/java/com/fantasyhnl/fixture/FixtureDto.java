package com.fantasyhnl.fixture;

import com.fantasyhnl.fixture.event.EventDto;
import com.fantasyhnl.fixture.goals.Goals;
import com.fantasyhnl.fixture.status.Status;
import com.fantasyhnl.fixture.teams.TeamsDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FixtureDto {
    private int id;
    private String referee;
    private String timezone;
    private Date date;
    private Integer timestamp;
    private Status status;
    private String round;
    private TeamsDto teams;
    private Goals goals;
    private List<EventDto> events;
}
