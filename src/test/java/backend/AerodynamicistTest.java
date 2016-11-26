package backend;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class  AerodynamicistTest {

    private Aerodynamicist aerodynamicist;

    @Before
    public void setUp() {
        aerodynamicist = new Aerodynamicist("Dan Fallows", 700000);
    }

    @Test
    public void constructorTest_name() {
        assertTrue(aerodynamicist.getName().equals("Dan Fallows"));
    }

    @Test
    public void constructorTest_salary() {
        assertTrue(aerodynamicist.getSalary() == 700000);
    }
}