package net.kemitix.spring.common.logging;

import java.util.logging.Level;
import java.util.logging.Logger;
import static net.kemitix.spring.common.logging.TestValues.ON_DISPLAY;
import static net.kemitix.spring.common.logging.TestValues.PACKAGE_DEFAULT;
import static net.kemitix.spring.common.logging.TestValues.SECRET;
import static net.kemitix.spring.common.logging.TestValues.VISIBLE;
import net.kemitix.spring.common.logging.other.OtherPackageTestProperties;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InOrder;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(BlockJUnit4ClassRunner.class)
public class PropertyLoggerTest {

    private PropertyLogger propertyLogger;

    @Before
    public void setUp() {
        propertyLogger = new PropertyLogger();
    }

    @Test
    public void shouldSeeFieldsInClassInSamePackage() {
        System.out.println("should see fields in class in same package");
        //given
        TestProperties properties = new TestProperties();

        //then
        assertThat(properties.packageDefault, is(PACKAGE_DEFAULT));
        assertThat(properties.onDisplay, is(ON_DISPLAY));
        assertThat(properties.visible, is(VISIBLE));
        // this would generate a compile error:
        //assertThat(properties.secret, is(SECRET));
    }

    @Test
    public void shouldLogFieldsInClassInSamePackage() throws Exception {
        System.out.println("should log fields in class in same package");
        //given
        LoggableProperties properties = new TestProperties();
        Logger logger = mock(Logger.class);
        setField(properties, "logger", logger);

        //when
        propertyLogger.logProperties(logger, properties);

        //then
        InOrder inOrder = Mockito.inOrder(logger);
        inOrder.verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"onDisplay", ON_DISPLAY});
        inOrder.verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"packageDefault", PACKAGE_DEFAULT});
        inOrder.verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"visible", VISIBLE});
        verify(logger, times(0)).log(Level.INFO, "{0} : {1}", new Object[]{
            "secret", SECRET
        });
    }

    @Test
    public void shouldLogFieldsInClassInDifferentPackage() throws Exception {
        System.out.println("should log fields in class in different package");
        //given
        LoggableProperties properties = new OtherPackageTestProperties();
        Logger logger = mock(Logger.class);
        setField(properties, "logger", logger);

        //when
        propertyLogger.logProperties(logger, properties);

        //then
        InOrder inOrder = Mockito.inOrder(logger);
        inOrder.verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"onDisplay", ON_DISPLAY});
        inOrder.verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"packageDefault", PACKAGE_DEFAULT});
        inOrder.verify(logger, times(1)).log(Level.INFO, "{0} : {1}", new Object[]{"visible", VISIBLE});
        verify(logger, times(0)).log(Level.INFO, "{0} : {1}", new Object[]{
            "secret", SECRET
        });
    }

}
