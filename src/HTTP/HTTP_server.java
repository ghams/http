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

    public HTTP_server() throws IOException {
        ExecutorService pool = Executors.newCachedThreadPool();
        ServerSocket serverSock = new ServerSocket(SERVER_PORT);
        while (true) {
            System.out.println("Waiting for request..");
            Socket connectionSocket = serverSock.accept();
            Runnable service = new HTTP_service(connectionSocket);
            pool.execute(service);
            System.out.println("Connection made.");
        }
    }

    public static void main(String[] args) throws IOException {
        new HTTP_server();
    }
}
