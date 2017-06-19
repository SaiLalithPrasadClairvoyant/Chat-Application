import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.slf4j.*;
/**
 * Created by Sai Lalith Pathi on 08-Jun-17.
 */
public class ReadThread implements Runnable{
    private static  Logger logger = LoggerFactory.getLogger(ReadThread.class);
    private Socket s;
    ReadThread(User u){
        this.s = u.getSocket();
    }
    @Override
    public void run() {
        try {
            logger.info(s.getLocalPort()+"ReadThread");
            InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String msg;
                while((msg = bufferedReader.readLine())!= null){
                    System.out.println(msg);
                }
        } catch (IOException e) {
            logger.error("Exception",e);
        }
    }
}
