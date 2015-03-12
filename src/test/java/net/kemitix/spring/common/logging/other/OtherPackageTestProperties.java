package net.kemitix.spring.common.logging.other;

import java.util.logging.Logger;
import lombok.Getter;
import net.kemitix.spring.common.logging.LogField;
import net.kemitix.spring.common.logging.LoggerProvider;
import static net.kemitix.spring.common.logging.TestValues.ON_DISPLAY;
import static net.kemitix.spring.common.logging.TestValues.PACKAGE_DEFAULT;
import static net.kemitix.spring.common.logging.TestValues.SECRET;
import static net.kemitix.spring.common.logging.TestValues.VISIBLE;

@Getter
public class OtherPackageTestProperties implements LoggerProvider {

    private Logger logger;

    private String secret = SECRET;

    @LogField
    protected String visible = VISIBLE;

    @LogField(name = "on display")
    public String onDisplay = ON_DISPLAY;

    String packageDefault = PACKAGE_DEFAULT;
}
