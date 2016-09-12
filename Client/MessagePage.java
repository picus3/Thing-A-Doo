package Client;
//GUI//
//This will be a mailbox Like thing with a list of all current Convesations
//It will also Keep a List of recently updated conversations with a little Notification

import javax.swing.*;

import utility.FontLibrary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MessagePage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel topPanel, centerPanel, overallMessagesPanel;
    private Vector<Conversation> conversations;
    private JLabel titleLabel;
    private JButton createNewMessageButton;
    private JScrollPane jsp;
    private Box topBox;
    private User mainUser;
    private MainClient mc;
	final Color color = new Color(250,243,233);
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);

	
    public MessagePage(User mainUser, MainClient mc, Vector<Conversation> v) {
        super("thingadoo");
        setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.mainUser = mainUser;
		this.mc = mc;
        this.conversations = v;
        initializeComponents();
        createGUI();
        addEvents();
        setVisible(true);
        this.setBackground(color);
    }



    private void initializeComponents() {
        topPanel = new JPanel();
        topPanel.setBackground(color);
        overallMessagesPanel = new JPanel();
        overallMessagesPanel.setBackground(color);
        titleLabel = new JLabel("Messages");
        titleLabel.setFont(font);
        createNewMessageButton = new CuteButton(0, "New Message");
        jsp = new JScrollPane(overallMessagesPanel);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        topBox = Box.createHorizontalBox();
        topBox.setBackground(color);
    }

    private void createGUI() {
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(titleLabel);

        topBox.add(Box.createGlue());
        topBox.add(Box.createGlue());
        topBox.add(createNewMessageButton);
        topPanel.add(topBox);

        overallMessagesPanel.setLayout(new BoxLayout(overallMessagesPanel, BoxLayout.Y_AXIS));
        System.out.println(mainUser.firstName + "Has Converstations: " + mainUser.messages.size());
        if (!(conversations == null))
        {
	        for (int i =0; i < conversations.size(); i++) {
	        	conversations.get(i).mc = mc;
	        //	conversations.get(i).font = FontLibrary.getTrueTypeFont(28);
	        //	conversations.get(i).customFont = FontLibrary.getTrueTypeFont(14);
	            if(conversations.get(i).sendButton.getActionListeners().length == 0) {
	        	conversations.get(i).addEvents();
	        }
	            boolean b = false;
	            if (conversations.get(i).lastmessage > conversations.get(i).lastopened) {
	            	b = true;
	            }
	            IndividualMessage temp = new IndividualMessage(conversations.get(i), b, this);
	     
	            overallMessagesPanel.add(temp);
	
	        }
        }

        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(jsp, BorderLayout.CENTER);

    }

    private void addEvents() {

    	createNewMessageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newMessage nm = new newMessage(MessagePage.this, mainUser, mc);
				nm.setVisible(true);
			}

		});
    	
    }
    
 

	public void addConversation(Conversation c) {
		System.out.println("Adding Conversation for Active User");
		mainUser.messages.addElement(c);
		conversations = mainUser.messages;
		mc.updateUser(mainUser);
		 IndividualMessage temp = new IndividualMessage(c, true, this);
         overallMessagesPanel.add(temp);
         repaint();
         revalidate();
		
	}





}
