package backend;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class StrategistTest {

    @Test
    public void constructorTest() {
        Strategist strategist = new Strategist("Anonyme", 1);

        assertTrue(strategist.getName().equals("Anonyme"));
        assertTrue(strategist.getSalary() == 1);
    }
}
