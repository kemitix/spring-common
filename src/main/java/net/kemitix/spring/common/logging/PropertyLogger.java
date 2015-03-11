package net.kemitix.spring.common.logging;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

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
    public void logProperties(Logger logger, Object subject) {

        // get all property names
        final List<String> propertyNames = new ArrayList<>();
        ReflectionUtils.doWithFields(subject.getClass(), (Field field) -> {
            propertyNames.add(field.getName());
        }, (Field field) -> !Modifier.isPrivate(field.getModifiers()));

        // get length of longest name
        int maxLength = 0;
        for (String propertyName : propertyNames) {
            if (propertyName.length() > maxLength) {
                maxLength = propertyName.length();
            }
        }

        // loop over properties and print them
        String nameFormat = String.format("%%%ds", maxLength);
        propertyNames.stream().forEach((propertyName) -> {
            try {
                logger.log(Level.INFO, "{0} : {1}", new Object[]{
                    String.format(nameFormat, propertyName),
                    ReflectionUtils.findField(subject.getClass(), propertyName).get(subject)
                });
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(PropertyLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
