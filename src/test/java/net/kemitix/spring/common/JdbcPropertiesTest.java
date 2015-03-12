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
package net.kemitix.spring.common;

import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Paul Campbell
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class JdbcPropertiesTest {

    /**
     * Test of get/set properties methods, of class JdbcProperties.
     */
    @Test
    public void testGetSetProperties() {
        System.out.println("get/setProperties");
        //given
        JdbcProperties properties = new JdbcProperties() {

            @Override
            protected Logger getLogger() {
                return mock(Logger.class);
            }
        };
        String driver = "DRIVER";
        String url = "URL";
        String user = "USER";
        String password = "PASSWORD";

        //when
        properties.setDriver(driver);
        properties.setUrl(url);
        properties.setUser(user);
        properties.setPassword(password);

        //then
        assertThat(properties.getDriver(), is(driver));
        assertThat(properties.getUrl(), is(url));
        assertThat(properties.getUser(), is(user));
        assertThat(properties.getPassword(), is(password));
    }

}
