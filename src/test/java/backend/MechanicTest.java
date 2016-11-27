package backend;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class MechanicTest {

    private Mechanic mechanic;

    @Before
    public void setUp() {
        mechanic = new Mechanic("Steve Matchett", 100000, 50, 50, 50);
    }

    @Test
    public void constructorTest_name() {
        assertTrue(mechanic.getName().equals("Steve Matchett"));
    }

    @Test
    public void constructorTest_salary() {
        assertTrue(mechanic.getSalary() == 100000);
    }
}
