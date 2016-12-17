package backend;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TeamTest {
    private Team team;
    private Team otherTeam;
    private Engine engine;
    private Engine otherEngine;
    private Aerodynamicist aerodynamicist;
    private Aerodynamicist aerodynamicist2;
    private Mechanic mechanic;
    private Mechanic mechanic2;
    private Strategist strategist;
    private Strategist strategist2;
    private Driver driver;
    private Driver driver2;
    private Driver driver3;

    @Before
    public void setUp() {
        driver = new Driver("Kimi Raikkonen", 16, 100, 50, 50, 50, false);
        driver2 = new Driver("Lewis Hamilton", 18, 100, 58, 52, 40, true);
        driver3 = new Driver("Driver3", 8, 100, 49, 20, 30, false);
        aerodynamicist = new Aerodynamicist("Dan Fallows", 700000, 1000000);
        aerodynamicist2 = new Aerodynamicist("Aerodynamicist", 500000, 1000000);
        mechanic = new Mechanic("Steve Matchett", 100000, 1000000, 50, 50, 50);
        mechanic2 = new Mechanic("Bill Murray", 120000, 1000000, 80, 40, 40);
        strategist = new Strategist("Anonyme", 1000000, 1000000);
        strategist2 = new Strategist("some guy", 10000, 1000000);
        engine = new Engine(900, 70, 80, "Mercedes");
        otherEngine = new Engine(700, 50, 100, "Honda");
        team = new Team("F2", "User", 2500000,
                engine, aerodynamicist, mechanic, strategist);
        otherTeam = new Team("name", "manager", 2000000,
                otherEngine, aerodynamicist2, mechanic2, strategist2);
        team.setFirstDriver(driver);
        team.setSecondDriver(driver2);
    }

    @Test
    public void getName() {
        assertEquals("F2", team.getName());
    }

    @Test
    public void setName() {
        otherTeam.setName("name3");
        assertEquals("name3", otherTeam.getName());
    }

    @Test
    public void getManager() {
        assertEquals("User", team.getManager());
    }

    @Test
    public void setManager() {
        otherTeam.setManager("manager3");
        assertEquals("manager3", otherTeam.getManager());
    }

    @Test
    public void getBudget() {
        assertEquals(2500000, team.getBudget());
    }

    @Test
    public void setBudget() {
        otherTeam.setBudget(2000003);
        assertEquals(2000003, otherTeam.getBudget());
    }

    @Test
    public void getPointsAlltime() {
        assertEquals(0, team.getPointsAlltime());
    }

    @Test
    public void setPointsAlltime() {
        otherTeam.setPointsAlltime(3);
        assertEquals(3, otherTeam.getPointsAlltime());
    }

    @Test
    public void getPointsThisSeason() {
        assertEquals(0, team.getPointsThisSeason());
    }

    @Test
    public void setPointsThisSeason() {
        otherTeam.setPointsThisSeason(3);
        assertEquals(3, otherTeam.getPointsThisSeason());
    }

    @Test
    public void getWinsAllTime() {
        assertEquals(0, team.getWinsAlltime());
    }

    @Test
    public void setWinsAllTime() {
        otherTeam.setWinsAlltime(3);
        assertEquals(3, otherTeam.getWinsAlltime());
    }

    @Test
    public void getWinsThisSeason() {
        assertEquals(0, team.getWinsThisSeason());
    }

    @Test
    public void setWinsThisSeason() {
        otherTeam.setWinThisSeason(3);
        assertEquals(3, otherTeam.getWinsThisSeason());
    }

    @Test
    public void getEngine() {
        assertEquals(engine, team.getEngine());
    }

    @Test
    public void setEngine() {
        otherTeam.setEngine(engine);
        assertEquals(engine, otherTeam.getEngine());
    }

    @Test
    public void getAerodynamicist() {
        assertEquals(aerodynamicist, team.getAerodynamicist());
    }

    @Test
    public void setAerodynamicist() {
        otherTeam.setAerodynamicist(aerodynamicist);
        assertEquals(aerodynamicist, otherTeam.getAerodynamicist());
    }

    @Test
    public void getMechanic() {
        assertEquals(mechanic, team.getMechanic());
    }

    @Test
    public void setMechanic() {
        otherTeam.setMechanic(mechanic);
        assertEquals(mechanic, otherTeam.getMechanic());
    }

    @Test
    public void getStrategist() {
        assertEquals(strategist, team.getStrategist());
    }

    @Test
    public void setStrategist() {
        otherTeam.setStrategist(strategist);
        assertEquals(strategist, otherTeam.getStrategist());
    }

    @Test
    public void contains_true() {
        assertTrue(team.contains(driver));
    }

    @Test
    public void contains_false() {
        assertFalse(team.contains(driver3));
    }

    @Test
    public void getFirstDriverTest() {
        assertEquals(driver, team.getFirstDriver());
    }

    @Test
    public void getSecondDriverTest() {
        assertEquals(driver2, team.getSecondDriver());
    }

    @Test
    public void setFirstDriverTest() {
        Team sameTeam = new Team("F2", "User", 2500000, engine, aerodynamicist, mechanic, strategist);
        sameTeam.setFirstDriver(driver);
        assertEquals(driver, sameTeam.getFirstDriver());
    }

    @Test
    public void setFirstDriverDifferent() {
        Team sameTeam = new Team("F2", "User", 2500000, engine, aerodynamicist, mechanic, strategist);
        sameTeam.setFirstDriver(driver);
        sameTeam.setFirstDriver(driver2);
        assertNotEquals(driver, sameTeam.getFirstDriver());
    }

    @Test
    public void setSecondDriverTest() {
        Team sameTeam = new Team("F2", "User", 2500000, engine, aerodynamicist, mechanic, strategist);
        sameTeam.setFirstDriver(driver);
        sameTeam.setSecondDriver(driver2);
        assertEquals(driver2, sameTeam.getSecondDriver());
    }

    @Test
    public void setSecondDriverDifferent() {
        Team sameTeam = new Team("F2", "User", 2500000, engine, aerodynamicist, mechanic, strategist);
        sameTeam.setFirstDriver(driver);
        sameTeam.setSecondDriver(driver2);
        sameTeam.setSecondDriver(driver3);
        assertNotEquals(driver2, sameTeam.getSecondDriver());
    }

    @Test
    public void getBudgetStringTest() {
        //TODO: Change for different locales
        String locale = Locale.getDefault().toLanguageTag();
        if (locale.equals("nl-NL")) {
            assertEquals("€ 2.500.000,00", team.getBudgetString());
        } else if (locale.equals("en-US")) {
            assertEquals("$2,500,000.00", team.getBudgetString());
        } else {
            System.out.println("Untested locale :" + locale + " giving " + team.getBudgetString());
            assertTrue(true);
        }
    }

    @Test
    public void testEquals_self() {
        assertTrue(team.equals(team));
    }

    @Test
    public void testEquals_same() {
        Team sameTeam = new Team("F2", "User", 2500000, engine, aerodynamicist, mechanic, strategist);
        sameTeam.setFirstDriver(driver);
        sameTeam.setSecondDriver(driver2);
        assertTrue(team.equals(sameTeam));
    }

    @Test
    public void testEquals_other() {
        otherTeam = new Team("name", "manager", 2000000, otherEngine, aerodynamicist2, mechanic2, strategist2);
        otherTeam.setFirstDriver(driver2);
        otherTeam.setSecondDriver(driver3);
        assertFalse(team.equals(otherTeam));
    }

    @Test
    public void diffObject() { assertNotEquals(team, ""); }

    @Test
    public void diffName() {
        Team diffName = new Team("F3", "User", 2500000, engine, aerodynamicist, mechanic, strategist);
        assertNotEquals(diffName, team);
    }

    @Test
    public void diffManager() {
        Team diffManager = new Team("F2", "Manager", 2500000, engine, aerodynamicist, mechanic, strategist);
        assertNotEquals(diffManager, team);
    }

    @Test
    public void diffBudget() {
        Team diffBudget = new Team("F2", "User", 2280000, engine, aerodynamicist, mechanic, strategist);
        assertFalse(diffBudget.equals(team));
    }

    @Test
    public void diffEngine() {
        Team diffEngine = new Team("F2", "User", 2500000, otherEngine, aerodynamicist, mechanic, strategist);
        assertNotEquals(diffEngine, team);
    }
}