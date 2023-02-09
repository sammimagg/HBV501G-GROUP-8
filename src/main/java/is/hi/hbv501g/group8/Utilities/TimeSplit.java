/**
 * TimeSplit
 *
 * Description: Implementation to help split up worked hours into a map of
 *              type of hours worked.
 *
 * @author Kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */

package is.hi.hbv501g.group8.Utilities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class TimeSplit {

    // Dagvinna
    private int dayStartHour = 8;
    private int dayEndHour = 16;

    // Eftirvinna
    private int eveningStartHour = 16;
    private int eveningEndHour = 24;

    // Næturvinna
    private int nightStartHour = 0;
    private int nightEndHour = 8;


    /**
     * Main function which calculates and maps results.
     *
     * @param shift_start LocalDateTime Starting time of shift
     * @param shift_end LocalDateTime Ending time of shift
     * @return hours_split Map<String, Double>
     */
    public static Map<String, Double> getTimeSplit(LocalDateTime shift_start, LocalDateTime shift_end) {
        // Input Data
        long totalMinutes = ChronoUnit.MINUTES.between(shift_start, shift_end);

        // Reval
        Map<String, Double> timeSplit = new HashMap<>();
        double dayHours = 0;
        double eveningHours = 0;
        double nightHours = 0;
        double overTime = 0;

        return null;
    }
}

/**
 * import java.time.LocalDateTime;
 * import java.time.temporal.ChronoUnit;
 * import java.util.HashMap;
 * import java.util.Map;
 *
 * public class TimeSplit {
 *     public static Map<String, Long> getTimeSplit(LocalDateTime start, LocalDateTime end) {
 *         Map<String, Long> timeSplit = new HashMap<>();
 *         long totalMinutes = ChronoUnit.MINUTES.between(start, end);
 *         long totalHours = totalMinutes / 60;
 *         int dayStartHour = 8;
 *         int dayEndHour = 17;
 *         int nightStartHour = 17;
 *         int nightEndHour = 24;
 *         long dayHours = 0;
 *         long nightHours = 0;
 *         long afterHours = 0;
 *         long overtime = 0;
 *         for (int i = 0; i < totalHours; i++) {
 *             int hour = start.plusHours(i).getHour();
 *             if (hour >= dayStartHour && hour < dayEndHour) {
 *                 dayHours++;
 *             } else if (hour >= nightStartHour && hour < nightEndHour) {
 *                 nightHours++;
 *             } else {
 *                 afterHours++;
 *             }
 *         }
 *         if (dayHours > 8) {
 *             overtime = dayHours - 8;
 *             dayHours = 8;
 *         }
 *         timeSplit.put("Day Hours", dayHours);
 *         timeSplit.put("Night Hours", nightHours);
 *         timeSplit.put("After Hours", afterHours);
 *         timeSplit.put("Overtime", overtime);
 *         return timeSplit;
 *     }
 * }
 */