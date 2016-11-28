package backend;

import org.junit.Test;
import static org.junit.Assert.*;

public class StrategyTest {

    @Test
    public void getLowRisk() {
        assertTrue(Strategy.getLowRisk() == 1);
    }

    @Test
    public void getMediumRisk() {
        assertTrue(Strategy.getMediumRisk() == 2);
    }

    @Test
    public void getHighRisk() {
        assertTrue(Strategy.getHighRisk() == 3);
    }
}