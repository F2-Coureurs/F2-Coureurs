package backend;

import com.google.gson.JsonSyntaxException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SeasonTest {
    private Season simpleSeason;
    private Season season;
    private Season sameSeason;
    private Season otherSeason;
    private String jsonString;
    private String jsonStringSpaces;
    private Team team;
    private Driver driver;
    private Driver driver2;
    private Aerodynamicist aerodynamicist;
    private Mechanic mechanic;
    private Strategist strategist;
    private Engine engine;
    private Race race;

    @Before
    public void setUp() {
        driver = new Driver("Kimi Raikkonen", 16, 100,50, 50, 50, false);
        driver2 = new Driver("Lewis Hamilton", 18, 100,58, 52, 40, true);
        aerodynamicist = new Aerodynamicist("Dan Fallows", 700000, 100);
        mechanic = new Mechanic("Steve Matchett", 100000, 100, 50, 50, 50);
        strategist = new Strategist("Anonyme", 1000000, 10000000);
        engine = new Engine(900, 70, 80, "Mercedes");
        team = new Team("F2", "User", 2500000,
                engine, aerodynamicist, mechanic, strategist);
        team.setFirstDriver(driver);
        team.setSecondDriver(driver2);

        this.simpleSeason = new Season();
        this.season = new Season();
        this.sameSeason = new Season();
        this.otherSeason = new Season();

        this.season.addTeam(team);
        this.otherSeason.addTeam(team);
        this.sameSeason.addTeam(team);

        race = new Race(new Setup(Setup.LOW_RISK), new Strategy(Strategy.HIGH_RISK), "Circuit de Monaco", 8);
        this.season.addRace(race);
        this.sameSeason.addRace(race);
        this.otherSeason.addRace(race);

        otherSeason.setCurrentRound(5);
        this.jsonString = "{\"currentRound\":0,\"rounds\":[{\"setup\":{\"risk\":1},\"strategy\":{\"risk\":3},\"trackName\":\"Circuit de Monaco\",\"roundInChampionship\":8}],\"teams\":[{\"name\":\"F2\",\"manager\":\"User\",\"budget\":2500000,\"pointsAlltime\":0,\"pointsThisSeason\":0,\"winsAlltime\":0,\"winsThisSeason\":0,\"engine\":{\"power\":900,\"drivability\":70,\"fuelConsumption\":80,\"name\":\"Mercedes\"},\"aerodynamicist\":{\"name\":\"Dan Fallows\",\"salary\":700000,\"buyoutClause\":100},\"mechanic\":{\"reliability\":50,\"partFixing\":50,\"pitstops\":50,\"name\":\"Steve Matchett\",\"salary\":100000,\"buyoutClause\":100},\"strategist\":{\"name\":\"Anonyme\",\"salary\":1000000,\"buyoutClause\":10000000},\"drivers\":[{\"speed\":50,\"raceCraft\":50,\"strategyInsight\":50,\"raceWins\":0,\"points\":0,\"championLastYear\":false,\"name\":\"Kimi Raikkonen\",\"salary\":16,\"buyoutClause\":100},{\"speed\":58,\"raceCraft\":52,\"strategyInsight\":40,\"raceWins\":0,\"points\":0,\"championLastYear\":true,\"name\":\"Lewis Hamilton\",\"salary\":18,\"buyoutClause\":100}]}],\"contractAerodynamicists\":[],\"contractDrivers\":[],\"contractMechanics\":[],\"contractStrategists\":[]}";
        this.jsonStringSpaces = "{\n\"currentRound\":0,\n\"rounds\":[\n{\"setup\":{\"risk\":1},\"strategy\":{\"risk\":3},\"trackName\":\"Circuit de Monaco\",\"roundInChampionship\":8}],\"teams\":[{\"name\":\"F2\",\"manager\":\"User\",\"budget\":2500000,\"pointsAlltime\":0,\"pointsThisSeason\":0,\"winsAlltime\":0,\"winsThisSeason\":0,\"engine\":{\"power\":900,\"drivability\":70,\"fuelConsumption\":80,\"name\":\"Mercedes\"},\"aerodynamicist\":{\"name\":\"Dan Fallows\",\"salary\":700000},\"mechanic\":{\"reliability\":50,\"partFixing\":50,\"pitstops\":50,\"name\":\"Steve Matchett\",\"salary\":100000},\"strategist\":{\"name\":\"Anonyme\",\"salary\":1000000},\"drivers\":[{\"speed\":50,\"raceCraft\":50,\"strategyInsight\":50,\"raceWins\":0,\"points\":0,\"championLastYear\":false,\"name\":\"Kimi Raikkonen\",\"salary\":16},{\"speed\":58,\"raceCraft\":52,\"strategyInsight\":40,\"raceWins\":0,\"points\":0,\"championLastYear\":true,\"name\":\"Lewis Hamilton\",\"salary\":18}]}]}";
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getCurrentRoundTest() {
        assertEquals(this.race, this.season.getCurrentRound());
    }

    @Test
    public void getTeamsTest() {
        assertEquals(this.season.getTeams(), this.sameSeason.getTeams());
    }

    @Test
    public void getTeamTest() {
        Team team2 = new Team("F2", "User", 2500000,
                engine, aerodynamicist, mechanic, strategist);
        team2.setPointsThisSeason(1);
        this.season.addTeam(team2);

        assertEquals(team2, this.season.getTeam(1));
    }

    @Test
    public void getCurrentRoundException() {
        Season exceptSeason = new Season();
        exceptSeason.addRace(race);
        exceptSeason.addTeam(team);
        exceptSeason.setCurrentRound(1);
        expectedException.expect(IllegalArgumentException.class);
        exceptSeason.getCurrentRound();
    }

    @Test
    public void roundIntTest() {
        assertEquals(0, season.getRoundInt());
    }

    @Test
    public void setCurrentRoundTest() {
        assertEquals(5, otherSeason.getRoundInt());
    }

    @Test
    public void getContractAeroTest() {
        assertEquals(new ArrayList<Aerodynamicist>(), season.getContractAerodynamicists());
    }

    @Test
    public void getContractDrivers() {
        assertEquals(new ArrayList<Driver>(), season.getContractDrivers());
    }

    @Test
    public void getContractMechanics() {
        assertEquals(new ArrayList<Mechanic>(), season.getContractMechanics());
    }

    @Test
    public void getContractStrategists() {
        assertEquals(new ArrayList<Strategist>(), season.getContractStrategists());
    }

    @Test
    public void getPlayerControlledTeamTest() {
        assertEquals(team, season.getPlayerControlledTeam());
    }
    @Test
    public void equalsSame() {
        assertEquals(season, sameSeason);
    }

    @Test
    public void equalsDifferent() {
        assertNotEquals(season, otherSeason);
    }

    @Test
    public void equalsSameMemory() {
        assertEquals(season, season);
    }

    @Test
    public void toJsonTest() {
        assertEquals(jsonString, season.toJson());
    }

    @Test
    public void toJsonPrettyTest() {
        String prettySimple = "{\n  \"currentRound\": 0,\n  \"rounds\": [],\n  \"teams\": [],\n  \"contractAerodynamicists\": [],\n  \"contractDrivers\": [],\n  \"contractMechanics\": [],\n  \"contractStrategists\": []\n}";
        assertEquals(prettySimple, simpleSeason.toJsonPretty());
    }

    @Test
    public void fromJsonTest() {
        assertEquals(season, Season.fromJson(jsonString));
    }

    @Test
    public void fromJsonSpaces() {
        assertNotEquals(season, Season.fromJson(jsonStringSpaces));
    }

    @Test
    public void fromJsonFail() {
        expectedException.expect(JsonSyntaxException.class);
        Season.fromJson("unexpected string");
    }

    @Test
    public void readFromJsonFileTest() throws IOException {
        InputStream is = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));

        assertEquals(season, Season.readFromJsonFile(is));
    }

    @Test
    public void readFromJsonSpaces() throws IOException {
        InputStream is = new ByteArrayInputStream(jsonStringSpaces.getBytes(StandardCharsets.UTF_8));

        assertNotEquals(season, Season.readFromJsonFile(is));
    }

    @Test
    public void equalsOtherObject() {
        assertNotEquals(season, new String());
    }
}
