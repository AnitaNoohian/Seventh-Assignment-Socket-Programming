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
        private File[] files = new File[10];
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
                out.writeUTF("CHOOSE: GROUP CHAT - LIST OF FILES TO DOWNLOAD - BACK - FINISH");
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
            case "LIST OF FILES TO DOWNLOAD":
              //  check = true;
                download();
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
    private void download() throws IOException {
        int num = 1;
        for (String fileName: fileNames){
            out.writeUTF(num + "." + fileName);
            num += 1;
        }
        out.writeUTF("Enter the number of the file you want to download OR enter \"BACK\"");

        String input = in.readUTF();
        if (!input.equals("BACK")) {
//            BufferedReader reader = new BufferedReader(new FileReader(files[Integer.parseInt(input) - 1]));
//            PrintWriter writer = new PrintWriter(out);
//            String line;
//            while ((line = reader.readLine()) != null) {
//                writer.println(line);
//            }
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
    //    check = false;
    }
    private void addFile() {
        this.files[0] = new File("Server\\data\\all-of-me-john-legend.txt");
        this.files[1] = new File("E:\\AP\\Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\java\\Server\\data\\a-man-without-love-ngelbert-Hmperdinck.txt");
        this.files[2] = new File("\\java\\Server\\data\\birds-imagine-dragons.txt");
        this.files[3] = new File("\\java\\Server\\data\\blinding-lights-the-weekend.txt");
        this.files[4] = new File("\\java\\Server\\data\\dont-matter-to-me-drake.txt");
        this.files[5] = new File("\\java\\Server\\data\\feeling-in-my-body-elvis.txt");
        this.files[6] = new File("\\java\\Server\\data\\out-of-time-the-weekend.txt");
        this.files[7] = new File("\\java\\Server\\data\\something-in-the-way-nirvana.txt");
        this.files[8] = new File("\\java\\Server\\data\\why-you-wanna-trip-on-me-michael-jackson.txt");
        this.files[9] = new File("\\java\\Server\\data\\you-put-a-spell-on-me-austin-giorgio.txt");
    }

}
