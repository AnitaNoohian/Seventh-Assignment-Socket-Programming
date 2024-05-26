package Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Service implements Runnable{
        public static boolean check;
        private Socket client;
        private String name;
        private DataInputStream in;
        private DataOutputStream out;
        private File[] files = new File[10];        //pathes of the files clients can download
        private String[] fileNames = {"all of me", "a man without love", "birds", "blinding lights",
                "don't matter to me", "feeling in my body", "out of time", "something in the way",
                "why you wanna trip on me", "you put a spell on me"};
        public Service (Socket socket) throws IOException {
            addFile();
            check = true;
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
                out.writeUTF("CHOOSE: GROUP CHAT - LIST OF FILES TO DOWNLOAD");
                request = in.readUTF();
                if (request.equals("BACK")) {
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
                out.writeUTF("GROUP CHAT");
                Server.groupClients.add(client);
                chat();
                break;
            case "LIST OF FILES TO DOWNLOAD":
                download();
                break;
            default:
                out.writeUTF("Wrong request!");
                out.flush();
        }

    }

    private void chat() throws IOException {
        out.writeUTF("Enter \"BACK\" whenever you want to leave the group chat!");
        if (!Server.messages.isEmpty()) {       //show all past message to a new client in chat group
            for (String msg : Server.messages) {
                out.writeUTF(msg);
            }
        }
        while (true) {
            String message = in.readUTF();
            if (!message.equals("BACK")) {
                Server.messages.add(name + ": " + message);     //save the messages
                sentToAll(name + ": " + message);       //send each message to all clients
            } else {
                Server.groupClients.remove(client);
                break;
            }
        }
    }
    private void sentToAll(String newMsg) throws IOException {
        for (Socket client : Server.groupClients){      //show a message in group chat for all clients in the chat
        //    if (client != this.client) {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                out.writeUTF(newMsg);
        //    }
        }
    }
    private void download() throws IOException {
        int num = 1;
        for (String fileName: fileNames){       //print file names for clients
            out.writeUTF(num + "." + fileName);
            num += 1;
        }
        out.writeUTF("Enter the number of the file you want to download OR enter \"BACK\"");

        String input = in.readUTF();
        out.writeUTF("Please enter the path of where you want to save the file:\n" +
                "(for example: C:\\Users\\user\\Downloads\\File_Name.txt)");
        String path = in.readUTF();
        if (!input.equals("BACK")) {
            out.writeUTF("1"); //response for client to figure out what to do
            out.writeUTF(path);
            sendFile(input);
        }
    }
    private void sendFile(String input) throws IOException {
        int bytes = 0;
        File file = files[Integer.parseInt(input) - 1];
        FileInputStream fileInputStream = new FileInputStream(file);

        out.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, bytes);
            out.flush();
        }
        fileInputStream.close();
    }
    private void addFile() {
        this.files[0] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\all-of-me-john-legend.txt");
        this.files[1] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\a-man-without-love-ngelbert-Hmperdinck.txt");
        this.files[2] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\birds-imagine-dragons.txt");
        this.files[3] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\blinding-lights-the-weekend.txt");
        this.files[4] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\dont-matter-to-me-drake.txt");
        this.files[5] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\feeling-in-my-body-elvis.txt");
        this.files[6] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\out-of-time-the-weekend.txt");
        this.files[7] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\something-in-the-way-nirvana.txt");
        this.files[8] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\why-you-wanna-trip-on-me-michael-jackson.txt");
        this.files[9] = new File("seventh_assignment\\src\\main\\java\\Server\\data\\you-put-a-spell-on-me-austin-giorgio.txt");
    }

}
