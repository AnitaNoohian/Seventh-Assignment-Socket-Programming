package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Service implements Runnable{
        private Socket client;
        private DataInputStream in;
        private DataOutputStream out;

        public Service (Socket socket) throws IOException {
            this.client = socket;
            this.in = new DataInputStream(client.getInputStream());
            this.out = new DataOutputStream(client.getOutputStream());
        }


    @Override
    public void run() {
            try {
                String request;
                while (true) {
                    out.writeUTF("CHOOSE: GROUP CHAT - LIST OF FILES - DOWNLOAD FILE");
                    request = in.readUTF();
                    if (request != null) {
                        response(request);
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {

            }

    }

    private void response(String request) throws IOException {
        switch (request) {
            case "GROUP CHAT":

                break;
            case "LIST OF FILES":

                break;
            case "DOWNLOAD FILE":

                break;
            default:
                out.writeUTF("Wrong request!");
        }

    }
}
