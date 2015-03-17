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

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Paul Campbell
 */
public class JdbcDataSourceTest {

    private JdbcDataSource dataSource;
    private JdbcProperties jdbcProperties;

    private static final String PASSWORD = "PASSWORD";
    private static final String USER = "USER";
    private static final String URL = "URL";
    private static final String DRIVER = "net.kemitix.spring.common.JdbcDataSourceTest$TestDriver";

    @Before
    public void setUp() {
        jdbcProperties = mock(JdbcProperties.class);
    }

    @Test
    public void testConstructorWithNamedDriverClass() {
        System.out.println("constructor with named driver class");
        //given

        //when
        when(jdbcProperties.getDriver()).thenReturn(DRIVER);
        when(jdbcProperties.getUrl()).thenReturn(URL);
        when(jdbcProperties.getUser()).thenReturn(USER);
        when(jdbcProperties.getPassword()).thenReturn(PASSWORD);
        dataSource = new JdbcDataSource(jdbcProperties);

        //then
        assertThat(dataSource.getUrl(), is(URL));
        assertThat(dataSource.getUsername(), is(USER));
        assertThat(dataSource.getPassword(), is(PASSWORD));
    }

    @Test
    public void testConstructorWithNoDriverClass() {
        System.out.println("constructor with no driver class");
        //given

        //when
        when(jdbcProperties.getUrl()).thenReturn(URL);
        when(jdbcProperties.getUser()).thenReturn(USER);
        when(jdbcProperties.getPassword()).thenReturn(PASSWORD);
        dataSource = new JdbcDataSource(jdbcProperties);

        //then
        assertThat(dataSource.getUrl(), is(URL));
        assertThat(dataSource.getUsername(), is(USER));
        assertThat(dataSource.getPassword(), is(PASSWORD));
    }

    public static class TestDriver implements Driver {

        @Override
        public Connection connect(String url, Properties info) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean acceptsURL(String url) throws SQLException {
            return url.startsWith("jdbc:test:");
        }

        @Override
        public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
            return new DriverPropertyInfo[]{};
        }

        @Override
        public int getMajorVersion() {
            return 1;
        }

        @Override
        public int getMinorVersion() {
            return 0;
        }

        @Override
        public boolean jdbcCompliant() {
            return true;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return mock(Logger.class);
        }

    }
}
