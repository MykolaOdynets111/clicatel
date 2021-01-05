package util;

import lombok.experimental.UtilityClass;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

@UtilityClass
public class DateProvider {

    public static String getCurrentTimeStamp() {
        return now().format(ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
