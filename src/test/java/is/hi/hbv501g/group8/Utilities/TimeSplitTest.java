package is.hi.hbv501g.group8.Utilities;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.desktop.SystemSleepEvent;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class TimeSplitTest {

    private TimeSplit timeSplit;

    @BeforeEach
    void setUp() {
        timeSplit = new TimeSplit(8, 16);
    }

    @AfterEach
    void tearDown() {
        timeSplit = null;
    }

    @Test
    void split_worked_hours_DV() {
        Map<String, Double> split_map = new HashMap<>();
        LocalDateTime shift_start = LocalDateTime.of(2023, 11, 29, 8, 0);
        LocalDateTime shift_end = LocalDateTime.of(2023, 11, 29, 16, 0);
        split_map = timeSplit.getTimeSplit(shift_start, shift_end);

        double exp = 8.0;
        double act = split_map.get("DV");

        assertEquals(8, split_map.get("DV"));
        System.out.println("______________________________________");
        System.out.println("Normal Working Hours: passed");
        System.out.println("Expected: " + exp + " Actual: " + act);
        System.out.println("______________________________________");
    }

    @Test
    void split_worked_hours_EV() {
        Map<String, Double> split_map = new HashMap<>();
        LocalDateTime shift_start = LocalDateTime.of(2023, 11, 29, 16, 0);
        LocalDateTime shift_end = LocalDateTime.of(2023, 11, 30, 0, 0);
        split_map = timeSplit.getTimeSplit(shift_start, shift_end);

        double exp = 8.0;
        double act = split_map.get("EV");

        assertEquals(8, split_map.get("EV"));
        System.out.println("______________________________________");
        System.out.println("After Working Hours: passed");
        System.out.println("Expected: " + exp + " Actual: " + act);
        System.out.println("______________________________________");
    }

    @Test
    void split_worked_hours_NV() {
        Map<String, Double> split_map = new HashMap<>();
        LocalDateTime shift_start = LocalDateTime.of(2023, 11, 29, 0, 0);
        LocalDateTime shift_end = LocalDateTime.of(2023, 11, 29, 6, 0);
        split_map = timeSplit.getTimeSplit(shift_start, shift_end);

        double exp = 8.0;
        double act = split_map.get("NV");

        assertEquals(6, split_map.get("NV"));
        System.out.println("______________________________________");
        System.out.println("Night Working Hours: passed");
        System.out.println("Expected: " + exp + " Actual: " + act);
        System.out.println("______________________________________");
    }

    @Test
    void split_worked_hours_YV() {
        Map<String, Double> split_map = new HashMap<>();
        LocalDateTime shift_start = LocalDateTime.of(2023, 11, 29, 0, 0);
        LocalDateTime shift_end = LocalDateTime.of(2023, 11, 29, 10, 0);
        split_map = timeSplit.getTimeSplit(shift_start, shift_end);

        double exp = 2.0;
        double act = split_map.get("YV");

        assertEquals(8, split_map.get("NV"));
        assertEquals(2, split_map.get("YV"));
        System.out.println("______________________________________");
        System.out.println("Working w/ Over-time: passed");
        System.out.println("Expected: " + exp + " Actual: " + act);
        System.out.println("______________________________________");
    }

    @Test
    void split_worked_hours_BL() {
        Map<String, Double> split_map = new HashMap<>();
        LocalDateTime shift_start = LocalDateTime.of(2023, 11, 29, 12, 0);
        LocalDateTime shift_end = LocalDateTime.of(2023, 11, 30, 4, 0);
        split_map = timeSplit.getTimeSplit(shift_start, shift_end);

        assertEquals(0, split_map.get("DV"));
        assertEquals(4, split_map.get("EV"));
        assertEquals(4, split_map.get("NV"));
        assertEquals(8, split_map.get("YV"));
        System.out.println("______________________________________");
        System.out.println("Mixed working hours: passed");
        System.out.println("Expected: 0.0 DV, 4.0 EV, 4.0 NV, 8.0 YV");
        System.out.println("Actual: " + split_map.get("DV") + " DV, " + split_map.get("EV")
                            + " EV, " + split_map.get("NV") + " NV, " + split_map.get("YV")
                            + " YV");
        System.out.println("______________________________________");
    }

    // Bæti við minute testing á morgun, þarf örgl fínpúss!

    @Test
    void various_minute_tests_one() {
        Map<String, Double> split_map = new HashMap<>();
        LocalDateTime shift_start = LocalDateTime.of(2023, 11, 29, 07, 45);
        LocalDateTime shift_end = LocalDateTime.of(2023, 11, 29, 16, 0);
        split_map = timeSplit.getTimeSplit(shift_start, shift_end);

        assertEquals(0.25, split_map.get("NV")); // 15 minutes
        assertEquals(7.75, split_map.get("DV")); // 8 hrs - 15 minutes

        System.out.println("______________________________________");
        System.out.println("Minutes w/ Over-time: passed");
        System.out.println("Expected: " + 0.25 + " Actual: " + split_map.get("NV"));
        System.out.println("Expected: " + 7.75 + " Actual: " + split_map.get("DV"));
        System.out.println("______________________________________");
    }

    @Test
    void various_minute_tests_two() {
        Map<String, Double> split_map = new HashMap<>();
        LocalDateTime shift_start = LocalDateTime.of(2023, 11, 29, 12, 53);
        LocalDateTime shift_end = LocalDateTime.of(2023, 11, 29, 15, 47);
        split_map = timeSplit.getTimeSplit(shift_start, shift_end);

        assertEquals(2.9, split_map.get("DV"));

        System.out.println("______________________________________");
        System.out.println("Minutes w/o Over-time: passed");
        System.out.println("Expected: " + 2.9 + " Actual: " + split_map.get("DV"));
        System.out.println("______________________________________");
    }

    @Test
    void various_minute_tests_three() {
        Map<String, Double> split_map = new HashMap<>();
        LocalDateTime shift_start = LocalDateTime.of(2023, 11, 29, 1, 33);
        LocalDateTime shift_end = LocalDateTime.of(2023, 11, 29, 14, 14);
        split_map = timeSplit.getTimeSplit(shift_start, shift_end);

        assertEquals(1.55, split_map.get("DV"));
        assertEquals(0.0, split_map.get("EV"));
        assertEquals(6.45, split_map.get("NV"));
        assertEquals(4.68, split_map.get("YV"));

        double reval_ttl = split_map.get("DV") + split_map.get("EV") + split_map.get("NV") + split_map.get("YV");
        double input_ttl = ChronoUnit.MINUTES.between(shift_start, shift_end);

        assertEquals(input_ttl-0.2, reval_ttl * 60); // Within margins

        System.out.println("______________________________________");
        System.out.println("Minutes w/o Over-time: passed");
        System.out.println("DV - Expected: " + 1.55 + " Actual: " + split_map.get("DV"));
        System.out.println("EV - Expected: " + 0.0 + " Actual: " + split_map.get("EV"));
        System.out.println("NV - Expected: " + 6.45 + " Actual: " + split_map.get("NV"));
        System.out.println("YV - Expected: " + 4.68 + " Actual: " + split_map.get("YV"));
        System.out.println("Total worked hours - Expected: " + input_ttl + " Actual: " + reval_ttl * 60 +
                            " | margin of error: " + ( (input_ttl - reval_ttl * 60) / input_ttl + "%"));
        System.out.println("______________________________________");
    }
}

