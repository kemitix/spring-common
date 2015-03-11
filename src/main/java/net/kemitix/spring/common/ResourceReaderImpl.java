package net.kemitix.spring.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Class, that is also a Spring @Component, for reading a
 * {@link org.springframework.core.io.ClassPathResource}.
 *
 * Handles the case where the Resource is a file within a Jar.
 *
 * Reads the file as UTF-8.
 *
 * <pre>
 *      String fileContent
 *          = new ResourceReader()
 *          .read(new ClassPathResource(filename));
 * </pre>
 *
 * @author Paul Campbell
 */
@Component
public class ResourceReaderImpl implements ResourceReader {

    @Override
    public String read(Resource resource) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        final BufferedReader reader = getReader(resource);
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private static BufferedReader getReader(Resource resource) throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        resource.getInputStream(), "UTF-8"));
    }

}
