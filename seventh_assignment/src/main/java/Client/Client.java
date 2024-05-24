package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// Client Class
public class Client {
    // TODO: Implement the client-side operations
    // TODO: Add constructor and necessary methods

    public static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        // TODO: Implement the main method to start the client
        Socket client = new Socket("localhost",PORT);
        System.out.println("connected to server...");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        String input;
        while (true) {
            input = in.readLine();
            out.writeUTF(input);
        }
    }
}