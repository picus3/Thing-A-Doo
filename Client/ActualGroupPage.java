package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sun.security.ntlm.Client;

import javafx.util.Pair;
import utility.Photo;

public class ActualGroupPage extends JFrame{
	private static final long serialVersionUID = 1L;
	public Group group;
	JPanel chatPanel, rightPanel, todoPanel, membersPanel, picPanel;
    private Border border, loweredbevel, raisedbevel;
    public MainClient c;
	private JPanel memberPanel;
	private JPanel memberHolderPanel;
	private Container sendMessagePanel;
	private JTextArea chatTextArea;
	private JScrollPane chatPane;
	private JTextField sendTextArea;
	private JButton sendMessageButton;
	private Component left;
	final Color color = new Color(250,243,233);

    
	public ActualGroupPage(int groupID, MainClient c){
        super("thingadoo");
        setSize(900, 600);
        System.out.println(groupID);
        group = c.getGroup(groupID);
        System.out.println(group.toString());
        System.out.println(group.members.size());
        System.out.println(group.members.toString());
        this.c = c;
        this.setResizable(false);
        this.setLayout(new GridLayout(1,2));
        initVar();
        chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        memberPanel = new JPanel();
        memberPanel.setLayout(new BoxLayout(memberPanel, BoxLayout.Y_AXIS));
        memberHolderPanel = new JPanel();
        memberHolderPanel.setLayout(new BoxLayout(memberHolderPanel, BoxLayout.X_AXIS));
        sendMessagePanel = new JPanel();
        sendMessagePanel.setLayout(new BoxLayout(sendMessagePanel, BoxLayout.Y_AXIS));

        chatTextArea = new JTextArea();
        chatTextArea.setPreferredSize(new Dimension(200, 30));
        chatTextArea.setWrapStyleWord(true);
        chatTextArea.setLineWrap(true);
        chatTextArea.setEditable(false);
        chatTextArea.setText(group.chat);
        
        chatPane = new JScrollPane(chatTextArea);
        //holds element of chat
        


        sendTextArea = new JTextField();
        sendTextArea.setPreferredSize(new Dimension(100, 30));
        sendMessageButton = new JButton("Send Message");
        sendMessageButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = sendTextArea.getText();
				c.sendMessage(new MessageObject(message, group.members, c.user, group.ID));
				sendTextArea.setText("");
			}
		});
        sendMessagePanel.add(sendTextArea);
        sendMessagePanel.add(sendMessageButton);
        left = new JLabel("Chat");
        chatPanel.add(left, BorderLayout.NORTH);
        chatPanel.add(chatPane, BorderLayout.CENTER);
        chatPanel.add(sendMessagePanel, BorderLayout.SOUTH);
        chatPanel.setBackground(new Color(250,243,233));
        rightPanel.setBackground(new Color(250,243,233));
        add(chatPanel);
        add(rightPanel);
    	this.addWindowListener(new java.awt.event.WindowAdapter() {
    	    @Override
    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    	       c.login.mainPage.opengroups.remove(this);
    	    }
    	});
    	
    	this.setBackground(color);
	}
	

	    
	public void initVar(){
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
    	border = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		chatPanel = new JPanel();
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2,1));
		picPanel = new JPanel();
		picPanel.setLayout(new BoxLayout(picPanel, BoxLayout.X_AXIS));
		
		todoPanel = new JPanel();
		todoPanel.setBorder(border);
		JPanel ugh = new JPanel();
		ugh.setBackground(new Color(250,243,233));
		ugh.add(new JLabel("Group To-do:"));
		todoPanel.add(ugh);
		todoPanel.setLayout(new BoxLayout(todoPanel, BoxLayout.Y_AXIS));
		membersPanel = new JPanel();
		membersPanel.setBorder(border);
		membersPanel.setLayout(new BoxLayout(membersPanel, BoxLayout.Y_AXIS));
		membersPanel.add(new JLabel("Members:"));
		membersPanel.add(picPanel);
		addMembers();
		addtodo();
		JScrollPane todoscroll = new JScrollPane(todoPanel);
		rightPanel.add(todoscroll);
		rightPanel.add(membersPanel);
	}
	
	public void addtodo(){
		Vector< Pair<Activity, String> > todo = group.todo;
		for(int i = 0; i < todo.size(); i++){
			JPanel temp = new JPanel();
			
			JTextField when = new JTextField();
			when.setPreferredSize(new Dimension( 100, 30));
			JLabel label = new JLabel("When: ");
			temp.add(label);
			temp.add(when);
			Pair<Activity, String> a = todo.get(i);
			Activity z = c.getActivity(a.getKey().ID);
	        if (z.ID != 9999) {
			
			temp.add(this.getCuteActivityBox(a.getKey()));
			if (!todo.get(i).getValue().equals("")){
				when.setText(todo.get(i).getValue());
			}
			when.getDocument().addDocumentListener(new updateIT(i, when));
			temp.add(when);
			todoPanel.add(temp);
	        }
	        else {
	        	group.todo.remove(a);
	        }
		}
		c.createGroup(group);
	}
	
	public void addMembers(){
		Vector<User> users = group.members;
		for (int i = 0; i < users.size(); i++){
			JPanel temp = new JPanel();
			MakePic a = new MakePic(users.get(i).profPic.getImage());
			a.setPreferredSize(new Dimension(100,100));
			a.setBorder(border);
			temp.setPreferredSize(new Dimension (200, 100));
			temp.add(a);
			temp.add(new JLabel(users.get(i).username));
			picPanel.add(temp);
		}
	}
	
	/*public static void main (String [] args){
        Vector<User> uservec = new Vector<User>();
        Group test = (new Group(032, uservec, "Swag Commitee"));
		ActualGroupPage gp = new ActualGroupPage(test);
		gp.setVisible(true);
	}*/
	
	 public void addChat(MessageObject m) {
	        String username = m.sender.firstName + " " + m.sender.lastName;
	        String chatToAdd = username + ": " + m.message + "\n";
	        chatTextArea.append(chatToAdd);
	        group.chat = group.chat + chatToAdd;
	    }
	
	private JPanel getCuteActivityBox(Activity temp) {
		JPanel temppanel = new JPanel();
		temppanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		temppanel.setPreferredSize(new Dimension(200, 60));
		temppanel.setBorder(border);
		
		
		MakePic a = new MakePic(temp.photo.getImage());
		a.setPreferredSize(new Dimension(40,40));
		a.setVisible(true);
		a.setBorder(border);
		a.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}

		});
		temppanel.add(a, BorderLayout.WEST);

		String text = temp.name + " \n" + temp.address + ", " + temp.zipcode + "\n" + temp.description;
		JTextArea area = new JTextArea(text);
		area.setEditable(false);
		area.setOpaque(false);
		temppanel.add(area, BorderLayout.CENTER);

		for(int i = 0; i < temp.rating; i++){
			JButton s = new Photo("Star.PNG").getPhotoButtonOfSize(15, 15);
			//s.setPreferredSize(new Dimension(15,15));
			s.setVisible(true);
			s.setBorder(null);
			temppanel.add(s,BorderLayout.EAST);
		}
		return temppanel;
    }

	public void addActivity(Activity ac) {
		group = c.getGroup(group.ID);
		JPanel temp = new JPanel();
		
		JTextField when = new JTextField();
		when.setPreferredSize(new Dimension( 100, 30));
		JLabel label = new JLabel("When: ");
		temp.add(label);
		temp.add(when);
		Pair<Activity, String> a = group.todo.get(group.todo.size()-1);
		temp.add(this.getCuteActivityBox(a.getKey()));
		if (!group.todo.get(group.todo.size()-1).getValue().equals("")){
			when.setText(group.todo.get(group.todo.size()-1).getValue());
		}
		when.getDocument().addDocumentListener(new updateIT(group.todo.size()-1, when));
		temp.add(when);
		todoPanel.add(temp);
		revalidate();
		repaint();
		
	}
	
	class updateIT implements DocumentListener {

		int i;
		JTextField t;
		public updateIT(int i, JTextField t) {
	      this.i = i;
	      this.t = t;
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			if (t.getText().length() > 7){
			update();
			}
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if (t.getText().length() > 7){
				update();
				}
		}

		private void update() {
			Activity a = group.todo.get(i).getKey();
			group.todo.set(i, new Pair<Activity, String>(a, t.getText()));
			c.createGroup(group);
            group.notificaton = true;
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}


