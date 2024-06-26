package Server;

public class Request {

    private String reqType;
    private String name;
    private String message;
    private int num;

    public Request(String type) {
        this.reqType = type;
    }
    public Request(String type, String name){
        this.reqType = type;
        this.name = name;
    }
    public Request(String type, int num){
        this.reqType = type;
        this.num = num;
    }
    public Request(String type, String name, String message){
        this.reqType = type;
        this.name = name;
        this.message = message;
    }

    public String getReqType(){
        return reqType;
    }
    public String getName(){
        return name;
    }
    public String getMessage(){
        return message;
    }
    public int getNum(){
        return num;
    }
}
