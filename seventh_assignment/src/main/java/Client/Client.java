package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import Server.Server;
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
        String input;
        main:
        while (true) {
            input = in.readLine();
            Request request = null;
            Gson gson = new Gson();
            if (input.equals("GROUP CHAT")){
                request = new Request(input);
                String json = gson.toJson(request);
                out.writeUTF(json);
                String name = in.readLine();
                request = new Request("NAME",name);
                String json1 = gson.toJson(request);
                out.writeUTF(json1);
                while (true) {
                   String msg = in.readLine();
                   if (msg.equals("BACK")) {
                       request = new Request("BACK");
                       String json2 = gson.toJson(request);
                       out.writeUTF(json2);
                       break main;
                   } else {
                       request = new Request("MESSAGE", name, msg);
                       String json2 = gson.toJson(request);
                       out.writeUTF(json2);
                   }
                }
            } else if (input.equals("DOWNLOAD FILE")){
                request = new Request(input);
                String json = gson.toJson(request);
                out.writeUTF(json);
                String num = in.readLine();
                if (num.equals("BACK")){
                    break main;
                } else {
                    request = new Request("CHOOSE FILE", Integer.parseInt(num));
                    String json1 = gson.toJson(request);
                    out.writeUTF(json1);
                    System.out.println("Your file has been downloaded!\n");
                    break main;
                }
            } else if(input.equals("BACK")) {
                break;
            }
        }
    }
}