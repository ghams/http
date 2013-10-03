package HTTP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel & Katarzyna
 */
public class HTTP_service implements Runnable {

    private final Socket connectionSocket;
    private static final Logger LOGGER = Logger.getLogger("server");
    public static final String ROOT_CATALOG = "C:/assign/";
    public static final String CRLF = "\r\n";

    /**
     *
     * @param connectionSocket
     */
    public HTTP_service(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Request from " + connectionSocket);
        try {
            try {
                // Request & split /GET
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String request = fromClient.readLine();
                String[] parts = request.split(" ");
                String filename = parts[1];
                LOGGER.log(Level.INFO, "Request: " + request);
                System.out.println(filename);

                // get file extension
                int last = filename.lastIndexOf(".");
                String ext = null;
                if (last != -1) {
                    ext = filename.substring(last + 1, filename.length());
                }
                System.out.println(ext);

                // Read file
                PrintStream ps = new PrintStream(connectionSocket.getOutputStream());
                FileInputStream file = new FileInputStream(ROOT_CATALOG + filename);
                ps.print("HTTP/1.0 200 OK" + CRLF);
//                ps.print("Content-type: " + getContentType(filename)+ CRLF);
                ps.print(CRLF);
                copy(file, ps);
                ps.flush();

                // 404
            } catch (FileNotFoundException ex) {
                PrintStream ps = new PrintStream(connectionSocket.getOutputStream());
                ps.print("HTTP/1.0 404 Not found: /doesNotExist.html" + CRLF);
                ps.print(CRLF);
                ps.flush();
                System.out.println(ex.getMessage());
            } finally {
                connectionSocket.close();
                LOGGER.log(Level.WARNING, "Connection close.");
            }
        } catch (IOException ioe) {
            System.err.println("ERROR :" + ioe.toString());
        }
    }

    private static void copy(final InputStream input, final OutputStream output) throws IOException {
        final byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = input.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            output.write(buffer, 0, bytesRead);
        }
    }
    
    /**
     *
     * Does not work as intended.
     * @param filename
     * @return
     */
    public static String getContentType(String filename) {
        if (filename.endsWith(".html") || filename.endsWith(".htm"))
            return "text/html";
        else if (filename.endsWith(".gif"))
            return "image/gif";
        else if (filename.endsWith(".jpg"))
            return "image/jpeg";
        else if (filename.endsWith(".pdf"))
            return "application/pdf";
        else if (filename.endsWith(".xml"))
            return "text/xml";
        else if (filename.endsWith(".doc") || filename.endsWith(".docx"))
            return "application/msword";
        else
            return "text/plain";
    }
}
