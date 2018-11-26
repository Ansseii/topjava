package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.*;

public class DateTimeFormatter {

    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return LocalDate.parse(text);
        }

        @Override
        public String print(LocalDate object, Locale locale) {
            return object.format(ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return LocalTime.parse(text);
        }

        @Override
        public String print(LocalTime object, Locale locale) {
            return object.format(ISO_LOCAL_TIME);
        }
    }
}
