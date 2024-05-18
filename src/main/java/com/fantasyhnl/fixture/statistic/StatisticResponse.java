package com.fantasyhnl.fixture.statistic;

import lombok.Data;

import java.util.ArrayList;

@Data
public class StatisticResponse {
    private ArrayList<StatisticPlayerResponse> players;
}
