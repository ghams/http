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
    
    // not used yet
    private static void copy(final InputStream input, final OutputStream output) throws IOException {
        final byte[] buffer = new byte[1024];
        while(true) {
            int bytesRead = input.read(buffer);
            if (bytesRead == -1) { break; }
            output.write(buffer, 0, bytesRead);
        }
    }
    
    public static void main(String argv[]) throws Exception {

        try {
            ServerSocket serverSock = new ServerSocket(SERVER_PORT);
            while (true) {
                Socket clientSocket = serverSock.accept();

                // reading
                InputStream ls = clientSocket.getInputStream();
                Scanner fromClient = new Scanner(ls);

                // output
                OutputStream os = clientSocket.getOutputStream();
                PrintWriter toClient = new PrintWriter(os, true); //always flush

                // echo text
                String echoString = "No echo";
                if (fromClient.hasNextLine()) {
                    echoString = fromClient.nextLine();
                }
                System.out.println("Echo string: " + echoString + ":");
                toClient.println(echoString);
                toClient.flush();

                clientSocket.close();
            }
        } catch (IOException ioe) {
            System.err.println("ERROR :" + ioe.toString());
        }
    }
}
