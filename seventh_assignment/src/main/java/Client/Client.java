package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import com.google.gson.Gson;

// Client Class
public class Client {
    // TODO: Implement the client-side operations
    // TODO: Add constructor and necessary methods

    public static final int PORT = 4444;
    public static String[] nums = {"1","2","3","4","5","6","7","8","9","10"};
    public static void main(String[] args) throws IOException {

        // TODO: Implement the main method to start the client
        Socket client = new Socket("localhost",PORT);
        System.out.println("connected to server...");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        HandleResponses response = new HandleResponses(client);
        new Thread(response).start();
        while (true) {
            menu();
            run(in, out);
        }
    }

    public static void menu(){
        System.out.println("\"GROUP CHAT\" OR \"DOWNLOAD FILE\"");
        System.out.println("[Enter BACK whenever you want to go back!]");
    }
    public static void run(BufferedReader in,DataOutputStream out) throws IOException {
        String input,name = null;
        while (true) {
            input = in.readLine();
            Request request = null;
            Gson gson = new Gson();
            if (input.equals("GROUP CHAT")){
                request = new Request(input);
                String json = gson.toJson(request);
                out.writeUTF(json);
                name = in.readLine();
                request = new Request("NAME",name);
            } else if (input.equals("DOWNLOAD FILE")){
                request = new Request(input);
            } else if(input.equals("BACK")) {
                break;
            } else {
                boolean check = true;
                for (String num : nums){
                    if (input.equals(num)){
                        request = new Request("CHOOSE FILE");
                        check = false;
                    }
                }
                if (check) {
                    request = new Request("MESSAGE", name, input);
                }
                System.out.println("Wrong input!!!");
            }
            String json = gson.toJson(request);
            out.writeUTF(json);
        }
    }
}