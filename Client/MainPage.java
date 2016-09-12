package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Server.Interests;
import utility.Photo;
import utility.Search;


//GUI//
//This will get and display all of the MainPage Info

public class MainPage extends JFrame {
	private static final long serialVersionUID = 1L;

	private Vector<Activity> feed;
	private User mainUser;
    private MainClient client;
    private JPanel leftMainPanel, centerMainPanel, rightMainPanel;
    private Border border, loweredbevel, raisedbevel;
    // left panel variables
    private JPanel groupPanel, toDoFeed;
    // middle panel variables
    private JTextField searchField;
    private JPanel recentPostPanel, topPanel, recentPostFeed;
    private SearchButton searchButton;
    private JComboBox<String> searchComboBox;
    private JLabel recentPostLabel;
    //right panel variables
    private JPanel rightTopPanel, userPanel, picPanel, infoPanel, pastActivitiesPanel;
    private CuteButton createActivityButton, messagesButton, editButton;
    private JLabel ageLabel, locationLabel, interestLabel, nameLabel;
    private JPanel groupsye;
    public Vector<Integer> selectedIntrests;
	final Color color = new Color(250,243,233);
	private Font font;
	private Font customFont;
	private Font smallerFont;

	public MessagePage mp;
	public Vector<ActualGroupPage> opengroups;

	private JComboBox<String> sortComboBox;

	private JLabel sorting;
 
    
    public MainPage(User mu, MainClient client) {
    	super("thingadoo");
		System.out.println("Main Page Constructor Started:" + System.currentTimeMillis());
		mainUser = mu;
		this.client = client;
		setSize(1000, 700);
		setLocation(100,50);
		this.setResizable(false);
		
		//create font
        InputStream is;
        try {
            is = new FileInputStream("Pacifico.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 25f);
           customFont = font.deriveFont(Font.PLAIN, 14);
           smallerFont = customFont.deriveFont(Font.PLAIN, 10);
        } catch (FontFormatException | IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        ge.registerFont(customFont);
        ge.registerFont(smallerFont);

		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
    	border = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
    	opengroups = new Vector<ActualGroupPage>();
    	addFakeActivites();
		setUpLeftPanel();
		System.out.println("Main Page Constructor Middle1:" + System.currentTimeMillis());
		setUpMiddlePanel();
		System.out.println("Main Page Constructor Middle2:" + System.currentTimeMillis());

		setUpRightPanel();
        addPastActivity(null);
		messagesButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MainPage.this.mp = new MessagePage(mainUser, client, mainUser.messages);
				mp.setVisible(true);
				messagesButton.staroff();
			}
		});
		System.out.println("Main Page Constructor Middle3:" + System.currentTimeMillis());
		
		setLayout(new GridLayout(1,3));
		leftMainPanel.setBorder(border);
		leftMainPanel.setBackground(new Color(250,243,233));
		add(leftMainPanel);
		centerMainPanel.setBorder(border);
		centerMainPanel.setBackground(new Color(250,243,233));
		add(centerMainPanel);
		rightMainPanel.setBorder(border);
		rightMainPanel.setBackground(new Color(250,243,233));
		add(rightMainPanel);
		System.out.println("Main Page Constructor End:" + System.currentTimeMillis());
		Vector<Integer> it = client.getCategorySearch(mainUser.interests);
		 Vector<Activity> alist = getActivities(it);
     
        addFeedActivity(alist);
		this.setVisible(true);
		this.setBackground(color);
    }

	private Vector<Activity> getActivities(Vector<Integer> it) {
		Vector<Activity> results = new Vector<Activity>();
		for (int i = 0; i < it.size(); i++) {
			Activity a = client.getActivity(it.get(i));
			if (a.ID != 9999) {results.addElement(a);}
		}
	 if (sortComboBox.getSelectedItem().equals(new String("Rating"))) {
		Search.sortResultsByRating(results);
	 }
	 else { 
		 Search.sortResultsByLocation(results, mainUser.zipCode);
	 }
		return results;
	}

	/*public static void main(String[] args) {
		Vector<Comment> ugh = new Vector<Comment>();
		for (int i = 0; i < 5; i++) {
			ugh.add(new Comment("sam picus", "this activity page is so cool", null));
		}
		

		MainPage mp = new MainPage(temp, null);
		mp.setVisible(true);
	}
	*/

	private void leftVariables() {
		leftMainPanel = new JPanel();
		leftMainPanel.setBackground(new Color(250,243,233));
		
		groupPanel = new JPanel();
		toDoFeed = new JPanel();
		toDoFeed.setBackground(new Color(250,243,233));
		groupPanel.setBorder(border);
		toDoFeed.setBorder(border);
		
		Vector<Activity> to_do = mainUser.activities;
		for (int i = 0; i < to_do.size(); i++){
			toDoFeed.add(this.getCuteTodoBox(to_do.get(i)));
		}
    }
    
    private void setUpLeftPanel(){
    	leftVariables();
    	leftMainPanel.setLayout(new GridLayout(3,1));

    	groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));

    	JPanel titlePanel = new JPanel();
    	titlePanel.setBackground(new Color(250,243,233));
    	JLabel groupLabel = new JLabel("My Groups:");
    	groupLabel.setFont(customFont);
    	titlePanel.add(groupLabel);
    	
    	groupPanel.add(titlePanel);
    	groupPanel.setBackground(new Color(250,243,233));

    	 groupsye = new JPanel();
    	 groupsye.setBackground(new Color(250,243,233));
    	groupsye.setLayout(new BoxLayout(groupsye, BoxLayout.Y_AXIS));

    	 Vector<Group> myGroups = mainUser.groups;
    	for (int i = 0; i < myGroups.size(); i++){
    		Group bump = client.getGroup(myGroups.get(i).ID);
    		GroupButton temp = new GroupButton(bump.name, bump.ID);
    		if (bump.notificaton) {
    			///MAKE THE GROUP FUCKING SPEICAL
    		}
    		System.out.println("Adding Buttong " + bump.todo.size());
    		
    		temp.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					ActualGroupPage g = new ActualGroupPage(temp.groupID, client);
					g.setVisible(true);
					MainPage.this.opengroups.add(g);
				}

    		});
    		
    		groupsye.add(temp);
    	}
    	JButton createGroup = new CuteButton(0, "Create Group");
    	createGroup.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CreateGroup swag = new CreateGroup(client);
				swag.setVisible(true);
			}

    	});
    	
    	JPanel cg = new JPanel();
    	cg.setBackground(new Color(250,243,233));
    	cg.setLayout(new FlowLayout(FlowLayout.LEFT));
    	cg.add(createGroup);
    	groupPanel.add(Box.createVerticalGlue());
    	groupPanel.add(groupsye);
    	groupPanel.add(Box.createVerticalGlue());
    	groupPanel.add(cg);
    	
    	//
    	JScrollPane jsp = new JScrollPane(toDoFeed);
    	jsp.getViewport().setBackground(new Color(250,243,233));
    	JPanel toDoPanel = new JPanel(new BorderLayout());
    	JLabel toDoTitle = new JLabel("To-Do List");
    	toDoTitle.setFont(customFont);
    	toDoTitle.setHorizontalAlignment(SwingConstants.CENTER);
    	toDoPanel.add(toDoTitle, BorderLayout.NORTH);
		toDoFeed.setLayout(new BoxLayout(toDoFeed, BoxLayout.Y_AXIS));
		
		if (mainUser.activities != null)
			{
			System.out.println(mainUser.activities.size());
			for (int i = 0; i < mainUser.activities.size(); i++){
				Activity temp = mainUser.activities.get(i);
				JPanel temppanel = getCuteActivityBox(temp);
				toDoFeed.add(temppanel);
				jsp.revalidate();
			}
		}
		toDoPanel.add(toDoFeed, BorderLayout.CENTER);
		// ADD A SCROLL PANE  ME I CANT DO IT OMG

		JPanel j = new JPanel();
			ImageIcon imagea = new ImageIcon("logo.png");
			Image image = imagea.getImage();
			Image scaledImage = image.getScaledInstance(200,200, java.awt.Image.SCALE_SMOOTH);
			imagea = new ImageIcon(scaledImage);
			JLabel imageLabel = new JLabel();
			imageLabel.setIcon(imagea);
			j.add(imageLabel);
			
	
		j.setBackground(new Color(250,243,233));
    	leftMainPanel.add(j);
		groupPanel.setBackground(new Color(250,243,233));
    	leftMainPanel.add(groupPanel);
		toDoPanel.setBackground(new Color(250,243,233));
    	leftMainPanel.add(toDoPanel);
		//leftMainPanel.setBackground(new Color(250,243,233));
    	//leftMainPanel.add(new JLabel("the todo list goes here wtf how does this work"));
    }
    
	public void addGroup(Group g) {
		
		CuteButton temp = new CuteButton(0, g.name);
		if (g.notificaton) {
			//MAKE THE BUTTON FUCKING SPECIAL
		}
		// OPEN THE GROUP OMMMMMMGG
		temp.addActionListener(new ActionListener(){
	
			@Override
			public void actionPerformed(ActionEvent e) {
				ActualGroupPage gp = new ActualGroupPage(g.ID, client);
				gp.setVisible(true);
	
			}
	
		});
		groupsye.add(temp);
		groupsye.repaint();
		groupsye.revalidate();
	}

	public void addFeedActivity(Vector<Activity> a){
		feed = a;	
		recentPostPanel.removeAll();
		System.out.println("FEED SIzE: " + feed.size());
		for (int i = 0; i < feed.size(); i++){
			Activity temp = feed.get(i);
			JPanel temppanel = getCuteActivityBox(temp);
			temppanel.setBackground(color);
			recentPostPanel.add(temppanel);
			recentPostFeed.repaint();
			recentPostFeed.revalidate();
		}
		recentPostFeed.repaint();
		recentPostFeed.revalidate();
		MainPage.this.repaint();
		MainPage.this.revalidate();
	}

    private void addFakeActivites() {
    	//public Activity(int ID, int rating, int zipcode, Image photo, String n, Vector<Comment> comments) {
    	feed = new Vector<Activity>();

    	for (int i = 0; i < 3; i ++){
    		Vector<Comment> ugh = new Vector<Comment>();
    		for (int a = 0 ; a < 5; a++){
    			ugh.add(new Comment("sam picus", "this activity page is so cool", null));
    		}
    		Activity temp = new Activity(117, 5, 90007, null, "Gaming with homies", null, "Its ok when you're single", "no address", null, 1);
	    	Activity temp2 = new Activity(88, 4, 90089, null, "FOOOTBALLLL", null, "Do it all the time", "850 W 37th St, Los Angeles, CA", null, 2);
	    	Activity temp3= new Activity(111, 5, 90210, null, "Celebrity Site Seeing", null, "Find celebrities, take a selfie with them, then insta it", "Beverly Hills", null, 3);
	    	//feed.add(client.getActivity(2));
	    	//feed.add(client.getActivity(3));
	    	//feed.add(client.getActivity(2));
    	}
	}
    
    public void adminRemoveActivity(Activity a){
    	//REFRESH TO DO
    	client.deleteActivity(a);
    	Vector<Activity> to_do = mainUser.activities;
		toDoFeed.removeAll();
		for (int i = 0; i < to_do.size(); i++){
			Activity temp = to_do.get(i);
            Activity z = client.getActivity(temp.ID);
            if (z.ID != 9999) {
			JPanel temppanel = getCuteTodoBox(temp);
			temppanel.setBackground(color);
			toDoFeed.add(temppanel);
			toDoFeed.repaint();
			toDoFeed.revalidate();
            }
            else {
            	mainUser.activities.remove(temp);
            }
		}
    	//Refresh Past Activities
		pastActivitiesPanel.removeAll();
		to_do = mainUser.pastactivites;
		for (int i = 0; i < to_do.size(); i++){
			Activity temp = to_do.get(i);
            Activity z = client.getActivity(temp.ID);
            if (z.ID != 9999) {
			JPanel temppanel = getCuteActivityBox(temp);
			temppanel.setBackground(color);
			pastActivitiesPanel.add(temppanel);
			pastActivitiesPanel.repaint();
			pastActivitiesPanel.revalidate();
            } else {
            	mainUser.pastactivites.remove(temp);
            }
		}
		pastActivitiesPanel.repaint();
		pastActivitiesPanel.revalidate();
		MainPage.this.repaint();
		MainPage.this.revalidate();
		client.updateUser(mainUser);
		Vector<Integer> it = client.getCategorySearch(mainUser.interests);
		 Vector<Activity> alist = getActivities(it);
         addFeedActivity(alist);
    	//Refresh Search!
    	
    }

	private void rightVariables(){
    	rightMainPanel = new JPanel();
    	rightTopPanel = new JPanel();
    	createActivityButton = new CuteButton(0, "Create  Activity");

    	createActivityButton.addActionListener(new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			CreateActivity ca = new CreateActivity(mainUser, client);
    			ca.setMainPage(MainPage.this);
    			ca.setVisible(true);
    		}
    	});
    	
    	
    	

    	messagesButton = new CuteButton(0,"Messages");
    	if (mainUser.newMessages) {
    		newMessageNotification();
    	}
    	rightTopPanel.add(createActivityButton);
    	rightTopPanel.add(messagesButton);
    	rightTopPanel.setBackground(new Color(250,243,233));

		userPanel = new JPanel();
		picPanel = new JPanel();
		editButton = new CuteButton(0,"Edit Profile");
		editButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				NewUser u = new NewUser(client, mainUser, null);
				u.setVisible(true);
				MainPage.this.setVisible(false);
			}
			
		});
		
		
		// CHANGE TO USE THE IMAGE
		if (mainUser.profPic == null){
			System.out.println("No Profile Picture");
		}
		MakePic a = new MakePic(mainUser.profPic.getImage());
		a.setPreferredSize(new Dimension(100,100));
		a.setBorder(border);
    	picPanel.add(a);
    	picPanel.add(editButton);
    	picPanel.setBackground(new Color(250,243,233));
    	
		pastActivitiesPanel = new JPanel();
		pastActivitiesPanel.setLayout(new BoxLayout(pastActivitiesPanel, BoxLayout.Y_AXIS));
		JPanel jp = new JPanel();
		JLabel paLabel = new JLabel("Past Activites:");
    	jp.add(paLabel);
    	pastActivitiesPanel.add(jp);
    	JPanel babyActivityPanel = new JPanel();
    	babyActivityPanel.setLayout(new BoxLayout(babyActivityPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < feed.size(); i++) {
			//THIS IS WHERE TO CHANGE TO PAST ACTIVITY LIST
			Activity temp = feed.get(i);
			JPanel temppanel = getCuteActivityBox(temp);
			babyActivityPanel.add(temppanel);
    	}

		pastActivitiesPanel.add(babyActivityPanel);

		infoPanel = new JPanel();
		nameLabel = new JLabel(mainUser.getFirstName() + " " + mainUser.getLastName());
		nameLabel.setFont(customFont);
		ageLabel = new JLabel("Age: " + mainUser.getAge());
		ageLabel.setFont(customFont);
		locationLabel = new JLabel("Location: " + mainUser.getZipCode());
		locationLabel.setFont(customFont);
    	String interestString = "";
    	Vector<Integer> temp = mainUser.getInterests();
    	System.out.println(temp);
    	for (int i = 0; i < temp.size(); i++){
    		if (i == 0){
				interestString = interestString + Interests.getInterestString(temp.get(i));
			} else {
				interestString = interestString + ", " + Interests.getInterestString(temp.get(i));
			}
    	}
    	interestLabel = new JLabel("Interests: " + interestString);
    	interestLabel.setFont(customFont);
    }
    
    private void setUpRightPanel(){
    	rightVariables();
    	rightMainPanel.add(rightTopPanel, BorderLayout.NORTH);

		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(Box.createVerticalGlue());
		infoPanel.add(nameLabel);
		infoPanel.add(Box.createVerticalGlue());
    	infoPanel.add(ageLabel);
    	infoPanel.add(Box.createVerticalGlue());
    	infoPanel.add(locationLabel);
    	infoPanel.add(Box.createVerticalGlue());
    	infoPanel.add(interestLabel);
    	infoPanel.add(Box.createVerticalGlue());

    	infoPanel.setBackground(new Color(250,243,233));

		userPanel.setLayout(new GridLayout(2, 1));
		userPanel.add(picPanel);
		userPanel.add(infoPanel);

		rightMainPanel.add(userPanel, BorderLayout.CENTER);
		rightMainPanel.add(pastActivitiesPanel, BorderLayout.SOUTH);
	}

	private void middleVariables(){
    	centerMainPanel = new JPanel();
    	recentPostPanel = new JPanel();
    	recentPostFeed = new JPanel();
    	topPanel = new JPanel();
    	recentPostLabel = new JLabel("Recent Posts:");
    	searchComboBox = new JComboBox<String>();
    	searchComboBox.addItem("Keyword");
    	searchComboBox.addItem("Location");
    	searchComboBox.addItem("Interest");
    	searchComboBox.setFont(customFont);
    	sorting = new JLabel("Order By:");
    	sorting.setFont(customFont);
    	sortComboBox = new JComboBox<String>();
    	sortComboBox.setFont(customFont);
    	sortComboBox.addItem("Rating");
    	sortComboBox.addItem("Location");
    	sortComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (sortComboBox.getSelectedItem().equals(new String("Rating"))) {
					Search.sortResultsByRating(feed);
					System.out.println("FEED SIzE: " + feed.size());
					addFeedActivity(feed);
				} else {
					Search.sortResultsByLocation(feed, mainUser.zipCode);
					System.out.println("FEED SIzE: " + feed.size());
					addFeedActivity(feed);
				}
				
			}
		});
    	//searchComboBox.addItem("Search Option4");
    	searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(120, 30));
		searchButton = new SearchButton();
		searchButton.setPreferredSize(new Dimension(20, 20));
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<Activity> alist;
				String search = searchField.getText();
				if (searchComboBox.getSelectedItem().equals(new String("Location"))) {
					  Vector<Integer> it = client.getLocationSearch(Integer.parseInt(search));
					  Vector<Activity> results = new Vector<Activity>();
						for (int i = 0; i < it.size(); i++) {
							Activity a = client.getActivity(it.get(i));
							if (a.ID != 9999) {results.addElement(a);}
						}
						alist = results;
					  sortComboBox.setEnabled(false);

				      
				} 
				else if (searchComboBox.getSelectedItem().equals(new String("Keyword"))) {
					Vector<String> words = new Vector<>();
					  Scanner fileScan= new Scanner(search).useDelimiter(" ");
				        while(fileScan.hasNext()){       
				            words.add(fileScan.next());
				        }
					Vector<Integer> it = client.getStringSearch(words);
					  alist = getActivities(it);
					  sortComboBox.setEnabled(true);

				      
				}
				else {
					
					new InterestPopup(selectedIntrests).setLocale(getLocale());
					selectedIntrests = client.getCategorySearch(selectedIntrests);
					  alist = getActivities(selectedIntrests);
					  sortComboBox.setEnabled(true);
				        
				}
				addFeedActivity(alist);
			}
		});
		searchButton.setBorder(null);
	}

	private void setUpMiddlePanel() {
		middleVariables();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		JPanel toptop = new JPanel();
		toptop.add(searchField);
		toptop.add(searchButton);
		toptop.add(searchComboBox);
		toptop.setBackground(new Color(250,243,233));
		JPanel topbottom = new JPanel();
		topbottom.setBackground(new Color(250,243,233));
		topbottom.add(sorting);
		topbottom.add(sortComboBox);
		topPanel.add(toptop);
		topPanel.add(topbottom);
		topPanel.setBorder(border);
		JScrollPane jsp = new JScrollPane(recentPostPanel);
		recentPostPanel.setLayout(new BoxLayout(recentPostPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < feed.size(); i++){
			Activity temp = feed.get(i);
			JPanel temppanel = getCuteActivityBox(temp);
			temppanel.setBackground(new Color(250,243,233));
			recentPostPanel.add(temppanel);
			jsp.revalidate();
		}

		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		centerMainPanel.add(topPanel, BorderLayout.NORTH);
		centerMainPanel.add(jsp, BorderLayout.CENTER);
	}

	private JPanel getCuteActivityBox(Activity temp) {
		JPanel temppanel = new JPanel();
		temppanel.setBackground(new Color(250,243,233));
		temppanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		temppanel.setPreferredSize(new Dimension(300, 60));
		temppanel.setBorder(border);
		
		
		MakePic a = new MakePic(temp.photo.getImage());
		a.setPreferredSize(new Dimension(40,40));
		a.setVisible(true);
		a.setBorder(border);
		a.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("calling action listenr for activity");
				//Activity a = client.getActivity(temp.ID);
				ActivityPage ac = new ActivityPage(temp, mainUser, client);
				ac.setVisible(true);
			}

		});
		temppanel.add(a, BorderLayout.WEST);

		String text = temp.name + " \n" + temp.address + ", " + temp.zipcode + "\n" + temp.description;
		JTextArea area = new JTextArea(text);
		area.setFont(smallerFont);
		area.setEditable(false);
		area.setOpaque(false);
		temppanel.add(area, BorderLayout.CENTER);

		for(int i = 0; i < temp.rating; i++){
			JButton s = new Photo("Star.PNG").getPhotoButtonOfSize(13, 13);
			//s.setPreferredSize(new Dimension(15,15));
			s.setVisible(true);
			s.setBorder(null);
			temppanel.add(s,BorderLayout.EAST);
		}
		return temppanel;
    }
	
	private JPanel getCuteTodoBox(Activity temp) {
		
		JPanel temppanel = new JPanel();
		temppanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		temppanel.setPreferredSize(new Dimension(300, 60));
		temppanel.setBorder(border);
		temppanel.setBackground(new Color(250,243,233));
		CuteButton remove = new CuteButton(0, "-");
		remove.setPreferredSize(new Dimension (20,20));
		remove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//	SAM REMOVE THIS ACTIVITY FROM THE CLIENT
				System.out.println("getting called");
				mainUser.removeToDo(temp);
				MainPage.this.addActivity(null);
			}
			
		});
		
		MakePic a = new MakePic(temp.photo.getImage());
		a.setPreferredSize(new Dimension(40,40));
		a.setVisible(true);
		a.setBorder(border);
		a.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("calling action listenr for activity");
				//Activity a = client.getActivity(temp.ID);
				ActivityPage ac = new ActivityPage(temp, mainUser, client);
				ac.setVisible(true);
			}

		});
		temppanel.add(a, BorderLayout.WEST);

		String text = temp.name + " \n" + temp.address + ", " + temp.zipcode + "\n" + temp.description;
		JTextArea area = new JTextArea(text);
		area.setFont(smallerFont);
		area.setEditable(false);
		area.setOpaque(false);
		temppanel.add(area, BorderLayout.CENTER);
		//temppanel.add(remove, BorderLayout.CENTER);

		for(int i = 0; i < temp.rating; i++){
			JButton s = new Photo("Star.PNG").getPhotoButtonOfSize(13, 13);
			//s.setPreferredSize(new Dimension(15,15));
			s.setVisible(true);
			s.setBorder(null);
			temppanel.add(s,BorderLayout.EAST);
		}
		temppanel.add(remove, BorderLayout.EAST);
		return temppanel;
    }

	// for todo list
	public void addActivity(Activity a) {	
		System.out.println("refreshing todo");
		Vector<Activity> to_do = mainUser.activities;
		toDoFeed.removeAll();
		for (int i = 0; i < to_do.size(); i++){
			Activity temp = to_do.get(i);
			   Activity z = client.getActivity(temp.ID);
	            if (z.ID != 9999) {
				JPanel temppanel = getCuteTodoBox(temp);
				toDoFeed.add(temppanel);
				toDoFeed.repaint();
				toDoFeed.revalidate();
	            }
	            else {
	            	mainUser.activities.remove(temp);
	            }
		}
		client.updateUser(mainUser);
		toDoFeed.repaint();
		toDoFeed.revalidate();
		MainPage.this.repaint();
		MainPage.this.revalidate();
	}

	public void addPastActivity(Activity activityToAdd) {
		pastActivitiesPanel.removeAll();
		if (activityToAdd != null) mainUser.pastactivites.add(activityToAdd);
		Vector<Activity> to_do = mainUser.pastactivites;
		for (int i = 0; i < to_do.size(); i++){
			Activity temp = to_do.get(i);
			   Activity z = client.getActivity(temp.ID);
	            if (z.ID != 9999) {
				JPanel temppanel = getCuteActivityBox(temp);
				pastActivitiesPanel.add(temppanel);
				pastActivitiesPanel.repaint();
				pastActivitiesPanel.revalidate();
	            } else {
	            	mainUser.pastactivites.remove(temp);
	            }
		}
		client.updateUser(mainUser);
		pastActivitiesPanel.repaint();
		pastActivitiesPanel.revalidate();
		MainPage.this.repaint();
		MainPage.this.revalidate();
	}

	public void newMessageNotification() {
		messagesButton.staron();
	}

	public void addToGroup(Group group, Activity a) {
		System.out.println("Calling Add To Group!");
		for (int i = 0; i < opengroups.size(); i++ ) {
			if (opengroups.get(i).group.ID == group.ID) {
				opengroups.get(i).addActivity(a);
				System.out.println("Found the Group");
			}
		}
		
	}
}

class SearchButton extends JButton{
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g){
		Image image = null;
		try {
			image = ImageIO.read(new File("search.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		super.paintComponent(g);
	}
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
