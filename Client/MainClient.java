package Client;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import javafx.util.Pair;
import utility.FontLibrary;
import utility.Search;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//This will allow the user to connect with the Server, it will then open a login Window
public class MainClient extends Thread {
	private static MainClient client;
	public  MainPage mainPage;
	LogIn login;
	int port;
	private String host;
	private Socket s;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	// GUI variables
	private JFrame popup;
	private JLabel ipLabel, portLabel, title;
	private JTextField ipField, portField;
	private JPanel titlePanel, ipPanel, portPanel, confirmPanel;
	private CuteButton confirmButton;

   private Lock lock = new ReentrantLock();
	public User user;
	final Color color = new Color(218,103,103);
	final Color textarea = new Color(250, 243, 233);
	// Have a Lock everytime on reads from the The Object Input Stream
	
	public MainClient() {
		//Create GUI that gets Localhost
		//Also gets a port
		insVar();
		makeTheGUI();
		popup.setVisible(true);
		
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				host = ipField.getText();
				port = Integer.parseInt(portField.getText());
				MainClient.this.start();
				login = new LogIn(MainClient.this);
				login.setVisible(true);
				popup.setVisible(false);
			}
		});
	}

	public static void main(String[] args) {

		/*try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		*/
		client = new MainClient();
	}

	private void makeTheGUI() {
		popup.setSize(350,250);
		popup.setLocation(300,100);
		popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		popup.setLayout(new GridLayout(4, 1));

		popup.add(titlePanel);
		popup.add(ipPanel);
		popup.add(portPanel);
		popup.add(confirmPanel);
	}

	private void insVar() {
		
		// font
		InputStream is;
		Font font = null;
		try {
			is = new FileInputStream("Pacifico.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 25f);

		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    ge.registerFont(font);
	    
	    
		popup = new JFrame("thingadoo");
		title = new JLabel("Welcome to Thing-a-doo!");
		title.setFont(font);
		ipLabel = new JLabel("IP:");
		ipLabel.setFont(font);
		portLabel = new JLabel("Port:");
		portLabel.setFont(font);
		ipField = new JTextField();
		ipField.setFont(font);
		ipField.setBackground(textarea);
		portField = new JTextField();
		portField.setFont(font);
		portField.setBackground(textarea);
		ipPanel = new JPanel();
		ipPanel.setBackground(color);
		portPanel = new JPanel();
		portPanel.setBackground(color);
		confirmPanel = new JPanel();
		confirmPanel.setBackground(color);
		titlePanel = new JPanel();
		titlePanel.setBackground(color);
		confirmButton = new CuteButton(0, "Confirm");
		confirmButton.setBorderPainted(false);
		setUpPanels();
	}

	private void setUpPanels() {
		ipPanel.add(ipLabel);
		ipField.setPreferredSize( new Dimension( 200, 30 ) );
		ipPanel.add(ipField);
		portPanel.add(portLabel);
		portField.setPreferredSize( new Dimension( 190, 30 ) );
		portPanel.add(portField);
		titlePanel.add(title);
		confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.X_AXIS));
		confirmPanel.add(Box.createHorizontalGlue());
		confirmPanel.add(confirmButton);
		confirmPanel.add(Box.createHorizontalGlue());
	}
	
	public void run() {
		try {
			System.out.println(host+ " " + port);
			s = new Socket(host, port);
			s.setSoTimeout(600);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		}  catch (IOException e) {
			e.printStackTrace();
		}
		Object obj = null;
			while (true) {
				//the lock is so Notifications and requests for information don't get screw up
				try {
				lock.lock();
				  obj = ois.readObject();
				if (obj instanceof MessageObject) {
					MessageObject m = (MessageObject) obj;
					//Group Message
					if (m.people.size() > 1) {
						System.out.println(login.mainPage);
						for (int i = 0; i < login.mainPage.opengroups.size(); i++ ) {
							if (login.mainPage.opengroups.get(i).group.ID == m.GroupID) {
								login.mainPage.opengroups.get(i).addChat(m);
							}
						}
						
					} else {
						boolean hasconversation = false;
						for (int j = 0; j < user.messages.size(); j++) {
							if (user.messages.get(j).otherID == m.sender.userID) {
								user.messages.get(j).addMessage(m);
								hasconversation = true;
							}
						}
						if (!hasconversation) {
	    	    	    	System.out.println("Creating new Conversation");;
	    	    	    	Conversation c = new Conversation(m.sender, user);
	    	    	    	System.out.println("Created Conversation with owner:" + user.firstName +" " + user.lastName + " Talking with: " + m.sender.firstName + " " + m.sender.lastName);
	    	    	    	c.mc = this;
	    	    	    	//c.font = FontLibrary.getTrueTypeFont(28);
	    	    	    	//c.customFont = FontLibrary.getTrueTypeFont(14);
	    	    	    	c.addMessage(m);
	    	    	    	user.messages.addElement(c);
	    	    	    	if(login.mainPage.mp != null)  {
	    	    	    		if (login.mainPage.mp.isVisible()){
	    	    	    			login.mainPage.mp.addConversation(c);
	    	    	    		}
	    	    	    		else {
		    	    	    		login.mainPage.newMessageNotification();
	    	    	    		}
	    	    	    	}
	    	    	    	else {
	    	    	    		login.mainPage.newMessageNotification();
	    	    	    	}
	    	    	    } else {
	    	    	    	if(login.mainPage.mp != null)  {
	    	    	    		if (!login.mainPage.mp.isVisible()) {
		    	    	    		login.mainPage.newMessageNotification();
	    	    	    		}
	    	    	    	}
	    	    	    }
						updateUser(user);
					}
				}
				else if (obj instanceof Group) {
					user.groups.add((Group) obj);
					login.mainPage.addGroup((Group) obj);
				}
				}
				catch (SocketTimeoutException e) {
						if (user != null) user.lastlogin = (int) System.currentTimeMillis();
			    }
			    catch (IOException e) {
				    e.printStackTrace();
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
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						if (ois != null) {
							try {
								ois.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
			    } catch (ClassNotFoundException nfe) {
				    nfe.printStackTrace();
			    }
				finally {
					lock.unlock();
					try {
						sleep(1);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		
		

	}
	
	public void send(Object obj) {
		if (oos == null){
			System.out.println("oos is null");
		}
		if (obj == null){
			System.out.println("obj is null");
		}
		if (obj == null) {
			System.out.println("TRYING TO SEND A NULL OBJECT!");
			return;
		}
		try {
			oos.reset();
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			System.out.println("IOE in MainClient Writing: " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	public User getUser(int ID) {
		User u = new User(ID);
		lock.lock();
		send(u);
		Object obj = null;
		try {
			obj = ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		if (obj instanceof User) {
			return (User) obj;
		} else {
			return null;
		}

	}

	public User getUserFromUsername(String name) {
		User u = new User(name, -1);
		lock.lock();
		send(u);
		Object obj = null;
		try {
			obj = ois.readObject();
			lock.unlock();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (obj instanceof User) {
			return (User)obj;
		}
		else {
			//User Did Not Exsist
			return null;
		}


	}
	
	//Will return null if Activty does not exsist
	public Activity getActivity(int id) {
		
		Activity a = new Activity(id);
		System.out.println("Activty ID: " + a.ID);
		lock.lock();
		send(a);
		Object obj = null;
		try {
			obj = ois.readObject();
			System.out.println("Got Activtiy:" + ((Activity)obj).ID);
			lock.unlock();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		if (obj instanceof Activity) {
			return (Activity)obj;
		}
		else {
			return null;
		}

	}
	
	//Will return null if The Group does not exist
	public Group getGroup(int ID) {
		Group g = new Group(ID);
		
		lock.lock();
		send(g);
		Object obj = null;
		try {
			obj = ois.readObject();
			System.out.println(obj.toString());
			lock.unlock();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		if (obj instanceof Group) {
			return (Group)obj;
		}
		else {
			return null;
		}


	}

	public void sendMessage(MessageObject m) {
		send(m);
		System.out.println("Sending Message! to: " + m.people.get(0).firstName );
    }


	public void updateUser(User us) {
		lock.lock();
		send(us);
		lock.unlock();
	}

	public User createUser(User u) {
		lock.lock();
		send(new String("NewUser"));
		send(u);
		try {
			Object obj = ois.readObject();
			lock.unlock();
			user = (User)obj;
			return (User)obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Group createGroup(Group g) {
		lock.lock();
		send(g);
		try {
			Object obj = ois.readObject();
			lock.unlock();
			return (Group)obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public Activity createActivity(Activity a) {
		lock.lock();
		send(a);
		try {
			Object obj = ois.readObject();
			lock.unlock();
			return (Activity)obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
//////////////////////////////////////////////
////////MAIN METHOD HERE//////////////////////
//////////////////////////////////////////////

	//If this Returns null then Something Went Wrong
	public User Login(String username, String Password) {
		lock.lock();
		System.out.println("Got Login Lock: " + System.currentTimeMillis());
		send(username);
		send(Password);
		Object obj = null;
		try {
			obj = ois.readObject();
			lock.unlock();
			System.out.println("Released " + System.currentTimeMillis());

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		if (obj instanceof User) {
			user = (User) obj;
			return (User) obj;
		} else {
			System.out.println("login in MainClient is returning null");
			return null;
		}

	}

	public  Vector<Integer> getStringSearch(Vector<String> keywords) {
		lock.lock();
		send(keywords);
		Object obj = null;
		try {
			obj = ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		if (obj instanceof Vector) {
			return ((Vector<Integer>) obj);
		} else {
			//Must Have Not Worked
			return null;
		}
	}

	public  Vector<Integer> getLocationSearch(int location) {
		lock.lock();
		send(new Integer(location));
		Object obj = null;
		try {
			obj = ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		if (obj instanceof Vector) {
			Vector<Integer> it =  Search.searhcLocation((Vector<Pair<Integer, Integer>>) obj, location);
			return ((Vector<Integer>) it);
		} else {
			//Must Have Not Worked
			return null;
		}
	}

	public Vector<Integer> getCategorySearch(Vector<Integer> categories) {
		lock.lock();
		send(categories);
		Object obj = null;
		try {
			obj = ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		if (obj instanceof Vector) {
			return ((Vector<Integer>) obj);
		} else {
			//Must Have Not Worked
			return null;
		}
	}

	public void addPastActivity(Activity activityToAdd) {
		// TODO Auto-generated method stub
		
	}

	public void deleteActivity(Activity a) {
		a.creator = -1;
		lock.lock();
		send(a);
		lock.unlock();
		
	}

}
