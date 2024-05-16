package com.fantasyhnl.fixture.events;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TimeId {
    private int eventsId;
    private int fixtureId;
}
