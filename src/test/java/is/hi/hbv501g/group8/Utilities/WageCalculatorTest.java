package is.hi.hbv501g.group8.Utilities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class WageCalculatorTest {

    private WageCalculator wageCalculator;

    @BeforeEach
    void setUp() {
        wageCalculator = new WageCalculator(200000);
    }

    @AfterEach
    void tearDown() {
        wageCalculator = null;
    }

    @Test
    void estimateWages() {
        assertEquals(wageCalculator.splitHours(LocalTime.of(18,0), LocalTime.of(23,0)), 5);
    }

    @Test
    void testSalaries() {
        assertEquals(wageCalculator.getBaseSalary(), 200000);
        assertEquals(wageCalculator.getDaytime(), 1253.37);
        assertEquals(wageCalculator.getAfterhours(), 1750.00);
        assertEquals(wageCalculator.getNighttime(), 1875.00);
        assertEquals(wageCalculator.getOvertime(), 2077.00);
    }
}