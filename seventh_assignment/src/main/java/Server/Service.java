package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Service implements Runnable{
        private Socket client;
        private String name;
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
            out.writeUTF("Enter your name:");
            name = in.readUTF();
            String request;
            while (true) {
                out.writeUTF("CHOOSE: GROUP CHAT - LIST OF FILES - DOWNLOAD FILE - BACK - FINISH");
                request = in.readUTF();
                if (request.equals("FINISH")) {
                    break;
                } else if (request != null) {
                    response(request);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void response(String request) throws IOException {
        switch (request) {
            case "GROUP CHAT":
                Server.groupClients.add(client);
                chat();
//                out.writeUTF("Hi");
                break;
            case "LIST OF FILES":

                break;
            case "DOWNLOAD FILE":

                break;
            case "BACK":
                break;
            default:
                out.writeUTF("Wrong request!");
                out.flush();
        }

    }

    private void chat() throws IOException {
        while (true) {
            if (!Server.messages.isEmpty()) {
                for (String msg : Server.messages) {
                    out.writeUTF(msg);
                }
            }
            String message = in.readUTF();
            if (!message.equals("BACK")) {
                Server.messages.add(name + ": " + message);
                sentToAll(name + ": " + message);
            } else {
                Server.groupClients.remove(client);
                break;
            }
        }
    }
    private void sentToAll(String newMsg) throws IOException {
            for (Socket client : Server.groupClients){
                if (client != this.client) {
                    DataOutputStream out = new DataOutputStream(client.getOutputStream());
                    out.writeUTF(newMsg);
                }
            }
    }
}
