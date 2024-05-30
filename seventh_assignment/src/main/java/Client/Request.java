package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Request {

    private String reqType;
    private String name;
    private String message;

    public Request(String type) {
        this.reqType = type;
    }
    public Request(String type, String name){
        this.reqType = type;
        this.name = name;
    }
    public Request(String type, String name, String message){
        this.reqType = type;
        this.name = name;
        this.message = message;
    }

    public String getReqType(){
        return reqType;
    }

}
