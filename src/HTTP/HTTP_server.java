package HTTP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Daniel & Katarzyna
 */
public class HTTP_server {

    public static final int SERVER_PORT = 8080;
    public static final String ROOT_CATALOG = "C:/_assign/hola.html";
    public static final String CRLF = "\r\n";

    public static void main(String argv[]) throws Exception {

        try {
            ServerSocket serverSock = new ServerSocket(SERVER_PORT);
            while (true) {
                System.out.println("Waiting for request..");
                Socket connectionSocket = serverSock.accept();
                System.out.println("Connection made.");

                // split GET /filename
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String request = fromClient.readLine();
                String[] parts = request.split(" ");
                String filename = parts[1];
                System.out.println(filename);

                // output
                OutputStream output = connectionSocket.getOutputStream();
                output.write(("HTTP/1.0 200 OK" + CRLF).getBytes());
                output.write(CRLF.getBytes());
                output.write("BODY".getBytes());
                output.flush();
                connectionSocket.close();

                FileInputStream file = new FileInputStream(ROOT_CATALOG);
                copy(file, output);
                file.close();


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
}
