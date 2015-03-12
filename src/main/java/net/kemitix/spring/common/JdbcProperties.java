package net.kemitix.spring.common;

import lombok.Getter;
import lombok.Setter;
import net.kemitix.spring.common.logging.LogField;
import net.kemitix.spring.common.logging.LoggerProvider;

@Setter
@Getter
public abstract class JdbcProperties implements LoggerProvider {

    @LogField(name = "jdbc driver")
    protected String driver;

    @LogField(name = "jdbc url")
    protected String url;

    @LogField(name = "jdbc user")
    protected String user;

    protected String password;

}
