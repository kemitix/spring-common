package net.kemitix.spring.common.logging;

import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import static net.kemitix.spring.common.logging.TestValues.ON_DISPLAY;
import static net.kemitix.spring.common.logging.TestValues.PACKAGE_DEFAULT;
import static net.kemitix.spring.common.logging.TestValues.SECRET;
import static net.kemitix.spring.common.logging.TestValues.VISIBLE;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class TestProperties implements LoggerProvider {

    @Log
    private Logger logger;

    private String secret = SECRET;

    @LogField
    protected String visible = VISIBLE;

    @LogField(name = "on display")
    public String onDisplay = ON_DISPLAY;

    String packageDefault = PACKAGE_DEFAULT;
}
