package com.fantasyhnl.fixture.event;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TimeId {
    private int eventsId;
    private int fixtureId;
}
