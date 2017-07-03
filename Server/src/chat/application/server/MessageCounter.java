package chat.application.server;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;


class MessageCounter extends TimerTask {
    private static int totalMessages = 0;
    private static Map<Group, Integer> messages = new HashMap<>();

    static void addMessage(Group group) {
        messages.compute(group, (key, val) -> {
            if (val == null) {
                return 1;
            } else {
                return val + 1;
            }
        });
        totalMessages++;
    }

    static void addNewGroup(Group g) {
        messages.put(g, 0);
    }

    @Override
    public void run() {
        for (Map.Entry<Group, Integer> groupIntegerEntry : messages.entrySet()) {
            System.out.println("Total number of Messages in '" + groupIntegerEntry.getKey().getGroupName() + "' are :" + groupIntegerEntry.getValue());
        }
        if (!messages.isEmpty()) {
            System.out.println("Total Messages :" + totalMessages);
        }
    }
}
