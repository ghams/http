package HTTP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Daniel & Katarzyna
 */
public class HTTP_server {

    public static final int SERVER_PORT = 8080;
    public static final int SHUTDOWN_PORT = 8081;

    /**
     * 
     * Start the server running on SERVER_PORT
     * @throws IOException
     */
    public HTTP_server() throws IOException {
        ExecutorService pool = Executors.newCachedThreadPool();
        ServerSocket serverSock = new ServerSocket(SERVER_PORT);
        while (true) {
            System.out.println("Waiting for request..");
            Socket connectionSocket = serverSock.accept();
            connectionSocket.setSoTimeout(SHUTDOWN_PORT);
            Runnable service = new HTTP_service(connectionSocket);
            pool.execute(service);
            System.out.println("Connection made.");
        }

    }

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new HTTP_server();
    }
}
