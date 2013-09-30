package HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Daniel
 */
public class TCP_client {

    public static void main(String argv[]) throws Exception {
        int port = 8080;  //default
        if (argv.length > 0) {
            port = Integer.parseInt(argv[0]);
        }
        BufferedReader inFromUser = new BufferedReader(
                new InputStreamReader(System.in));
        // To server on local host
        Socket clientSocket = new Socket("127.0.0.1", port);
        // To server on other host with IP-address = 83.92.58.109
        //Socket clientSocket = new Socket("83.92.58.109", port);
        PrintStream outToServer = new PrintStream(
                clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String sentence = inFromUser.readLine();
        outToServer.println(sentence);
        String modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();
    }
}
