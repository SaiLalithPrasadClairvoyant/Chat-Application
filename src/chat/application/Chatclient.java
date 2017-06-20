package chat.application;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.*;


public class Chatclient {
    private static Logger logger = LoggerFactory.getLogger(Chatclient.class);

    public static void main(String[] args) throws IOException {
        try {
            Socket s = new Socket("localhost", 5000);
            ReadThread readThread = new ReadThread(s);
            Thread t1 = new Thread(readThread);
            t1.start();
            WriteThread writeThread = new WriteThread(s);
            Thread t2 = new Thread(writeThread);
            t2.start();
        } catch (Exception e) {
            logger.info("Exception !", e);
        }
    }
}
