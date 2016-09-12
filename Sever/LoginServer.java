package Server;
//THIS Will be the first thread opened when a user logins and will handle of the 
//The Logining In

import Client.Activity;
import Client.Conversation;
import Client.Group;
import Client.MessageObject;
import Client.User;
import javafx.util.Pair;
import utility.Search;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.util.Vector;

//It will then create an instance of a Main Page which will then handle the rest 
//of the program from the server side
public class LoginServer extends Thread {
	public  int ID = -1;
	public boolean loggedin = false; //Will be false until successful setting  ID
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private User u;
	private MainServer parent;
	
    
	public LoginServer(Socket s, MainServer m) {
		this.s = s;
		parent = m;
		try {
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			System.out.println("IOE in LoginServer constructor: " + e.getMessage());
			e.printStackTrace();
		}
	}

	//MainPage
	@Override
	public void run() {
		
			Object obj;
			try {
				while (true) {
				     obj = ois.readObject();
				     if (obj instanceof String) {
				    	 System.out.println(System.currentTimeMillis());
				    	 String username = (String)obj;
					         //Will Handle New User Creation
					         if (username.equals(new String("NewUser"))) {
						          u = createUser();
	    					      if (u == null) {  
		    					      //If it fails 
			    				      sendMessage(new String("Failed Creation"));
				    		      }  
					    	      else {
						    	    sendMessage(u);
							       loggedin = true;
					    	      }
					          }
					          else {
						         u = login(username);
						         if (u == null) {
							          sendMessage(new String("Failed Login"));
						         }
						        else {
						        	System.out.println("Login Succed: " + System.currentTimeMillis());
							        sendMessage(u);
							        loggedin = true;
						        }
					        }
					     
				   }
				   else if (obj instanceof User) {

				    	if (((User)obj).username == null) {
					    	sendMessage(getUser(((User)obj).userID));
				    	}					   
				    	else if (((User)obj).userID == -1) {
				    		sendMessage(getUserFromName(((User)obj).username));
				    	}
				    	else {
				    		updateUser((User)obj);
				    	}
		
				    }
				     else if (obj instanceof Group) {
				    	 
				    	 if (((Group)obj).name == null) {
				    		 //Gets Group
				    		 sendMessage(getGroup(((Group)obj).ID));
				    	 }
				 
				    	 else {
				    		 //Creates Group
				    	     sendMessage(createGroup((Group)obj));
				    	 }
				     }
				     else if (obj instanceof Activity) {
				    	 if (((Activity)obj).name == null) {
				    		 System.out.print("Group ID: " + ((Activity)obj).ID);
				    		 sendMessage(getActivity(((Activity)obj).ID));
				    		 
				    	 }
				    	 else if (((Activity)obj).creator == -1) {
				    		 deleteActivity(obj);
				    	 }
				    	 else {
				    		sendMessage(createActivity((Activity)obj));
					    	 
				    	 }
				     }
				      
				else if (obj instanceof MessageObject) {
				    	MessageObject m = (MessageObject)obj;
		
				    	for (int i = 0; i < m.people.size(); i++) {
				    	    if (!parent.SendMessage(m.people.get(i).userID, m)) {
				    	    	User recipient = getUser(m.people.get(i).userID);
				    	    	//GROUP MESSAGE
				    	    	//UPDATE GROUP IN HERE PLUS PUSH THE MESSAGE!
				    	    	if (m.people.size() > 1) {
				    	    		//Do Nothing
				    	    	}
				    	    	//INDIVIUAL MESSAGE
				    	    	else {
				    	    		System.out.println("Updating Conversation of User");
				    	    		boolean hasconversation = false;
				    	    	    recipient.newMessages = new Boolean(true);
				    	    	    for (int j = 0; j < recipient.messages.size(); j++) {
				    	    	    	if (recipient.messages.get(j).otherID == m.sender.userID) {
				    	    	    		recipient.messages.get(j).addMessage(m);
				    	    	    		hasconversation = true;
				    	    	    	}
				    	    	    	
				    	    	    }
				    	    	    if (!hasconversation) {
				    	    	    	System.out.println("Creating new Conversation");;
				    	    	    	Conversation c = new Conversation(m.sender, recipient);
				    	    	    	c.addMessage(m);
				    	    	    	recipient.messages.addElement(c);
				    	    	    	System.out.println(recipient.firstName + " Has Conversations: " + recipient.messages.size());
				    	    	    	
				    	    	    }
				    	    	
				    	    	updateUser(recipient);
				    	    	User temp = getUser(recipient.userID);
				    	    	System.out.println("User Updated");
		    	    	    	System.out.println(temp.firstName + " Has Conversations: " + temp.messages.size());
				    	    	}
				    	    	if (m.people.size() > 1) {
				    	    		String username = m.sender.firstName + " " + m.sender.lastName;
				    		        String chatToAdd = username + ": " + m.message + "\n";
				    		        Group g = getGroup(m.GroupID);
				    		        g.chat = g.chat + chatToAdd;
				    		        g.notificaton = true;
				    		        createGroup(g);
				    	    	}
				    	    }
				    	}
				     }
				else if (obj instanceof Vector<?>) {
					Object thing = ((Vector<?>)obj).get(0);
					if (thing instanceof Integer) {
						//Doing Locaiton Search
						sendMessage(categorySearch(obj));
					}
					if (thing instanceof String) {
						//Doing Keyword Search
						sendMessage(keywordSearch(obj));
					}
				}
				else if (obj instanceof Integer) {
					//Doing Categories search
					sendMessage(locationSearch(obj));
				}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				parent.remove(this);
				e.printStackTrace();
			}
			finally {
				
				if (s != null) {
					try {
						s.close();
					} catch (IOException ioe) {
						System.out.println("IOE closing socket: " + ioe.getMessage());
					}
				}
				if (oos != null) {
					try {
						oos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (ois != null) {
					try {
						ois.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	
			
	}
	
	
	
	private void deleteActivity(Object obj) {
		DataMain m = new DataMain();
		m.Connect();
		m.delete(obj);
		m.Stop();
	}

	//This will send all necessary Info to the clients.
	public void sendMessage(Object message) {
		try {
			if (message == null) { return;}
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private User login(String username) {

		User u = (User) getUserFromName(username);
		DataMain m = new DataMain();
		m.Connect();
		String password = m.getPass(u.userID);
		Object pass = null;
		m.Stop();
		try {
			 pass = ois.readObject();
				try {
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.reset();
					md.update(((String) pass).getBytes());
					byte[] digest = md.digest();
					BigInteger bigInt = new BigInteger(1,digest);
					pass = bigInt.toString(16);
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (password.equals((String)pass)) {
			ID = u.userID;
			System.out.println("Login of: " + u.firstName + " " + u.lastName + " Suceeded");
			return u;
		}
		else {
			System.out.println("Login of: " + username + " Failed");
			return null;
		}
		
	}
	
	private User getUserFromName(String username) {
	    DataMain m = new DataMain();
	    m.Connect();
	    User u = m.getUserFromName(username);
	    System.out.println(u.toString());
	    m.Stop();
	    return u;
	}
	
	private User getUser(int userID) {
		DataMain m = new DataMain();
	    m.Connect();
	    User u = m.getUser(userID);
	    m.Stop();
	    return u;
	}
	
	private Group getGroup(int ID) {
		DataMain m = new DataMain();
	    m.Connect();
	    Group u = m.getGroup(ID);
	    m.Stop();
	    System.out.println(u.toString());
	    return u;
	    
		
		}
	
	private Activity getActivity(int ID) {
		DataMain m = new DataMain();
	    m.Connect();
	    Activity u = m.getActivity(ID);
	    System.out.println("Got Activity: " + u.name);
	    m.Stop();
	    return u;
	}
	
	//Will be Called if User Pushes new User Button
	//Will return true if it works
    public User createUser() {
    	DataMain m = new DataMain();
	    m.Connect();
    	try {
    		Object user = ois.readObject();
            ID = m.addUser((User)user);
            m.Stop();
            ((User)user).userID = ID;
            
	    	return (User)user;
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    private void updateUser(User obj) {
    	DataMain m = new DataMain();
	    m.Connect();
	    ((User)obj).lastlogin = (int) System.currentTimeMillis();
	    m.updateUser(obj);
	    m.Stop();
	}
    
    private Group createGroup(Group obj) {
    	DataMain m = new DataMain();
	    m.Connect();
	    Group g = m.getGroup(((Group)obj).ID);
	    
	    if (g == null) {
	    	int ID = m.addGroup(obj);
	    	m.Stop();
	    	obj.ID = ID;
	    	for (int i = 0; i < ((Group)obj).members.size(); i++) {
	    		if (((Group)obj).members.get(i).userID != u.userID) {
		    	   parent.SendMessage(((Group)obj).members.get(i).userID, obj);
	    		}
	    	}
	    	return obj;
	    }
	    else {
	    	m.updateGroup(obj);
	    	m.Stop();
	    	return obj;
	    }
	    
		
	}
    
    private Activity createActivity(Activity obj) {
    	DataMain m = new DataMain();
	    m.Connect();
	    Activity g = m.getActivity(((Activity)obj).ID);
	    if (g == null) {
	    	int ID = m.addActivity(obj);
	    	m.Stop();
	    	obj.ID = ID;
	    	return obj;
	    }
	    else {
	    	m.updateActivity(obj);
	    	m.Stop();
	    	return obj;
	    }
	}
    
    private Object locationSearch(Object obj) {
    	DataMain m = new DataMain();
	    m.Connect();
	    Vector<Pair<Integer, Integer>> results = m.locationSearch((Integer)obj);
	    m.Stop();
	    return results;
	}
    
    private Object keywordSearch(Object obj) {
    	DataMain m = new DataMain();
	    m.Connect();
	    Vector<Integer> results = m.keywordSerch((Vector)obj);
	    m.Stop();
	    return results;
    	}

    private Object categorySearch(Object obj) {
    	DataMain m = new DataMain();
	    m.Connect();
	    Vector<Integer> results = m.categorySearch((Vector)obj);
	    m.Stop();
	    return results;
    	}


}
