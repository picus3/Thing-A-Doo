package Client;
///GUI///
//This will be a GUI with a conversation
//It will suport sending and reciving messages

import javax.swing.*;

import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;

import utility.FontLibrary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Vector;

public class Conversation extends JFrame implements Serializable {

    private static final long serialVersionUID = 1L;
    public int otherID;
    public User recipientUser;
    //should be fetched from the server
    private Vector<String> messages;
    private JPanel topPanel, midPanel, bottomPanel;
    private JScrollPane messagesJSP, composeMessageJSP;
    private JLabel senderNameLabel;
    private String senderName;
    private JTextArea messageDisplay;
    private  JTextArea newMessage;
    public  JButton sendButton;
    public User mainUser;
    public long lastmessage;
    public transient MainClient mc;
	public long lastopened;
	final Color color = new Color(250,243,233);
	public  Font font = FontLibrary.getTrueTypeFont(28);
	public  Font customFont = FontLibrary.getTrueTypeFont(14);


    public Conversation(User u, User mainUser) {
        super("thingadoo");
        setSize(600, 400);
        this.otherID = u.userID;
        this.mainUser = mainUser;
        this.senderName = u.getFirstName();
        initializeVariables();
        createGUI();
        addEvents();
        recipientUser = u;
        System.out.println("CREATING A CONVERSATION OWNER:" + mainUser + " TALKING WITH: " + u);
    }

    private void initializeVariables() {
        messages = new Vector<>();


        topPanel = new JPanel();
        midPanel = new JPanel();
        bottomPanel = new JPanel();


        senderNameLabel = new JLabel(senderName);
       // senderNameLabel.setFont(font);
        
        
        messageDisplay = new JTextArea(20, 50);
       // messageDisplay.setFont(customFont);
        messageDisplay.setWrapStyleWord(true);
        messageDisplay.setLineWrap(true);
        messageDisplay.setEditable(false);
        midPanel.add(messageDisplay);
        newMessage = new JTextArea(5, 25);
       // newMessage.setFont(customFont);
        newMessage.setWrapStyleWord(true);
        newMessage.setLineWrap(true);
        sendButton = new JButton("Send");

        messagesJSP = new JScrollPane(messageDisplay);
        messagesJSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        composeMessageJSP = new JScrollPane(newMessage);
        composeMessageJSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


    }

    private void createGUI() {
        //name of sender at top
        topPanel.add(senderNameLabel);

        //actual messages being exchanged
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
        for (String message : messages) {
            //should be a JTA because they can send multiple lines
            JTextArea tempMessage = new JTextArea(message);
          //  tempMessage.setFont(customFont);
            tempMessage.setEditable(false);
            tempMessage.setWrapStyleWord(true);
            tempMessage.setLineWrap(true);
            tempMessage.setFont(new Font("Arial", Font.PLAIN, 15));
            tempMessage.setBorder(null);


            midPanel.add(tempMessage);
        }

        bottomPanel.add(composeMessageJSP);
        bottomPanel.add(sendButton);

        topPanel.setBackground(new Color(250,243,233));
        messagesJSP.getViewport().setBackground(new Color(250,243,233));
        bottomPanel.setBackground(new Color(250,243,233));
        add(topPanel, BorderLayout.NORTH);
        add(messagesJSP, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
     //   this.setBackground(color);
    }

    public void addEvents() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = newMessage.getText();
                messages.add(text);
                addMessage(new MessageObject(text, null, mainUser, -1));
                newMessage.setText("");
                Vector<User> m = new Vector<User>();
                m.add(recipientUser);
                mc.sendMessage(new MessageObject(text, m, mainUser, -1));
                //TODO: add this string that they typed into the messages vector, and send to database
            }
        });
    }
    
    //receiving message from server to add to chat box
    public void addMessage(MessageObject m) {
        // TODO Auto-generated method stub
    	String sender = m.sender.getFirstName() + " " + m.sender.getLastName();
    	String message = m.message + "\n";
    	String messageToAdd = sender + ": " + message;
    	messageDisplay.append(messageToAdd);
    	lastmessage =  System.currentTimeMillis();
    	//not sure what to add this message to!
 
    	
    	
    }
    
    public User getRecipient()
    {
    	return recipientUser;
    }


}
