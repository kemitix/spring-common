package net.kemitix.spring.common.logging.other;

import net.kemitix.spring.common.logging.*;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import static net.kemitix.spring.common.logging.TestValues.ON_DISPLAY;
import static net.kemitix.spring.common.logging.TestValues.PACKAGE_DEFAULT;
import static net.kemitix.spring.common.logging.TestValues.SECRET;
import static net.kemitix.spring.common.logging.TestValues.VISIBLE;

@Setter
@Getter
public class OtherPackageTestProperties extends LoggableProperties {

    private Logger logger;

    private String secret = SECRET;
    protected String visible = VISIBLE;
    public String onDisplay = ON_DISPLAY;
    String packageDefault = PACKAGE_DEFAULT;
}
