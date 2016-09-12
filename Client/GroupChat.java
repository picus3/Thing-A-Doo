package Client;

import java.io.Serializable;
import java.util.Vector;


public class GroupChat implements Serializable {
    public Vector<String> conversations;

    public GroupChat() {
        conversations = new Vector<>();
    }

    public void addChat(String message, User u) {
        String username = u.getFullName();
        String chatToAdd = username + ":" + " " + message;
        conversations.add(chatToAdd);
    }

    public void addTestChat(String message) {
        conversations.add(message);
    }
}
