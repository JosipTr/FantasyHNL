package com.fantasyhnl.util;

public class Constants {
    public static final String teamUrl = "https://v3.football.api-sports.io/teams?season=2023&league=210";
    public static final String playerBasicUrl = "https://v3.football.api-sports.io/players/squads?team=";
    public static final String playerUrl = "https://v3.football.api-sports.io/players?season=2023&league=210&page=";
    public static final String fixturesUrl = "https://v3.football.api-sports.io/fixtures?league=210&season=2023";
    public static final String detailedFixture = "https://v3.football.api-sports.io/fixtures?id=";

    public static final String teamFilePath = "./src/main/resources/data/team/teams.json";
    public static final String basicPlayerPath = "./src/main/resources/data/team_players/team_players";
    public static final String playerPath = "./src/main/resources/data/players/players";
    public static final String fixturePath = "./src/main/resources/data/fixture/fixture.json";
    public static final String fixtureDetailPath = "./src/main/resources/data/fixture_detail/fixture_detail";

    public static final String emptyList = "The list is empty";
}
