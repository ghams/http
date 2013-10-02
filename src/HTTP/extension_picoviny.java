package HTTP;

import java.io.IOException;

/**
 *
 * @author Daniel
 */
public class extension_picoviny {

    private static final String wtf = "C:/assign/hola.html";

    public extension_picoviny() {
    }

    public String getContentType(String wtf) throws IOException {
        String[] parts = wtf.split(".");
        String filename = parts[2];
        return filename;
    }
}
