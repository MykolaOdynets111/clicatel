package util;

import lombok.extern.java.Log;
import lombok.var;

import java.util.Properties;

import static java.lang.String.format;

/**
 * Class util, to work with reading config.properties file values directly
 * <p>
 *
 * @author ksichenko
 */

@Log
public final class PropertiesReader {

    private PropertiesReader() {
    }

    private static final String CONFIG_PROPERTIES = "config.properties";

    private static Properties PROPERTIES;

    public static String getProperty(final String propertyName) {

        if (PROPERTIES == null) {
            synchronized (PropertiesReader.class) {
                if (PROPERTIES == null) {
                    try (var reader = PropertiesReader.class
                            .getClassLoader()
                            .getResourceAsStream(CONFIG_PROPERTIES)) {

                        var properties = new Properties();
                        properties.load(reader);
                        PROPERTIES = properties;
                    } catch (Exception ex) {
                        throw new IllegalStateException(format("An issue: '%s' occurred during reading config.properties file !!", ex));
                    }
                }
            }
        }

        var systemProperty = System.getProperty(propertyName);

        return systemProperty == null
                ? PROPERTIES.getProperty(propertyName, "Wrong config.properties name was set !!")
                : systemProperty;
    }
}
