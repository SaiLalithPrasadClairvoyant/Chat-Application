import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sai Lalith Pathi on 08-Jun-17.
 */
public class WriteThread implements Runnable{
    Socket s;
    String name;
    public WriteThread(Socket clientSocket,String name){
        this.s = clientSocket;
        this.name = name;
    }
    @Override
    public void run() {
        try {
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String msg;
            while(true){
                msg = bufferedReader.readLine();
                pw.println(name+": "+msg);
                pw.flush();
                if(msg.toLowerCase().equals("bye")) {
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
