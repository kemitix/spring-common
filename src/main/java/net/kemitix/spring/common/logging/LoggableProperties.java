package net.kemitix.spring.common.logging;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Causes subclassed @Components to automatically have their non-private fields
 * logged by the PropertyLogger.
 *
 * @see PropertyLogger
 * @author Paul Campbell
 */
public abstract class LoggableProperties {

    protected abstract Logger getLogger();

    protected Properties getProperties() {
        BeanWrapper bean = PropertyAccessorFactory.forBeanPropertyAccess(this);
        final Class<? extends LoggableProperties> subjectClass = this.getClass();
        Field[] fields = subjectClass.getDeclaredFields();
        Properties properties = new Properties();
        for (Field field : fields) {
            Class<?> propertyType = field.getType();
            if (propertyType.equals(Logger.class)
                    || propertyType.equals(Class.class)
                    || Modifier.isPrivate(field.getModifiers())) {
                continue;
            }
            String name = field.getName();
            try {
                properties.setProperty(name,
                        (String) bean.getPropertyDescriptor(name)
                        .getReadMethod().invoke(this));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(LoggableProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return properties;
    }

    @Autowired
    private PropertyLogger propertyLogger;

    @PostConstruct
    public void logProperties() {
        propertyLogger.logProperties(getLogger(), this);
    }

}
