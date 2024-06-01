package Server;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Service implements Runnable{
  //  public static boolean check;
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
            while (true) {
                String input = in.readUTF();
                Gson gson = new Gson();
                Request request = gson.fromJson(input, Request.class);
                String type = request.getReqType(); //get request from socket and do base on what type it is
                if (type.equals("GROUP CHAT")) {
                    Server.groupClients.add(client);
                    Response response = new Response("GROUP CHAT");
                    String json = gson.toJson(response);
                    out.writeUTF(json);
                } else if (type.equals("NAME")) {
                    name = request.getName();
                    Response response = new Response("NAME", name);
                    String json = gson.toJson(response);
                    out.writeUTF(json);
                    history();
                } else if (type.equals("MESSAGE")) {
                    Server.messages.add(name + ": " + request.getMessage());
                    sentToAll(request.getMessage());
                } else if (type.equals("DOWNLOAD FILE")) {
                    Response response = new Response("DOWNLOAD FILE");
                    String json = gson.toJson(response);
                    out.writeUTF(json);
                } else if (type.equals("CHOOSE FILE")) {
                    Response response = new Response("CHOOSE FILE", request.getNum());
                    String json = gson.toJson(response);
                    out.writeUTF(json);
                } else if (type.equals("BACK")) {
                    Server.groupClients.remove(client);
                } else {

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void history() throws IOException {
        if (!Server.messages.isEmpty()) {       //show all past message to a new client in chat group
            for (String msg : Server.messages) {
                Response response = new Response("MESSAGE", null, msg);
                Gson gson = new Gson();
                String json = gson.toJson(response);
                out.writeUTF(json);
            }
        }
    }
    private void sentToAll(String newMsg) throws IOException {
        for (Socket client : Server.groupClients){      //show a message in group chat for all clients in the chat
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            Response response = new Response("MESSAGE2", name, newMsg);
            Gson gson = new Gson();
            String json = gson.toJson(response);
            out.writeUTF(json);
        }
    }
}
