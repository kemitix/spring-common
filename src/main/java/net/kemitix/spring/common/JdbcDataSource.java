package net.kemitix.spring.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcDataSource extends DriverManagerDataSource {

    @Autowired
    public JdbcDataSource(JdbcProperties properties) {
        String driver = properties.getDriver();
        if (driver != null && driver.length() > 0) {
            setDriverClassName(properties.getDriver());
        }
        setUrl(properties.getUrl());
        setUsername(properties.getUser());
        setPassword(properties.getPassword());
    }

}
