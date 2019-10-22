package com.urice.webapp.util;

import java.time.Month;
import java.time.YearMonth;

public class DateUtil {
//    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
//    public static LocalDate of(int year, Month month) {
//        return LocalDate.of(year, month, 1);
//    }

    public static final YearMonth NOW = YearMonth.of(3000, 1);

    public static YearMonth of(int year, Month month) {
        return YearMonth.of(year, month);
    }
}
