package com.fantasyhnl.fixture.score;

import lombok.Data;

@Data
public class Score {
    private Halftime halftime;
    private Fulltime fulltime;
    private Extratime extratime;
    private Penalty penalty;
}
