import java.util.TimerTask;

/**
 * Created by Sai Lalith Pathi on 13-Jun-17.
 */
class Stats extends TimerTask{
    private static int totalMessages=0;
    static void add(){
        totalMessages++;
    }
    @Override
    public void run() {
        System.out.println("Total number of Messages:"+totalMessages);
    }
}
