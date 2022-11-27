/**
* Package which handles calculation for wage estimates
* and splitting worked hours into DV, EV, NV, YF
* 
* @depreceted
*/

package is.hi.hbv501g.group8.Utilities;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class WageCalculator {
    private double baseSalary;
    private double daytime;
    private double afterhours;
    private double nighttime;
    private double overtime;
    private static final Map<String, Double> conversion = new HashMap<String, Double>();

    public WageCalculator(double baseSalary) {
        defineConversions();
        this.baseSalary = baseSalary;
        this.daytime = (double) Math.round(baseSalary*conversion.get("DV")*100)/100;
        this.afterhours = (double) Math.round(baseSalary*conversion.get("EV")*100)/100;
        this.nighttime = (double) Math.round(baseSalary*conversion.get("NV")*100)/100;
        this.overtime = (double) Math.round(baseSalary*conversion.get("YV")*100)/100;
    }

    private void defineConversions() {
        conversion.put("DV", 1/159.57); // Daytime %
        conversion.put("EV", 0.875/100); // Afterhours %
        conversion.put("NV", 0.9375/100); // Nighttime %
        conversion.put("YV", 1.0385/100); // Overtime %
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public double getDaytime() {
        return daytime;
    }

    public double getAfterhours() {
        return afterhours;
    }

    public double getNighttime() {
        return nighttime;
    }

    public double getOvertime() {
        return overtime;
    }

    /*
    * Try #1, did not work
    */
    public String estimateWages(LocalTime shift_start, LocalTime shift_end) {
        double totalCost = 0.0;
        double hoursYV = 0.0;
        double hoursEV = 0.0;
        double hoursNV = 0.0;
        double hoursDV = 0.0;
        double totalHours = 0.0;

        Duration workedHours = Duration.between(shift_start, shift_end);
        totalHours = workedHours.toMinutes()/60.0;

        // Worked time > 12 = YV taka frá DV
        // Mismunur milli 24:00 og shift_end NV
        // Mismunur milli 16:00 og shift_end EV
        // Mismunur milli 08:00 og shift_end DV

        if(shift_end.isBefore(LocalTime.of(8, 0))){
            hoursNV += (Math.min(Duration.between(shift_start, shift_end).toMinutes(),
                    Duration.between(LocalTime.of(0,0), shift_end).toMinutes()))/60.0;
        }

        if(shift_end.isBefore(LocalTime.of(23,59))){
            hoursEV += (Math.min(Duration.between(shift_start, shift_end).toMinutes(),
                    Duration.between(LocalTime.of(16,0), shift_end).toMinutes())/60.0);
        }

        hoursYV = Math.max(totalHours-12, 0);
        hoursDV = totalHours - hoursNV - hoursEV - hoursYV;

        //return hoursNV;

        return String.format("DV: %.2f - EV: %.2f - NV: %.2f - YV: %.2f - TTL: %.2f", hoursDV, hoursEV, hoursNV, hoursYV, totalHours);
    }

    /*
    * Function that is SUPPOSED to split hours into DV, NV, EV, YF
    * Does not work as intended, but quite close to it!
    * @see WageCalculatorTest.java
    */
    public String splitHours(LocalTime shift_start, LocalTime shift_end){
        // 00 - 08: 0 - 480
        // 08 - 16: 480 - 960
        // 16 - 24: 960 - 1440

        double hDV, hEV, hNV, hYV, totalHours;
        int mDV = 0, mEV = 0, mNV = 0, mYV;
        int start = shift_start.getHour() * 60 + shift_start.getMinute();
        int end = shift_end.getHour() * 60 + shift_end.getMinute();

        int totalMinutes = end-start;
        totalHours = totalMinutes/60.0;
        hYV = Math.max(((end-start) / 60.0) - 12, 0);
        // If end < start:
        // Loopa loopa frá byrjunartíma að miðnætti
        // breyta byrjunartíma í 0000
        // halda áfarm niður:
        int ptr = start;
        /*
         *   Fyrir: ptr = start <= end
         *   Eftir: ptr > end, stöðugildi DV, EV, NV og YV hækka
         */
        while(ptr <= end){
            if(ptr < 480) mNV++;
            if(ptr >= 480 && ptr < 960) mDV++;
            if(ptr >= 960 && ptr < 1440) mEV++;
            ptr++;
        }

        // Formatting
        hDV = mDV/60.0;
        hNV = mNV/60.0;
        hEV = mEV/60.0;

        return String.format("DV: %.2f - EV: %.2f - NV: %.2f - YV: %.2f - TTL: %.2f", hDV, hEV, hNV, hYV, totalHours);
    }
}
