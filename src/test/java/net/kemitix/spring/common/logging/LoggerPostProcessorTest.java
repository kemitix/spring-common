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

import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.springframework.test.util.ReflectionTestUtils.getField;

/**
 *
 * @author Paul Campbell
 */
public class LoggerPostProcessorTest {

    private LoggerPostProcessor loggerPostProcessor;

    @Before
    public void setUp() {
        loggerPostProcessor = new LoggerPostProcessor();
    }

    /**
     * Test of postProcessAfterInitialization method, of class
     * LoggerPostProcessor.
     */
    @Test
    public void testPostProcessAfterInitialization() {
        System.out.println("postProcessAfterInitialization");
        //given
        Object bean = new Object();

        //when
        Object result = loggerPostProcessor.postProcessAfterInitialization(bean, null);

        //then
        assertThat(result, is(bean));
    }

    /**
     * Test of postProcessBeforeInitialization method, of class
     * LoggerPostProcessor.
     */
    @Test
    public void testPostProcessBeforeInitialization() {
        System.out.println("postProcessBeforeInitialization");
        //given
        LoggerProvider bean = new LoggerProvider() {
            @Log
            public Logger logger;

            @Override
            public Logger getLogger() {
                return logger;
            }
        };

        //when
        Object result = loggerPostProcessor.postProcessBeforeInitialization(bean, null);

        //then
        assertThat(result, is(bean));
        Logger logger = (Logger) getField(result, "logger");
        assertThat(logger, is(not(nullValue())));
        assertThat(logger.getName(), containsString(this.getClass().getName()));
    }

}
