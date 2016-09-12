package Client;

import java.io.Serializable;
import java.util.Vector;

public class MessageObject implements Serializable {
	private static final long serialVersionUID = 1L;
	public Vector<User> people;
	public String message;
	public User sender;
	public int GroupID;

	public MessageObject(String message, Vector<User> recipents, User Sender, int GroupID) {
		this.message = message;
		this.people = recipents;
		this.sender = Sender;
		this.GroupID = GroupID;
	}

}
