package org.prd.resourceserver.util;

import java.time.LocalDate;
import java.util.List;

public class UtilConstants {


    public static int getAge(int yearOfBirth) {
        return LocalDate.now().getYear() - yearOfBirth;
    }

    public static boolean isValidDuration(int duration) {
        List<Integer> validDurations = List.of(15, 30);
        return validDurations.contains(duration);
    }

    public static boolean isValidTimes(String... times) {
        for (String t : times) {
            if (t == null || t.isEmpty()) {
                return false;
            }
            String[] parts = t.split(":");
            if (parts.length != 2) {
                return false;
            }
            try {
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                if (!(hours >= 0 && hours < 24) && (minutes == 30 || minutes == 0)){
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;

    }

    public static boolean isDateRangeOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }




    public static DateRangeChangeType detectarCambioFechas(LocalDate startOld, LocalDate endOld, LocalDate startNew, LocalDate endNew) {
        if (startNew.equals(startOld) && endNew.equals(endOld)) {
            return DateRangeChangeType.NO_CHANGE;
        }
        if (startNew.isAfter(startOld) && endNew.equals(endOld)) {
            return DateRangeChangeType.START_CHANGED;
        }
        if (startNew.equals(startOld) && endNew.isBefore(endOld)) {
            return DateRangeChangeType.END_CHANGED;
        }
        if (startNew.isAfter(startOld) && endNew.isBefore(endOld)) {
            return DateRangeChangeType.SHRINKED_INSIDE;
        }
        if (startNew.isAfter(startOld) || endNew.isBefore(endOld)) {
            return DateRangeChangeType.BOTH_CHANGED;
        }
        return DateRangeChangeType.INVALID; // fuera del rango original
    }


}