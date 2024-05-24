package Client;

import Server.Service;

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
                System.out.println(in.readUTF());
                receiveFile(Service.check);
//                if (Service.check) {
//                    receiveFile();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                    FileWriter writer = new FileWriter("your_file.txt");
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        writer.write(line + "\n");
//                    }
//                    System.out.println("Your file has been downloaded!");
//                } else {
//                    System.out.println(in.readUTF());
//                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void receiveFile(boolean check) throws IOException {
        if (check) {
            int bytes = 0;
            FileOutputStream fileOutputStream = new FileOutputStream("YourFile.txt");

            long size = in.readLong();
            byte[] buffer = new byte[4 * 1024];
            while (size > 0 && (bytes = in.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                fileOutputStream.write(buffer, 0, bytes);
                size -= bytes;
            }
            System.out.println("File is Received");
            fileOutputStream.close();
        }
    }
}
