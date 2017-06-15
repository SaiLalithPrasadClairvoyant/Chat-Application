import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Sai Lalith Pathi on 08-Jun-17.
 */
public class ReadThread implements Runnable{
    private Socket s;
    ReadThread(Socket clientSocket){
        this.s = clientSocket;
    }
    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String msg;
                while((msg = bufferedReader.readLine())!= null){
                    System.out.println(msg);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
