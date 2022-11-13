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
        this.daytime = baseSalary*conversion.get("DV");
        this.afterhours = baseSalary*conversion.get("EV");
        this.nighttime = baseSalary*conversion.get("NV");
        this.overtime = baseSalary*conversion.get("YV");
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

    public double estimateWages(LocalTime shift_start, LocalTime shift_end) {
        double totalCost = 0.0;
        double hoursYV = 0.0;
        double hoursEV = 0.0;
        double hoursNV = 0.0;
        double hoursDV = 0.0;
        double totalHours = 0.0;

        Duration workedHours = Duration.between(shift_start, shift_end);
        totalHours = workedHours.toHours();

        // Worked time > 12 = YV
        // Mismunur milli 24:00 og shift_end
        // Mismunur milli 16:00 og shift_end
        // Mismunur milli 08:00 og shift_end


        return totalHours;
    }
}
