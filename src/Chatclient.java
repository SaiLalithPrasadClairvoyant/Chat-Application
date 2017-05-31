import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 * Created by Sai Lalith on 5/31/2017.
 */
public class Chatclient {
    public void connect(){
        try{
            Socket s = new Socket("localhost",5000);
            InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String msg = bufferedReader.readLine();
            System.out.println(msg);
            s.close();
            bufferedReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        new Chatclient().connect();
    }
}
