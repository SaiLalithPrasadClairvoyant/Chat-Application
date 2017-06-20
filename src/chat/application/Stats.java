package chat.application;

import java.util.TimerTask;


class Stats extends TimerTask {
    private static int totalMessages = 0;

    static void add() {
        totalMessages++;
    }

    @Override
    public void run() {
        System.out.println("Total number of Messages:" + totalMessages);
    }
}
