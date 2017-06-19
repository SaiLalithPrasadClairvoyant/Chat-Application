import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.slf4j.*;
/**
 * Created by Sai Lalith Pathi on 08-Jun-17.
 */
public class WriteThread implements Runnable{
    private static  Logger logger = LoggerFactory.getLogger(WriteThread.class);
    private Socket s;
    private String name;
    WriteThread(User u){
        this.s = u.getSocket();
        this.name = u.getUserName();
    }
    @Override
    public void run() {
        try {
            logger.info(s.getLocalPort()+"WriteThread");
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String msg;
            while(true){
                msg = bufferedReader.readLine();
                pw.println(name+": "+msg);
                pw.flush();
                if(msg.equalsIgnoreCase("bye")) {
                    System.exit(0);
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Exception",e);
        }
    }
}
