package HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Daniel & Katarzyna
 */
public class HTTP_server {

    private static final int PORT = 8080;

    public static void main(String argv[]) throws Exception {
        
        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            while (true) {
                Socket clientSocket = serverSock.accept();
                
                // reading
                InputStream ls = clientSocket.getInputStream();
                Scanner fromClient = new Scanner(ls);
                
                // output
                OutputStream os = clientSocket.getOutputStream();
                PrintWriter toClient = new PrintWriter(os, true);
                
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
