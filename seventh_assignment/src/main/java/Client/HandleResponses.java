package Client;

import Server.Request;
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
//        try {
//            while (true) {
//                String input = in.readUTF();
//                if (input.equals("1")) {
//                    receiveFile();
//                } else if (input.equals("GROUP CHAT")) {
//                    chat();
//                } else {
//                    System.out.println(input);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }
//    private void receiveFile() throws IOException {
//        int bytes = 0;
//        String path = in.readUTF();
//        FileOutputStream fileOutputStream = new FileOutputStream(path);
//
//        long size = in.readLong();
//        byte[] buffer = new byte[4 * 1024];
//        while (size > 0 && (bytes = in.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
//            fileOutputStream.write(buffer, 0, bytes);
//            size -= bytes;
//        }
//        System.out.println("File is Received");
//        fileOutputStream.close();
//    }
//    private void chat() throws IOException {
//        while (true) {
//            String input = in.readUTF();
//            System.out.println(input);
//            if (input.equals("BACK")) {
//                break;
//            }
//        }
//    }
}
