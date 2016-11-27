package backend;

public class Mechanic extends Staff {
    private int reliability;
    private int partFixing;
    private int pitstops;

    /**
     * Create a new mechanic.
     *
     * @param name the name of the staff member
     * @param salary the salary per race
     * @param reliability the reliability of the mechanic (0-100)
     * @param partFixing the speed that the mechanic fixes a part (0-100)
     * @param pitstops the workflow in pitstops
     */
    public Mechanic(String name, int salary, int reliability, int partFixing, int pitstops) {
        super(name, salary);

        this.reliability = reliability;
        this.partFixing = partFixing;
        this.pitstops = pitstops;
    }
}
