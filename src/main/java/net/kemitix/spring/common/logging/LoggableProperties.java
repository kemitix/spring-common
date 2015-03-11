package net.kemitix.spring.common.logging;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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

    @Autowired
    private PropertyLogger propertyLogger;

    @PostConstruct
    public void logProperties() {
        propertyLogger.logProperties(getLogger(), this);
    }

}
