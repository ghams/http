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

/**
 *
 * @author Daniel & Katarzyna
 */
public class HTTP_service implements Runnable {

    private final Socket connectionSocket;
    public static final String ROOT_CATALOG = "C:/assign/";
    public static final String CRLF = "\r\n";

    public HTTP_service(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        try {
            try {
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String request = fromClient.readLine();
                String[] parts = request.split(" ");
                String filename = parts[1];
                System.out.println(filename);

                PrintStream ps = new PrintStream(connectionSocket.getOutputStream());
                FileInputStream file = new FileInputStream(ROOT_CATALOG + filename);
                ps.print("HTTP/1.0 200 OK" + CRLF);
                ps.print(CRLF);
                copy(file, ps);
                ps.flush();
            } catch (FileNotFoundException ex) {
                PrintStream ps = new PrintStream(connectionSocket.getOutputStream());
                ps.print("HTTP/1.0 404 Not found: /doesNotExist.html" + CRLF);
                ps.print(CRLF);
                ps.flush();
                System.out.println(ex.getMessage());
            }
            connectionSocket.close();
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
}
