package com.softron.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    private DateUtils() {
        super();
    }

    public static LocalDate toLocaDate(final Date date) {
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
    }

    public static Date toDate(final LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String toDateStr(final LocalDate date) {
        return toDateStr(date, DEFAULT_DATE_FORMAT);
    }

    public static String toDateStr(final LocalDate date, String pattern) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate toLocalDate(final String date) {
        return toLocalDate(date, DEFAULT_DATE_FORMAT);
    }

    public static LocalDate toLocalDate(final String date, final String pattern) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

}
