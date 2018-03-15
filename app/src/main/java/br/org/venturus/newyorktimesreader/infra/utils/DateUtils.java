package br.org.venturus.newyorktimesreader.infra.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");

    private static final java.lang.String NYT_API_PATTERN = "yyyy-MM-dd";
    private static final java.lang.String DEFAULT_PATTERN = "dd/MM/YYYY";

    private DateUtils() {}

    public static Date parse(String date) throws ParseException {
        if (date == null) return null;
        return new SimpleDateFormat(NYT_API_PATTERN, DEFAULT_LOCALE).parse(date);
    }

    public static String format(Date date) {
        if (date == null) return null;
        return (new SimpleDateFormat(DEFAULT_PATTERN, DEFAULT_LOCALE)).format(date);
    }
}
