package Server;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Provider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Server Class
public class Server {
    // TODO: Implement the server-side operations
    // TODO: Add constructor and necessary methods

    private static final int PORT = 1234;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(4);
    public static void main(String[] args) throws IOException {
        // TODO: Implement the main method to start the server
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Waiting for clients to connect...");

        while (true) {
            Socket socket = server.accept();
            System.out.println("Client connected!");
            Service service = new Service(socket);
            threadPool.execute(service);
        }

    }
}