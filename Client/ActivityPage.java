package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.ParagraphView;

import Server.Interests;
import javafx.util.Pair;
import utility.FontLibrary;
import utility.Photo;
import utility.calculateZipDistance;

//GUI//
//This will provide a GUI to Actually display an Activity
//In Order to Instantiate it you will need to pass an Activity
public class ActivityPage extends JFrame {
	private static final long serialVersionUID = 117L;
	public JComboBox<String> comboBox;
	public Vector<Comment> comments;
	JPanel topPanel, bottomPanel;
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);
	
	/*	public int rating;
	public int zipcode;
	public String description;
	public int ID;
	private ImageIcon photo;
	public String name;
	public Vector<Comment> comments;
	public String address;
	*/
	// variables for top panel
	JLabel nameLabel, addressLabel, distanceLabel, desLabel;
	JPanel namePanel, picPanel, infoPanel, upPanel, desPanel, starPanel, midPanel, commentTitle;
	JButton addAct;
	private Activity act;
	private User me;
	private Border border, loweredbevel, raisedbevel;
	public MainClient client;
	final Color color = new Color(250,243,233);
	
	
	public ActivityPage (Activity act, User me, MainClient c){
		super("thingadoo");
		this.setBackground(color);
		this.act = act;
		this.client = c;
		this.comments = act.comments;
		setSize(500,600);
		this.me = me;
		setLocation(200,100);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
    	border = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		makeTheGUI();
	}

	public static void main(String[] args) {
		Vector<Comment> ugh = new Vector<Comment>();
		for (int i = 0; i < 5; i++) {
			ugh.add(new Comment("sam picus", "this activity page is so cool", null));
		}

		Vector<Integer> tempvec = new Vector<Integer>();
		tempvec.add(2);
		tempvec.add(3);
		Vector<Group> groupvec = new Vector<Group>();
		groupvec.add(new Group(0, null, "Swag Commitee"));
		groupvec.add(new Group(0, null, "Comp Sci or Die"));
		groupvec.add(new Group(0, null, "Miller's Fan Club"));
		
	}
	
	public void initVar(){
		topPanel = new JPanel();
		topPanel.setBackground(new Color(250,243,233));
		bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(250,243,233));
		nameLabel = new JLabel(act.name);
		nameLabel.setFont(customFont);
		nameLabel.setBackground(new Color(250,243,233));
		addressLabel = new JLabel("Address: "+act.address);
		addressLabel.setFont(customFont);
		addressLabel.setBackground(new Color(250,243,233));
		distanceLabel = new JLabel ("Distance: fix this shit yo");
		distanceLabel.setFont(customFont);
		distanceLabel.setBackground(new Color(250,243,233));
		desLabel = new JLabel("Description: "+act.description);
		desLabel.setFont(customFont);
		desLabel.setBackground(new Color(250,243,233));
		namePanel = new JPanel();
		namePanel.setBackground(new Color(250,243,233));
		picPanel = new JPanel();
		picPanel.setBackground(new Color(250,243,233));
		infoPanel = new JPanel();
		infoPanel.setBackground(new Color(250,243,233));
		desPanel = new JPanel();
		desPanel.setBackground(new Color(250,243,233));
		upPanel = new JPanel();
		upPanel.setBackground(new Color(250,243,233));
		starPanel = new JPanel();
		starPanel.setBackground(new Color(250,243,233));
		midPanel = new JPanel();
		midPanel.setBackground(new Color(250,243,233));
		addAct = new CuteButton(0,"Add to To-do List");
		addAct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddAct a = new AddAct(me, ActivityPage.this.act, client);
				a.setVisible(true);
			}

		});
	}
	
	public void makeTheGUI(){
		initVar();
		this.setLayout(new GridLayout(2,1));

		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		namePanel.add(nameLabel);
		topPanel.add(namePanel);
		topPanel.add(starPanel);

		midPanel.setLayout(new GridLayout(1,2));
		MakePic pic = new MakePic(act.photo.getImage());
		pic.setPreferredSize(new Dimension(150,150));
		pic.setBorder(border);
		picPanel.add(pic);
		for (int i = 0; i < act.rating; i++){
			JButton s = new Photo("Star.PNG").getPhotoButtonOfSize(15, 15);
			//s.setPreferredSize(new Dimension(15,15));
			s.setVisible(true);
			s.setBorder(null);
			starPanel.add(s);
		}
		midPanel.setLayout(new GridLayout(1, 2));
		midPanel.add(picPanel);

		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(new Color(250,243,233));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    	String interestString = "";
    	Vector<Integer> temp = act.categories;
    	Double distanceFromYou = 0.0;
    	for (int i = 0; i < temp.size(); i++){
    		if (i == 0){
				interestString = interestString + Interests.getInterestString(temp.get(i));
			} else {
				interestString = interestString + ", " + Interests.getInterestString(temp.get(i));
			}
    	}
    	try {
			distanceFromYou = calculateZipDistance.getDistance(me.zipCode, act.zipcode);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String info = "\nAddress: " + act.address + "\n" + "\n" + "Distance From You: "+ distanceFromYou + " miles" +  "\n" + ""
				+ "\n Categories: " + interestString;
		JTextArea infoArea = new JTextArea(info);
		infoArea.setBackground(new Color(250,243,233));
		infoArea.setFont(customFont);
		infoArea.setEditable(false);
		infoArea.setOpaque(false);

		infoPanel.add(infoArea);
		infoPanel.add(Box.createHorizontalGlue());
		infoPanel.add(Box.createVerticalGlue());
		infoPanel.add(addAct);
		midPanel.add(infoPanel);
		//midPanel.add(addAct);
		topPanel.add(midPanel);

		JPanel desPanel = new JPanel();
		desPanel.setBackground(new Color(250,243,233));
		JTextArea desArea = new JTextArea("Description: " + act.description);
		desArea.setFont(customFont);
		desPanel.setBorder(border);
		desArea.setEditable(false);
		desArea.setOpaque(false);
		desPanel.add(desArea);
		topPanel.add(desPanel);

		bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(250,243,233));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

		add(topPanel);
		commentTitle = new JPanel();
		commentTitle.setBackground(new Color(250,243,233));
		commentTitle.setLayout(new BoxLayout(commentTitle, BoxLayout.X_AXIS));
		JButton addButton = new CuteButton(0, "Add Comment");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddComment ac = new AddComment(me, act, ActivityPage.this);
				ac.setVisible(true);
				Comment newComment = ac.c;
			}

		});
		JLabel commentsa = new JLabel("  Comments:");
		commentsa.setFont(customFont);
		commentTitle.add(commentsa);
		commentTitle.add(Box.createHorizontalGlue());
		commentTitle.add(addButton);
		if (me.Admin == 1) {
			System.out.println("Am Admin");
		     JButton delete =  new JButton("Delete"); 
		     delete.setMaximumSize(new Dimension(50, 30));
		     delete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					client.mainPage.adminRemoveActivity(act);	
				}
			});
		     commentTitle.add(delete);
		}
		bottomPanel.add(commentTitle);
		for (int i = 0; i < comments.size(); i++) {
			bottomPanel.add(getCuteCommentBox(comments.get(i)));
		}
		bottomPanel.setBorder(border);
		add(bottomPanel);
		
	}

	// for testing

	protected void addComment(Comment com) {
		Vector<Comment> temp = new Vector<Comment>();
		temp.add(com);
		for (int i = 1; i < comments.size(); i++) {
			temp.add(comments.get(i - 1));
		}
		comments = temp;
		act.setComment(temp);
		bottomPanel.removeAll();
		ActivityPage ap = new ActivityPage(act, me, client);
		ap.setVisible(true);
		this.setVisible(false);
	}
	
    private JPanel getCuteCommentBox(Comment temp){
		JPanel temppanel = new JPanel();
		temppanel.setBackground(new Color(250,243,233));
		temppanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		temppanel.setPreferredSize(new Dimension(300, 60));
		temppanel.setBorder(border);
		// FIX THIS ALANA OMG
		MakePic a = new MakePic(temp.image.getImage());
		a.setPreferredSize(new Dimension(30,30));
		a.setVisible(true);
		a.setBorder(border);
		temppanel.add(a, BorderLayout.WEST);
		
		String text = temp.username + " - " + temp.des;
		JTextArea area = new JTextArea(text);
		area.setFont(customFont);
		area.setBackground(new Color(250,243,233));
		area.setEditable(false);
		area.setOpaque(false);
		temppanel.add(area, BorderLayout.CENTER);
		return temppanel;
    }
}


class AddAct extends JDialog {
	private static final long serialVersionUID = 1L;
	public int addActivitySelected;
	private JComboBox<String> comboBox;
	public Activity a;
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);

	public AddAct(User me, Activity a, MainClient c){
		super();
		setTitle("Thing-a-doo!!");
		setSize(240,130);
		setLocation(700,200);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.a = a;
    	comboBox = new JComboBox<String>();
    	comboBox.setFont(customFont);
    	comboBox.setPreferredSize(new Dimension (200, 30));
    	Vector<Group> myGroups = me.getGroups();
    	comboBox.addItem("My To-do List");
    	for (int i = 0; i < myGroups.size(); i++ ){
    		comboBox.addItem(myGroups.get(i).name);
    	}
    	JPanel temp = new JPanel();
    	temp.setBackground(new Color(250,243,233));
    	JPanel temp2 = new JPanel();
    	temp2.setBackground(new Color(250,243,233));
    	JLabel addActivity = new JLabel("Add this activity to..");
    	addActivity.setFont(customFont);
    	temp2.add(addActivity);
    	temp.add(comboBox);
    	
    	JButton addButton = new CuteButton(0, "Add");
    	addButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addActivitySelected = comboBox.getSelectedIndex();
				if (addActivitySelected == 0) {
					me.activities.addElement(a);
					c.updateUser(me);
					c.login.mainPage.addActivity(a);
				} else {
					Group bump = c.getGroup(me.groups.get(addActivitySelected-1).ID);
					System.out.println(bump.todo.size());
					bump.todo.addElement(new Pair<Activity, String>(a, new String("")));
					System.out.println(bump.todo.size());
					c.createGroup(bump);
					c.login.mainPage.addToGroup(bump, a);

				}
				AddAct.this.setVisible(false);
			}
    		
    	});
    	
    	this.add(temp2, BorderLayout.NORTH);
    	this.add(temp, BorderLayout.CENTER);
    	this.add(addButton, BorderLayout.SOUTH);
    	
    	this.setModal(true);
	}
}

class AddComment extends JDialog{
	private static final long serialVersionUID = 1L;
	public JTextField des;
	public Comment c;
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);
	
	public AddComment(User me, Activity act, ActivityPage ap){
		super();
		setTitle("Thing-a-doo!!");
		setSize(400, 300);
		setLocation(700,400);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		des = new JTextField();
		des.setFont(customFont);
		des.setPreferredSize(new Dimension( 100, 200));
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel temp = new JPanel();
		temp.setBackground(new Color(250,243,233));
		JLabel addComment = new JLabel("Add A Comment!");
		JLabel desLabel = new JLabel("Description:");
		desLabel.setFont(customFont);
		addComment.setFont(customFont);
		temp.add(addComment);
		this.add(temp);
		this.add(desLabel);
		this.add(des);
		
		JButton confirm = new CuteButton(0, "Confirm");
		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Comment temp = new Comment(me.username, AddComment.this.des.getText(), me.profPic);
				c = temp;
				ap.comments.add(temp);
				ap.client.createActivity(act);
				AddComment.this.setVisible(false);
				ap.addComment(temp);
			}
			
		});
		this.add(confirm);
		this.setModal(true);
	}
}
