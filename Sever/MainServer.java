package Server;

import Client.Conversation;
import Client.CuteButton;
import Client.MessageObject;
import Client.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

//This CLass will create the server and let the user select the port
//It will then begin listening for requests
public class MainServer {

	private Vector<LoginServer> activeUsers = new Vector<LoginServer>();

	//fixed everything

	// GUI Variables for mainserver
	private JFrame popup;
	private JLabel portLabel, title;
	private JTextField portField;
	private CuteButton host;
	private JPanel titlePanel, portPanel, buttonPanel;
	final Color color = new Color(218,103,103);
	final Color textarea = new Color(250, 243, 233);
	
	//This Holds All Active Users
	//When Implementing Chat will use this
	//heres a change	a
	public MainServer(){
		makeTheGUIII();
		popup.setBackground(color);
		popup.setVisible(true);
		host.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.setVisible(false);
				ListenOn(Integer.parseInt(portField.getText()));
			}
		});
	}

	public static void main(String[] args) {
		new MainServer();
		
	}

	private void makeTheGUIII() {
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
		titlePanel = new JPanel();
		titlePanel.setBackground(color);
		title = new JLabel("Welcome to Thing-a-doo");
		title.setFont(font);
		titlePanel.add(title);

		portPanel = new JPanel();
		portPanel.setBackground(color);
		portLabel = new JLabel("Port:");
		portLabel.setFont(font);
		portField = new JTextField();
		portField.setFont(font);
		portField.setBackground(textarea);
		portField.setPreferredSize( new Dimension( 200, 30 ) );
		portPanel.add(portLabel);
		portPanel.add(portField);

		buttonPanel = new JPanel();
		buttonPanel.setBackground(color);
		host = new CuteButton(0,"Host");
		host.setBorderPainted(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(host);
		buttonPanel.add(Box.createHorizontalGlue());

		popup.setSize(350,250);
		popup.setLocation(300,100);
		popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		popup.setLayout(new GridLayout(1, 1));
		popup.setBackground(color);

		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
		overallPanel.add(titlePanel);
		overallPanel.add(portPanel);
		overallPanel.add(buttonPanel);
		popup.add(overallPanel);
		//popup.add(titlePanel);
		//popup.add(portPanel);
		//popup.add(buttonPanel);
	}

	public void ListenOn(int port) {
		  try {
		      ServerSocket ss = new ServerSocket(port);
		      while (true) {
		    	  Socket s = ss.accept();
				  LoginServer ls = new LoginServer(s, this);
				  ls.start();
		    	  activeUsers.add(ls);
		      }
	      } catch (Exception e) {
		     System.out.println("AHHHHHHH"); // <- word
		     e.printStackTrace();
	         }
	}

	//Send Message
	//This Method is for Chat
	//It will allow one user to send a message to a specific other users
	//Users will be identified by their  ID Number
	public boolean SendMessage(int ID, Object m) {
		for ( int i = 0; i < activeUsers.size(); i++) {
			System.out.println((activeUsers.get(i)).ID + "Server ID  " + ID + "Passed ID");
			if((activeUsers.get(i)).ID == ID) {
				activeUsers.get(i).sendMessage(m);
				return true;
			}
		}
		return false;
	}

	public void remove(LoginServer loginServer) {
		for ( int i = 0; i < activeUsers.size(); i++) {
			if (activeUsers.get(i).ID == loginServer.ID) {
				activeUsers.remove(i);
			}
		}
		
	}
}
