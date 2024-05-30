package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Response {

    private String resType;

    public Response(String type){
        this.resType = type;
    }
    public void doWork(){
        if (resType.equals("GROUP CHAT")){
            System.out.println("Enter your name:");
        } else if(resType.equals("NAME")) {
            
        }
    }
}
