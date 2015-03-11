package net.kemitix.spring.common.logging;

import java.lang.reflect.Field;
import java.util.logging.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class LoggerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws
            BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, String beanName)
            throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), (Field field) -> {
            ReflectionUtils.makeAccessible(field);
            //Check if the field is annoted with @Log
            if (field.getAnnotation(Log.class) != null) {
                Logger logger = Logger.getLogger(bean.getClass().getName());
                field.set(bean, logger);
            }
        });
        return bean;
    }
}
