package net.kemitix.spring.common.logging;

import java.util.Enumeration;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 * Logs the non-private fields in an object using the supplied {@link Logger}.
 *
 * @see LoggableProperties
 * @author Paul Campbell
 */
@Component
public class PropertyLogger {

    /**
     * Log the non-private fields of the subject to the logger.
     *
     * @param logger the {@link Logger} to log the properties to
     * @param subject the Object whose properties are to be logged
     */
    public void logProperties(Logger logger, LoggableProperties subject) {
        Properties properties = subject.getProperties();
        Enumeration<?> propertyNames = properties.propertyNames();
        SortedMap<String, String> sorted = new TreeMap<>();
        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            String value = properties.getProperty(name);
            sorted.put(name, value);
        }
        sorted.entrySet().stream().forEach((entry) -> {
            logger.log(Level.INFO, "{0} : {1}", new Object[]{
                entry.getKey(), entry.getValue()
            });
        });
    }
}
