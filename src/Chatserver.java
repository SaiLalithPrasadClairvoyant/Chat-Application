import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatserver {
    public void makeserver() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while(true) {
                Socket s = serverSocket.accept();
                System.out.print("Connected");
                PrintWriter printWriter = new PrintWriter(s.getOutputStream());
                printWriter.print("Connected to the server at port"+s.getPort());
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String ar[]){
            new Chatserver().makeserver();
    }
}
