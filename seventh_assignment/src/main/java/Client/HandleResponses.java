package Client;

import javax.imageio.IIOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HandleResponses implements Runnable{
    private DataInputStream in;
    public HandleResponses(Socket socket) throws IOException{
        this.in = new DataInputStream(socket.getInputStream());
    }
    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(in.readUTF());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
