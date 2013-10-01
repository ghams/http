package HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Daniel & Katarzyna
 */
public class HTTP_server {

    public static final int SERVER_PORT = 8080;
    public static final String ROOT_CATALOG = "C:/Users/Daniel/Documents/NetBeansProjects/HTTP assignment/catalog";

    public static void main(String argv[]) throws Exception {

        try {
            ServerSocket serverSock = new ServerSocket(SERVER_PORT);
            while (true) {
                System.out.println("Waiting for request..");
                Socket connectionSocket = serverSock.accept();
                System.out.println("Connection made.");

                // input
                InputStream input = connectionSocket.getInputStream();
                input.read("GET /FILENAME   HTTP/1.1\r\n".getBytes());
                input.read("\r\n".getBytes());

                // output
                OutputStream output = connectionSocket.getOutputStream();
                output.write("HTTP/1.0 200 OK\r\n".getBytes());
                output.write("\r\n".getBytes());
                output.write("BODY".getBytes());
                output.flush();
                connectionSocket.close();
            }
        } catch (IOException ioe) {
            System.err.println("ERROR :" + ioe.toString());
        }
    }

    // not used yet
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
