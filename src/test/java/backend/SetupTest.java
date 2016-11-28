package backend;

import org.junit.Test;
import static org.junit.Assert.*;

public class SetupTest {

    @Test
    public void getLowRisk() {
        assertTrue(Setup.getLowRisk() == 1);
    }

    @Test
    public void getMediumRisk() {
        assertTrue(Setup.getMediumRisk() == 2);
    }

    @Test
    public void getHighRisk() {
        assertTrue(Setup.getHighRisk() == 3);
    }
}