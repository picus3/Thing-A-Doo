package Client;

import java.io.Serializable;
import java.util.Vector;

import javafx.util.Pair;

//This will contain all of the information of the group
public class Group implements Serializable {
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int ID;
     public Vector<User> members;
     public String name;
     public String chat;
     
     //These two vectors will match up One will contain the activities, the other will contain
     //All of the dates, in string form. We can pull these straight out the text field
     //If its blank its blank
     public Vector< Pair<Activity,String> > todo;
     public Vector<String>dates;
     public GroupChat groupChat;
	public boolean notificaton;
     
     public Group(int ID, Vector<User> members, String n) {
		this.ID = ID;
		this.members = members;
		notificaton = false;
		chat = new String("");
		this.name = n;
         todo = new Vector<Pair<Activity,String>>();
         dates = new Vector<>();
         groupChat = new GroupChat();
     }
     
    public Group(int ID)  {
        this.ID = ID;
        
    }

    public void addTestChat(String s) {
        groupChat.addTestChat(s);
    }



	
}
