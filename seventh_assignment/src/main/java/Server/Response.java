package Server;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class Response {

    private String resType;
    private String message;
    private String name;
    private int numFile;
    private static File[] files = new File[10];        //paths of the files clients can download
    private static String[] fileNames = {"all of me", "a man without love", "birds", "blinding lights",
            "don't matter to me", "feeling in my body", "out of time", "something in the way",
            "why you wanna trip on me", "you put a spell on me"};

    public Response(String type){
        this.resType = type;
        if (type.equals("DOWNLOAD FILE")){
            addFile();
        }
    }
    public Response(String type, String name){
        this.resType = type;
        this.name = name;
        Service.name = name;
    }
    public Response(String type, String name, String message) throws IOException {
        this.resType = type;
        this.name = name;
        this.message = message;
    }
    public Response(String type, int num){
        this.resType = type;
        this.numFile = num;
    }
    public void doWork() throws IOException {
        if (resType.equals("GROUP CHAT")){
            System.out.println("Enter your name:");
        } else if(resType.equals("NAME")) {
            history();
        } else if(resType.equals("MESSAGE")) {
            Server.messages.add(name + ": " + message);
            sentToAll(message);
            System.out.println(name + ": " + message);
        } else if (resType.equals("MESSAGE2")){
            System.out.println(name + ": " + message);
        } else if(resType.equals("DOWNLOAD FILE")){
            listOfFiles();
        } else if(resType.equals("CHOOSE FILE")){
            sendFile(numFile);
        } else {

        }
    }
    private void sentToAll(String newMsg) throws IOException {
        for (Socket client : Server.groupClients){      //show a message in group chat for all clients in the chat
            if (client != Service.client) {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                Response response = new Response("MESSAGE2", name, newMsg);
                Gson gson = new Gson();
                String json = gson.toJson(response);
                out.writeUTF(json);
            }
        }
    }
    private void history(){
        if (!Server.messages.isEmpty()) {       //show all past message to a new client in chat group
            for (String msg : Server.messages) {
                System.out.println(msg);
            }
        }
    }
    private void listOfFiles(){
        int num = 1;
        for (String fileName: fileNames){       //print file names for clients
            System.out.println(num + "." + fileName);
            num += 1;
        }
        System.out.println("Enter the number of the file you want to download OR enter \"BACK\"");
    }
    private void sendFile(int input) throws IOException {
        File file = Service.files[input - 1];
        FileReader fileInputStream = new FileReader(file);

        FileWriter fileOutputStream = new FileWriter("yourFile.txt");
        String str = " ";
        int i;
        while ((i = fileInputStream.read()) != -1) {
            str += ((char) i);
        }
        fileOutputStream.write(str);
        
        fileInputStream.close();
        fileOutputStream.close();
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
