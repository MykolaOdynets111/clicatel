package util;

import lombok.extern.java.Log;

import static java.lang.String.format;
import static java.lang.Thread.sleep;

@Log
public class Waits {

    public static void waitForSystemLoading(final long timeoutSeconds){
        try {
            sleep(timeoutSeconds * 1000);
        } catch (InterruptedException e){
            log.warning(format("Wait for system loading was interrupted by: %s", e.getMessage()));
            if (e.getCause()!=null){
                log.warning(format("The cause of interruptions is: %s", e.getCause().getMessage()));
            }
        }
        Thread.currentThread().interrupt();
    }
}
