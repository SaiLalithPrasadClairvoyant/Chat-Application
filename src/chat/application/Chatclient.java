package chat.application;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.*;


public class Chatclient {
    private static Logger logger = LoggerFactory.getLogger(Chatclient.class);

    public static void main(String[] args) throws IOException {
        try {
            Socket s = new Socket("localhost", 5000);
            ReaderThread readerThread = new ReaderThread(s);
            Thread t1 = new Thread(readerThread);
            t1.start();
            WriterThread writerThread = new WriterThread(s);
            Thread t2 = new Thread(writerThread);
            t2.start();
        } catch (Exception e) {
            logger.info("Exception !", e);
        }
    }
}
