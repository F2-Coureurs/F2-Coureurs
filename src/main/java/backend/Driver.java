package backend;

public class Driver extends Staff implements Comparable<Driver> {
    private int speed;
    private int raceCraft;
    private int strategyInsight;
    private int raceWins;
    private int points;
    private boolean championLastYear;

    /**
     * Create a new driver.
     *
     * @param name             the name of the staff member
     * @param salary           the salary per race
     * @param speed            the speed of the driver (0-100)
     * @param raceCraft        the raceCraft of the driver (0-100)
     * @param strategyInsight  the strategy insight of the driver (0-100)
     * @param championLastYear true if the driver was the champion last year
     */
    public Driver(String name, int salary, int buyoutClause, int speed, int raceCraft,
                  int strategyInsight, boolean championLastYear) {
        super(name, salary, buyoutClause);

        this.speed = speed;
        this.raceCraft = raceCraft;
        this.strategyInsight = strategyInsight;
        this.championLastYear = championLastYear;
        this.raceWins = 0;
        this.points = 0;
    }

    /**
     * set the race wins.
     *
     * @param raceWins must be between 0 and 100
     */
    public void setRaceWins(int raceWins) {
        //TODO: Enforce between 0 and 100?
        this.raceWins = raceWins;
    }

    /**
     * Set the points of the driver.
     *
     * @param points must be between 0 and 100
     */
    public void setPoints(int points) {
        //TODO: Enforce between 0 and 100
        this.points = points;
    }

    /**
     * Get the total amount of points.
     *
     * @return the points of the driver
     */
    public int getPoints() {
        return points;
    }

    /**
     * Finds the quality of the driver by combining all its attributes.
     *
     * @return the quality of the driver, an int between 0 and 100
     */
    @Override
    public int getQuality() {
        //TODO: Decide how to calculate driver quality
        return 0;
    }

    /**
     * Checks for equality between the object and its attributes.
     *
     * @param other object to check against
     * @return true if all attributes are the same, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Driver) {
            Driver that = (Driver) other;
            boolean boSpeed = this.speed == that.speed;
            boolean boCraft = this.raceCraft == that.raceCraft;
            boolean boInsight = this.strategyInsight == that.strategyInsight;
            boolean boWins = this.raceWins == that.raceWins;
            boolean boPoints = this.points == that.points;
            boolean boChampion = this.championLastYear == that.championLastYear;
            boolean boAll = boSpeed && boCraft && boInsight && boWins && boPoints && boChampion;
            return boAll && super.equals(other);
        } else {
            return false;
        }
    }

    /**
     * Sort drivers by its points.
     *
     * @param other the driver to compare with
     * @return the ascending order
     */
    @Override
    public int compareTo(Driver other) {
        int comparePoints = other.getPoints();

        return comparePoints - this.points;
    }
}
