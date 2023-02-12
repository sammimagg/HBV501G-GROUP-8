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

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;

public class TimeSplit {

    // Dagvinna
    private int dayStartHour;
    private int eveningStartHour;
    private static final DecimalFormat decFormat = new DecimalFormat("0.00");

    public TimeSplit(int dayStartHour, int eveningStartHour) {
        this.dayStartHour = dayStartHour;
        this.eveningStartHour = eveningStartHour;
    }

    /**
     * Main function which calculates and maps results.
     *
     * @param shift_start LocalDateTime Starting time of shift
     * @param shift_end LocalDateTime Ending time of shift
     * @return hours_split Map<String, Double>
     */
    public Map<String, Double> getTimeSplit(LocalDateTime shift_start, LocalDateTime shift_end) {
        // Input Data
        long totalMinutes = ChronoUnit.MINUTES.between(shift_start, shift_end);

        // Reval
        Map<String, Double> timeSplit = new HashMap<>();
        double dayHours = 0;
        double eveningHours = 0;
        double nightHours = 0;
        double overTime = 0;

        // Allocating extra minutes
        int minutes_shift_start = 60 - shift_start.getMinute();
        int minutes_shift_end = shift_end.getMinute();

        if ( shift_start.getHour() < dayStartHour ) {
            nightHours += minutes_shift_start / 60.0;
        } else if ( shift_start.getHour() < eveningStartHour ) {
            dayHours += minutes_shift_start / 60.0;
        } else {
            eveningHours += minutes_shift_start / 60.0;
        }
        LocalDateTime new_shift_start = shift_start.withMinute(0).plusHours(1);

        if ( shift_end.getHour() < dayStartHour ) {
            nightHours += minutes_shift_end / 60.0;
        } else if ( shift_end.getHour() < eveningStartHour ) {
            dayHours += minutes_shift_end / 60.0;
        } else {
            eveningHours += minutes_shift_end / 60.0;
        }

        LocalDateTime new_shift_end = shift_end.withMinute(0);

        // Now we only need to calculate whole hours!
        while( new_shift_start.isBefore(new_shift_end)) {
            if ( new_shift_start.getHour() < dayStartHour ) {
                nightHours += 1;
            } else if ( new_shift_start.getHour() < eveningStartHour ) {
                dayHours += 1;
            } else {
                eveningHours += 1;
            }
            new_shift_start = new_shift_start.plusHours(1);
        }

        if ( dayHours + eveningHours + nightHours > 8) {
            overTime += min((dayHours + eveningHours + nightHours) - 8, dayHours);
            dayHours -= min((dayHours + eveningHours + nightHours) - 8, dayHours);
        }

        if ( dayHours + eveningHours + nightHours > 8) {
            overTime += min((dayHours + eveningHours + nightHours) - 8, eveningHours);
            eveningHours -= min((dayHours + eveningHours + nightHours) - 8, eveningHours);
        }

        if ( dayHours + eveningHours + nightHours > 8) {
            overTime += min((dayHours + eveningHours + nightHours) - 8, nightHours);
            nightHours -= min((dayHours + eveningHours + nightHours) - 8, nightHours);
        }


        timeSplit.put("DV", Double.valueOf(decFormat.format(dayHours)));
        timeSplit.put("EV", Double.valueOf(decFormat.format(eveningHours)));
        timeSplit.put("NV", Double.valueOf(decFormat.format(nightHours)));
        timeSplit.put("YV", Double.valueOf(decFormat.format(overTime)));

        System.out.println(dayHours + ", " + eveningHours + ", " + nightHours + ", " + overTime);
        return timeSplit;
    }
}