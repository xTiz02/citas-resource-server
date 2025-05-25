package org.prd.resourceserver.util;

import java.time.LocalDate;

public class UtilConstants {


    public static int getAge(int yearOfBirth) {
        return LocalDate.now().getYear() - yearOfBirth;
    }
}