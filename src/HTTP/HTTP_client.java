package HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Daniel & Katarzyna
 */
public class HTTP_client {

    // new instance
    public HTTP_client() {
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);

            // output
            OutputStream os = socket.getOutputStream();
            PrintWriter toServer = new PrintWriter(os, true);

            // reading
            InputStream is = socket.getInputStream();
            Scanner fromServer = new Scanner(is);

            // to server
            String text = "echo echo echo";
            toServer.printf(text);
            toServer.flush();

            // from server
            while (fromServer.hasNextLine()) {
                System.out.println(fromServer.nextLine());
            }

            socket.close();
        } catch (IOException ioe) {
            System.err.println("ERROR: " + ioe.toString());
        }
    }
}
