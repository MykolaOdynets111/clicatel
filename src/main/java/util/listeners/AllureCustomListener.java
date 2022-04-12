package util.listeners;

import io.qameta.allure.Attachment;
import lombok.Synchronized;

public class AllureCustomListener {

    @Synchronized
    @Attachment(value = "SQL query", type = "text/plain")
    public static String attachQuery(final String query) {
        return query;
    }
}
