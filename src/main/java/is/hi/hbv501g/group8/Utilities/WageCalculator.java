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
        this.daytime = (double) Math.round(baseSalary * conversion.get("DV") * 100) / 100;
        this.afterhours = (double) Math.round(baseSalary * conversion.get("EV") * 100) / 100;
        this.nighttime = (double) Math.round(baseSalary * conversion.get("NV") * 100) / 100;
        this.overtime = (double) Math.round(baseSalary * conversion.get("YV") * 100) / 100;
    }

    private void defineConversions() {
        conversion.put("DV", 1 / 159.57); // Daytime %
        conversion.put("EV", 0.875 / 100); // Afterhours %
        conversion.put("NV", 0.9375 / 100); // Nighttime %
        conversion.put("YV", 1.0385 / 100); // Overtime %
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

    public String estimateWages(LocalTime shift_start, LocalTime shift_end) {
        double totalCost = 0.0;
        double hoursYV = 0.0;
        double hoursEV = 0.0;
        double hoursNV = 0.0;
        double hoursDV = 0.0;
        double totalHours = 0.0;

        Duration workedHours = Duration.between(shift_start, shift_end);
        totalHours = workedHours.toMinutes() / 60.0;

        // Worked time > 12 = YV taka frá DV
        // Mismunur milli 24:00 og shift_end NV
        // Mismunur milli 16:00 og shift_end EV
        // Mismunur milli 08:00 og shift_end DV

        if (shift_end.isBefore(LocalTime.of(8, 0))) {
            hoursNV += (Math.min(Duration.between(shift_start, shift_end).toMinutes(),
                    Duration.between(LocalTime.of(0, 0), shift_end).toMinutes())) / 60.0;
        }

        if (shift_end.isBefore(LocalTime.of(23, 59))) {
            hoursEV += (Math.min(Duration.between(shift_start, shift_end).toMinutes(),
                    Duration.between(LocalTime.of(16, 0), shift_end).toMinutes()) / 60.0);
        }

        hoursYV = Math.max(totalHours - 12, 0);
        hoursDV = totalHours - hoursNV - hoursEV - hoursYV;

        //return hoursNV;

        return String.format("DV: %.2f - EV: %.2f - NV: %.2f - YV: %.2f - TTL: %.2f", hoursDV, hoursEV, hoursNV, hoursYV, totalHours);
    }

    // Function that divides time into normal hours, after hours, overtime, nighttime
    public String splitHours(LocalTime shift_start, LocalTime shift_end) {
        // OT = More than 8 hours
        // AH = Work between 16:00 and 24:00
        // NT = Work between 00:00 and 08:00
        // NH = Work between 08:00 and 16:00

        int OT = 0;
        int AH = 0;
        int NT = 0;
        int NH = 0;

        if (shift_start.isBefore(shift_end)) {
            if (shift_start.isBefore(LocalTime.of(16, 0)) && shift_end.isAfter(LocalTime.of(16, 0))) {
                NH = LocalTime.of(16, 0).getHour() - shift_start.getHour();
                AH = shift_end.getHour() - LocalTime.of(16, 0).getHour();
            } else if (shift_start.isBefore(LocalTime.of(8, 0)) && shift_end.isAfter(LocalTime.of(8, 0))) {
                NT = LocalTime.of(8, 0).getHour() - shift_start.getHour();
                NH = shift_end.getHour() - LocalTime.of(8, 0).getHour();
            } else if (shift_start.isBefore(LocalTime.of(0, 0)) && shift_end.isAfter(LocalTime.of(0, 0))) {
                OT = LocalTime.of(0, 0).getHour() - shift_start.getHour();
                NT = shift_end.getHour() - LocalTime.of(0, 0).getHour();
            } else {
                NH = shift_end.getHour() - shift_start.getHour();
            }
        } else {
            if (shift_start.isBefore(LocalTime.of(16, 0)) && shift_end.isAfter(LocalTime.of(16, 0))) {
                NH = LocalTime.of(16, 0).getHour() - shift_start.getHour();
                OT = shift_end.getHour() - LocalTime.of(16, 0).getHour();
            } else if (shift_start.isBefore(LocalTime.of(8, 0)) && shift_end.isAfter(LocalTime.of(8, 0))) {
                NT = LocalTime.of(8, 0).getHour() - shift_start.getHour();
                NH = shift_end.getHour() - LocalTime.of(8, 0).getHour();
            } else if (shift_start.isBefore(LocalTime.of(0, 0)) && shift_end.isAfter(LocalTime.of(0, 0))) {
                OT = LocalTime.of(0, 0).getHour() - shift_start.getHour();
                NT = shift_end.getHour() - LocalTime.of(0, 0).getHour();
            } else {
                NH = shift_end.getHour() - shift_start.getHour();
            }
        }
        return "OT: " + OT + " AH: " + AH + " NT: " + NT + " NH: " + NH;
    }
}
