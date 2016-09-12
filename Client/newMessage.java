package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import utility.FontLibrary;

public class newMessage extends JFrame{
	private JPanel topPanel, middlePanel, bottomPanel;
	private JLabel recipientLabel;
	private JTextField recipientTF;
	private JTextArea messageJTA;
	private JButton sendButton;
	private JScrollPane messageScrollPane;
	private User mainUser;
	private MainClient mc;
	private MessagePage mp;
	final Color color = new Color(250,243,233);
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);

	
	
	public newMessage(MessagePage mp, User mainUser, MainClient mc)
	{
		super("thingadoo");
		setSize(600,400);
		this.mainUser = mainUser;
		this.mc = mc;
		this.mp = mp;
		createGUI(); 
		addEvents();
		setVisible(true);
		this.setBackground(color);
	}
	
	private void createGUI()
	{
		topPanel = new JPanel();
		middlePanel = new JPanel();
		bottomPanel = new JPanel();
		
		//do the top
		recipientLabel = new JLabel("To (Username):");
		recipientLabel.setFont(customFont);
		recipientTF = new JTextField(15);
		recipientTF.setFont(customFont);
		topPanel.add(recipientLabel);
		topPanel.add(recipientTF);
		topPanel.setBackground(new Color(250,243,233));
		add(topPanel, BorderLayout.NORTH);
		
		//do the middle (chat)
		messageJTA = new JTextArea(30,45);
		messageJTA.setFont(customFont);
		messageScrollPane = new JScrollPane(messageJTA);
		messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		middlePanel.add(messageScrollPane);
		middlePanel.setBackground(new Color(250,243,233));
		add(middlePanel, BorderLayout.CENTER);
		
		//do the bottom
		sendButton = new CuteButton(0,"Send message");
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(sendButton);
		bottomPanel.setBackground(new Color(250,243,233));
		add(bottomPanel, BorderLayout.SOUTH);
		
		
	}
	
	private void addEvents()
	{
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String chatToSend = messageJTA.getText();
				User userWhoSent = mainUser;
				User userToReceive = mc.getUserFromUsername(recipientTF.getText());
				int groupID = -1;
				Vector<User> recipients = new Vector<>();
				recipients.addElement(userToReceive);
				
				MessageObject messageToAdd = new MessageObject(chatToSend, recipients, userWhoSent, groupID);
				mc.sendMessage(messageToAdd);
				Conversation c = new Conversation(userToReceive, userWhoSent);
				c.mc = mc;
				c.addMessage(messageToAdd);
				mp.addConversation(c);
				c.setVisible(true);
               newMessage.this.dispose();				
				
				
			}

		});
	}
	
}
