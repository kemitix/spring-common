/*
 * The MIT License
 *
 * Copyright 2015 Paul Campbell.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.kemitix.spring.common.logging;

import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import static net.kemitix.spring.common.logging.TestValues.ON_DISPLAY;
import static net.kemitix.spring.common.logging.TestValues.PACKAGE_DEFAULT;
import static net.kemitix.spring.common.logging.TestValues.SECRET;
import static net.kemitix.spring.common.logging.TestValues.VISIBLE;
import net.kemitix.spring.common.logging.other.OtherPackageTestProperties;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.config.BeanPostProcessor;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 *
 * @author Paul Campbell
 */
public class LogFieldPostProcessorTest {

    private BeanPostProcessor postProcessor;

    @Before
    public void setUp() {
        postProcessor = new LogFieldPostProcessor();
    }

    /**
     * Test of postProcessBeforeInitialization method, of class
     * LogFieldPostProcessor.
     */
    @Test
    public void testPostProcessBeforeInitialization() {
        System.out.println("postProcessBeforeInitialization");
        //given
        Object bean = new Object();

        //when
        Object result = postProcessor.postProcessBeforeInitialization(bean, null);

        //then
        assertThat(result, is(bean));
    }

    /**
     * Test of postProcessAfterInitialization method, of class
     * LogFieldPostProcessor.
     */
    @Test
    public void testPostProcessAfterInitialization() {
        System.out.println("postProcessAfterInitialization");
        //given
        LoggerProvider bean = new TestProperties();
        Logger logger = mock(Logger.class);
        setField(bean, "logger", logger);

        //when
        Object result = postProcessor.postProcessAfterInitialization(bean, null);

        //then
        assertThat(result, is(bean));
        verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"visible", VISIBLE});
        verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"on display", ON_DISPLAY});

        verify(logger, times(0)).log(Level.INFO, "{0} : {1}", new Object[]{"secret", SECRET});
        verify(logger, times(0)).log(Level.INFO, "{0} : {1}", new Object[]{"packageDefault", PACKAGE_DEFAULT});
    }

    /**
     * Test of postProcessAfterInitialization method, of class
     * LogFieldPostProcessor.
     *
     * We use a class in a different package to confirm we can still see the
     * fields.
     */
    @Test
    public void testPostProcessAfterInitializationOtherPackage() {
        System.out.println("postProcessAfterInitialization other package");
        //given
        LoggerProvider bean = new OtherPackageTestProperties();
        Logger logger = mock(Logger.class);
        setField(bean, "logger", logger);

        //when
        Object result = postProcessor.postProcessAfterInitialization(bean, null);

        //then
        assertThat(result, is(bean));
        verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"visible", VISIBLE});
        verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"on display", ON_DISPLAY});

        verify(logger, times(0)).log(Level.INFO, "{0} : {1}", new Object[]{"secret", SECRET});
        verify(logger, times(0)).log(Level.INFO, "{0} : {1}", new Object[]{"packageDefault", PACKAGE_DEFAULT});
    }

    @Getter
    public static class TestProperties implements LoggerProvider {

        private Logger logger;

        private String secret = SECRET;

        @LogField
        protected String visible = VISIBLE;

        @LogField(name = "on display")
        public String onDisplay = ON_DISPLAY;

        String packageDefault = PACKAGE_DEFAULT;

    }

}
