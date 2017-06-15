import java.net.Socket;

/**
 * Created by Sai Lalith Pathi on 07-Jun-17.
 */
public class Clientdetails {
    private static String clientName;
    private static Socket socket;
    public static Socket getSocket(){
        return socket;
    }
    public void setSocket(Socket s){
        this.socket=socket;
    }
    public static String getClientName(){
        return clientName;
    }
    public void setClientName(String name){
        this.clientName=name;
    }
}
