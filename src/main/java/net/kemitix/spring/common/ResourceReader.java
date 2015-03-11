package net.kemitix.spring.common;

import java.io.IOException;
import org.springframework.core.io.Resource;

/**
 * Interface for reading a {@link Resource}.
 *
 * @author Paul Campbell
 */
public interface ResourceReader {

    /**
     * Reads the {@link Resource} into a String.
     *
     * @param resource the {@link Resource} to read
     * @return the contents of the {@link Resource}
     * @throws IOException if Resource is not found or can't be opened or read
     */
    String read(Resource resource) throws IOException;

}
