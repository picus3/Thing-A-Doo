package Client;

import javax.swing.*;
import javax.swing.border.Border;

import utility.FontLibrary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

//GUI
//This will Actually Display the Group Page With all of the Info
public class GroupPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel chatPanel, toDoPanel, memberPanel, sendMessagePanel, memberHolderPanel, activityPanel;
    private JLabel left, topright, bottomright;
    private GridBagConstraints gbc;
    private GroupChat groupChat;
    private Group group;
    private JScrollPane chatPane, userPane, toDoPane;
    private JTextArea chatTextArea;
    private CuteButton sendMessageButton;
    private JTextField sendTextArea;
    private Vector<Activity> todo;
    private Border border, loweredbevel, raisedbevel;
	final Color color = new Color(250,243,233);
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);
    private Vector<Activity> feed;


    public GroupPage(Group g) {
        super("thingadoo");
        setSize(900, 600);
        group = g;
        this.setResizable(false);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
    	border = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        initializeComponents();
        //createGUI();
        addEvents();
        populateChat();
        populateMembers();
        this.setBackground(new Color(250,243,233));
        
    }
	

  

    private void initializeComponents() {
        //to highlight different panels of the layout
        left = new JLabel("Chat");
        left.setFont(customFont);
        topright = new JLabel("To-Do");
        topright.setFont(customFont);
        bottomright = new JLabel("Members");

        //gui elements for the chat panel
        chatPanel = new JPanel();
        chatPanel.setBackground(color);
        chatPanel.setLayout(new BorderLayout());

        memberPanel = new JPanel();
        memberPanel.setBackground(color);
        memberPanel.setLayout(new BoxLayout(memberPanel, BoxLayout.Y_AXIS));
        memberHolderPanel = new JPanel();
        memberHolderPanel.setBackground(color);
        memberHolderPanel.setLayout(new BoxLayout(memberHolderPanel, BoxLayout.X_AXIS));
        sendMessagePanel = new JPanel();
        sendMessagePanel.setBackground(color);
        sendMessagePanel.setLayout(new BoxLayout(sendMessagePanel, BoxLayout.Y_AXIS));

        chatTextArea = new JTextArea();
        chatTextArea.setFont(customFont);
        chatTextArea.setPreferredSize(new Dimension(200, 30));
        chatTextArea.setWrapStyleWord(true);
        chatTextArea.setLineWrap(true);
        chatTextArea.setEditable(false);
        chatTextArea.setText("chatTextArea");
        
        chatPane = new JScrollPane(chatTextArea);
        //holds element of chat
        


        sendTextArea = new JTextField();
        sendTextArea.setFont(customFont);
        sendTextArea.setPreferredSize(new Dimension(100, 10));
        sendMessageButton = new CuteButton(0, "Send Message");
        sendMessagePanel.add(sendTextArea);
        sendMessagePanel.add(sendMessageButton);
        
        chatPanel.add(left, BorderLayout.NORTH);
        chatPanel.add(chatPane, BorderLayout.CENTER);
        chatPanel.add(sendMessagePanel, BorderLayout.SOUTH);

        //gui elements for members of a group
        userPane = new JScrollPane(memberHolderPanel);
        userPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        memberPanel.add(userPane);

        //add to do list
        //to do is overall panel
        toDoPanel = new JPanel();
        toDoPanel.setBackground(color);
        
        //activity holds the actual activities
        activityPanel = new JPanel();
        activityPanel.setBackground(color);
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        toDoPane = new JScrollPane(activityPanel);

        toDoPanel.setLayout(new BoxLayout(toDoPanel, BoxLayout.Y_AXIS));
        toDoPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        toDoPanel.add(topright);
        toDoPanel.add(toDoPane);

    }

    private void createGUI() {

        //CREATE BORDERS
        chatPanel.setBorder(border);
        toDoPanel.setBorder(border);
        memberPanel.add(bottomright);
        memberPanel.setBorder(border);

        //create the grid bag layout elements
        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0.5;
        gbc.weighty = 2;
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
       // add(chatPanel, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        //add(toDoPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        //add(memberPanel, gbc);
        setVisible(true);
    }


    private void addEvents() {
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToAdd = chatTextArea.getText();
            }
        });
    }

    private void populateChat() {
        groupChat = group.groupChat;

        //add elements to chat
        for (int i = 0; i < groupChat.conversations.size(); i++) {
            String currentTextInChat = chatTextArea.getText();
            String textToAddIn = groupChat.conversations.get(i);
            String newTextInChat = currentTextInChat + textToAddIn + "\n";
            chatTextArea.setText(newTextInChat);
        }
    }

    private void populateMembers() {
        //add in all the userpanel objects to member holder
        for (int i = 0; i < group.members.size(); i++) {
            MakePic tempPanel = new MakePic(group.members.get(i).profPic.getImage());
            tempPanel.setPreferredSize(new Dimension (100,100));
            tempPanel.setBorder(border);
            memberHolderPanel.add(tempPanel);
        }
        memberHolderPanel.revalidate();
        memberHolderPanel.repaint();
    }

    private void populateActivities() {
        //add in all the activities
        System.out.println("size of todo is: " + todo.size());
        for (int i = 0; i < todo.size(); i++) {
            ActivityPanel temp = new ActivityPanel(todo.get(i));
            activityPanel.add(temp);
        }

        activityPanel.revalidate();
        activityPanel.repaint();
    }

    class MakePic extends JButton{
    	Image image;
    	private static final long serialVersionUID = 1L;
    	
    	public MakePic(Image i){
    		image = i;
    		repaint();
    	}

    	public void paintComponent(Graphics g){
    		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    		super.paintComponent(g);
    	}
    }

    class ActivityPanel extends JPanel {
        public Image activityPic;

        public ActivityPanel(Activity a) {
            activityPic = a.photo.getImage();

            setLayout(new GridLayout(1, 2));
            //add(new JLabel(activityPic));
            JLabel temp = new JLabel(a.name);
            temp.setFont(customFont);
            add(temp);
        }
    }
}
