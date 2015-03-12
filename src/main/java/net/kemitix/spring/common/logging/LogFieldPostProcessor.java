package net.kemitix.spring.common.logging;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class LogFieldPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LoggerProvider) {
            Logger logger = ((LoggerProvider) bean).getLogger();
            logger.log(Level.INFO, "Scanning: {0}", beanName);
            ReflectionUtils.doWithFields(bean.getClass(), (Field field) -> {
                ReflectionUtils.makeAccessible(field);
                LogField annotation = field.getAnnotation(LogField.class);
                //Check if the field is annoted with @LogField
                if (annotation != null) {
                    String name = annotation.name();
                    if (name.equals("")) {
                        name = field.getName();
                    }
                    String value = field.get(bean).toString();
                    logger.log(Level.INFO, "{0} : {1}", new Object[]{
                        name, value
                    });
                }
            });
        }
        return bean;
    }

}
