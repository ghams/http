package HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Daniel
 */
public class TCP_server {

    public static void main(String argv[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(8080);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(
                    new InputStreamReader(connectionSocket.getInputStream()));
            PrintStream outToClient = new PrintStream(
                    connectionSocket.getOutputStream());
            String clientSentence = inFromClient.readLine();
            System.out.println("FROM CLIENT: " + clientSentence);
            String capitalizedSentence = clientSentence.toUpperCase();
            outToClient.println(capitalizedSentence);
        }
    }
}
