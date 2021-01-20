package util;

import lombok.Synchronized;
import lombok.experimental.UtilityClass;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.time.format.DateTimeFormatter.ofPattern;

@UtilityClass
public class DateProvider {
    @Synchronized
    public static String getCurrentTimeStamp() {
        return now().format(ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Synchronized
    public static String getCurrentReportDate() {
        return now().format(ofPattern("yyyy-MM-dd"));
    }

    @Synchronized
    public static String getCurrentTransactionDate() {
        return now().format(ofPattern("MMMM dd, yyyy"));
    }

    @Synchronized
    public static String getCurrentApplicationDate() {
        return now().format(ofPattern("MMM d, yyyy"));
    }

    public static String getCurrentTimeStampByPattern(final String pattern) {
        return now().format(ofPattern(pattern));
    }

    public static String getTimeStampMinusMonthsByPattern(final int monthDays, final String pattern) {
        return now().minusMonths(monthDays).format(ofPattern(pattern));
    }

    public static String getTimeStampPlusMonthsByPattern(final int monthDays, final String pattern) {
        return now().plusMonths(monthDays).format(ofPattern(pattern));
    }

    public static String getCurrentIsoDateTime() {
        return now().format(ISO_LOCAL_DATE_TIME) + "Z";
    }
}

