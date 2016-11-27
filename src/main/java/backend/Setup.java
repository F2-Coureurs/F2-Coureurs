package backend;

public class Setup {
    public static final int LOW_RISK = 1;
    public static int MEDIUM_RISK = 2;
    public static int HIGH_RISK = 3;

    /**
     * Get the LOW_RISK for setup.
     *
     * @return the value of LOW_RISK
     */
    public static int getLowRisk() {
        return LOW_RISK;
    }

    /**
     *  Get the MEDIUM_RISK for setup.
     *
     * @return the value of MEDIUM_RISK
     */
    public static int getMediumRisk() {
        return MEDIUM_RISK;
    }

    /**
     *  Get the HIGH_RISK for strategy.
     *
     * @return the value of HIGH_RISK
     */
    public static int getHighRisk() {
        return HIGH_RISK;
    }
}
