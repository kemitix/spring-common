package net.kemitix.spring.common;

import java.io.IOException;
import net.kemitix.spring.common.ResourceReader;
import net.kemitix.spring.common.ResourceReaderImpl;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@RunWith(BlockJUnit4ClassRunner.class)
public class ResourceReaderImplTest {

    /**
     * Name of file in the classpath and it's content.
     */
    private static final String CLASSPATH_FILENAME = "sampleTextFile.txt";
    private static final String CLASSPATH_FILE_TEXT = "This is a test file.\n"
            + "It has text on two lines.\n";

    /**
     * Test of read method, of class ResourceReader.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testRead() throws IOException {
        System.out.println("read");
        //given
        Resource resource = new ClassPathResource(CLASSPATH_FILENAME);

        //when
        ResourceReaderImpl reader = new ResourceReaderImpl();

        //then
        String result = reader.read(resource);
        assertThat(result, equalTo(CLASSPATH_FILE_TEXT));
    }

    /**
     * Test of read method, of ResourceReader.
     *
     * Verify the concise example used in the Javadoc.
     *
     *     * @throws java.io.IOException
     */
    @Test
    public void testReadConciseJavadocExample() throws IOException {
        System.out.println("read javadoc concise example");
        //when
        String result
                = new ResourceReaderImpl()
                .read(new ClassPathResource(CLASSPATH_FILENAME));

        //then
        assertThat(result, equalTo(CLASSPATH_FILE_TEXT));
    }

    /**
     * Test of read method, of ResourceReader.
     *
     * Read file from a FifeSystemResource.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testReadFifeSystem() throws IOException {
        System.out.println("read from file system");
        //given
        Resource resource = new FileSystemResource("pom.xml");

        //when
        ResourceReader reader = new ResourceReaderImpl();

        //then
        String result = reader.read(resource);
        assertThat(result, containsString("net.kemitix"));
    }

}
