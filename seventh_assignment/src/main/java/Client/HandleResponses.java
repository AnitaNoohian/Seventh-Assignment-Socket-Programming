package Client;

import Server.Request;
import Server.Server;
import Server.Service;
import com.google.gson.Gson;

import javax.imageio.IIOException;
import java.io.*;
import java.net.Socket;
import java.security.Provider;

public class HandleResponses implements Runnable{
    private DataInputStream in;
    public HandleResponses(Socket socket) throws IOException{
        this.in = new DataInputStream(socket.getInputStream());
    }
    @Override
    public void run() {
        try {
            while (true) {
                String input = in.readUTF();
                Gson gson = new Gson();
                Response response = gson.fromJson(input, Response.class);
                response.doWork();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
