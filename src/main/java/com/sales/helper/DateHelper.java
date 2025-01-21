package com.sales.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateHelper {
    public static String dateParse(LocalDate date, String pattern, Locale format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, format);
        String dateFormatted = date.format(formatter);
        return dateFormatted;
    }
}
