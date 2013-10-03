package HTTP;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Daniel & Katarzyna
 */
public class mapExtensions {

    Map<String, String> map;

    public mapExtensions() {
        this.map = new HashMap<String, String>();
    }

    /**
     * HashMap for Content Types
     * @return
     */
    public String extensions() {
        map.put("html", "text/html");
        map.put("htm", "text/html");
        map.put("doc", "application/msword");
        map.put("gif", "image/gif");
        map.put("jpg", "image/jpeg");
        map.put("pdf", "application/pdf");
        map.put("css", "text/css");
        map.put("xml", "text/xml");
        map.put("jar", "application/x-java-archive");
        map.put("avi", "video/msvideo");
        return ("Map: " + map);
    }
}
