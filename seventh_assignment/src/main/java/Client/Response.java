package Client;

import Server.Server;
import Server.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Response {

    private String resType;
    private String message;
    private String name;

    public Response(String type){
        this.resType = type;
    }
    public Response(String type, String name){
        this.resType = type;
        this.name = name;
    }
    public Response(String type, String name, String message){
        this.resType = type;
        this.name = name;
        this.message = message;
    }
    public void doWork() throws IOException {
        if (resType.equals("GROUP CHAT")){
            System.out.println("Enter your name:");
        } else if(resType.equals("NAME")) {

        } else if(resType.equals("MESSAGE")){
            sentToAll(message);
        } else if(resType.equals("")){

        } else if(resType.equals("")){

        } else if(resType.equals("")){

        } else if(resType.equals("")){

        }
    }
    private void sentToAll(String newMsg) throws IOException {
        for (Socket client : Server.groupClients){      //show a message in group chat for all clients in the chat
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(newMsg);
        }
    }

}
