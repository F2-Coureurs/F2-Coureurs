package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Race {
    private Setup userSetup;
    private Strategy userStrategy;
    private String trackName;
    private int roundInChampionship;

    /**
     * Create a new Race.
     *
     * @param setup the setup used in the race
     * @param strategy the strategy used in the race
     * @param trackName the name of the track
     * @param roundInChampionship the round in the championship
     */
    public Race(Setup setup, Strategy strategy, String trackName, int roundInChampionship) {
        this.userSetup = setup;
        this.userStrategy = strategy;
        this.trackName = trackName;
        this.roundInChampionship = roundInChampionship;
    }

    /**
     * Calculate the result of the race.
     *
     * @return the sorted list of the drivers determined by the race formula.
     */
    public List<Driver> calculateRaceResult() {
        Season season = GameEngine.getInstance().getSeason();
        List<Team> teams = season.getTeams();

        ArrayList<Driver> drivers = new ArrayList<>();

        for (Team team: teams) {
            Driver driver1 = team.getFirstDriver();
            Driver driver2 = team.getSecondDriver();

            if (season.getPlayerControlledTeam().equals(team)) {
                calculatePointsOfDriver(driver1, team.getEngine(), team.getMechanic(),
                        team.getStrategist(), team.getAerodynamicist(), userSetup, userStrategy);

                calculatePointsOfDriver(driver2, team.getEngine(), team.getMechanic(),
                        team.getStrategist(), team.getAerodynamicist(), userSetup, userStrategy);
            } else {
                Random random = new Random();

                // +1 because Setup and Strategy expects a number between 1-3
                Setup randomSetup = new Setup(random.nextInt(3) + 1);
                Strategy randomStrategy = new Strategy(random.nextInt(3) + 1);

                calculatePointsOfDriver(driver1, team.getEngine(), team.getMechanic(),
                        team.getStrategist(), team.getAerodynamicist(), randomSetup,
                        randomStrategy);

                calculatePointsOfDriver(driver2, team.getEngine(), team.getMechanic(),
                        team.getStrategist(), team.getAerodynamicist(), randomSetup,
                        randomStrategy);
            }

            drivers.add(driver1);
            drivers.add(driver2);
        }

        // Sort drivers by score
        drivers.sort((driver1, driver2) -> {
            float score1 = driver1.getScore();
            float score2 = driver2.getScore();

            if (score1 < score2) {
                return 1;
            }
            if (score1 > score2) {
                return -1;
            }
            return 0;
        });

        return drivers;

        // TODO Update championship standings (team and drivers)
        // TODO Update team finances
    }

    /**
     * Calculate the race score of a driver.
     *
     * @param driver the driver
     * @param engine the engine
     * @param mechanic the mechanic of the team
     * @param strategist the strategist of the team
     * @param aerodynamicist the aerodynamicist of the team
     */
    public void calculatePointsOfDriver(Driver driver, Engine engine, Mechanic mechanic,
                                          Strategist strategist, Aerodynamicist aerodynamicist,
                                          Setup setup, Strategy strategy) {

        Random random = new Random();
        float driverRisk = random.nextFloat() * (1.0f - 0.5f) + 0.5f;

        float driverWeight = driver.getQuality() * driverRisk;
        float engineWeight = engine.getQuality();
        float carWeight = aerodynamicist.getQuality();
        float setUpWeight = mechanic.getQuality() * setup.calculateRisk();
        float strategyWeight = strategist.getQuality() * strategy.calculateRisk();

        driver.setScore(0.2f * (driverWeight
                + engineWeight
                + carWeight
                + setUpWeight
                + strategyWeight
            )
        );
    }

    /**
     * Gets the setup of the race.
     *
     * @return the Setup
     */
    public Setup getSetup() {
        return userSetup;
    }

    /**
     * Gets the strategy of the race.
     *
     * @return the Strategy
     */
    public Strategy getStrategy() {
        return userStrategy;
    }

    /**
     * Gets the trackName of the race.
     *
     * @return the TrackName
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * Gets the round in the Championship.
     *
     * @return the round in the championship
     */
    public int getRoundInChampionship() {
        return roundInChampionship;
    }

    /**
     * Checks for equality between the object and its attributes.
     *
     * @param other object to check against
     * @return true if all attributes are the same, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Race race = (Race) other;
        return roundInChampionship == race.roundInChampionship
                && Objects.equals(userSetup, race.userSetup)
                && Objects.equals(userStrategy, race.userStrategy)
                && Objects.equals(trackName, race.trackName);
    }
}
