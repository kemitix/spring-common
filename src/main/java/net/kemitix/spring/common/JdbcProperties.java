package net.kemitix.spring.common;

import lombok.Getter;
import lombok.Setter;
import net.kemitix.spring.common.logging.LoggableProperties;

@Setter
@Getter
public abstract class JdbcProperties extends LoggableProperties {

    protected String driver;
    protected String url;
    protected String user;
    protected String password;

}
