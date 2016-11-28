package backend;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class StrategistTest {

    private Strategist strategist;

    @Before
    public void setUp() {
        strategist = new Strategist("Anonyme", 1000000);
    }

    @Test
    public void constructorTestname () {
        assertTrue(strategist.getName().equals("Anonyme"));
    }

    @Test
    public void constructorTest_salary() {
        assertTrue(strategist.getSalary() == 1000000);
    }
}