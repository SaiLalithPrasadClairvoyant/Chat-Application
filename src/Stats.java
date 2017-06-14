import java.util.TimerTask;

/**
 * Created by Sai Lalith Pathi on 13-Jun-17.
 */
public class Stats extends TimerTask{
    private static int totalMessages=0;
    static void add(){
        totalMessages++;
    }
    @Override
    public void run() {
        System.out.print("Total number of Messages:"+totalMessages);
    }
}
