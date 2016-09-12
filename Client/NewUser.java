package Client;

import javax.swing.*;

import sun.security.jgss.LoginConfigImpl;
import utility.FontLibrary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

public class NewUser extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public Image profPic;
	JLabel titleLabel, usernameLabel, passwordLabel, password2Label, emailLabel, picLabel, interestLabel;
	JTextField locField, firstnameField, lastnameField, usernameField, passwordField, password2Field, emailField, ageField;
	JButton adminButton, filedialog, thingadooButton, profPicBrowseButton;
	JPanel uFrame, passFrame, pass2Frame, eFrame, pFrame, iFrame, buttonFrame, tFrame, buttonPanel, categoriesBox;
	CheckboxGroup interests;
	JComboBox<String> dateComboBox;
	private Vector<JCheckBox> checkboxes;
	private User edit;
	private MainClient client;
	private MainPage mainPage;
	public LogIn l;
	int admin;
	final Color color = new Color(250,243,233);
    private JCheckBox active, chilling, extreme, learning, movies, museums, outdoors, romantic;
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);


	
	public NewUser(MainClient client, User ur, LogIn l){
		super("thingadoo");
		font = font.deriveFont(Font.BOLD);
		this.client =  client;
		this.l = l;
		edit = ur;
		setSize(350, 590);
		admin = 0;
		initVariables();
		setLocation(600, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//create font
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        ge.registerFont(customFont);	
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlePanel.add(titleLabel);
		
		JPanel fnPanel = new JPanel();
		fnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel fn = new JLabel("First name:");
		fn.setFont(customFont);
		fnPanel.add(fn);
		fnPanel.add(firstnameField);
		
		JPanel lnPanel = new JPanel();
		lnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel ln = new JLabel("Last name:");
		ln.setFont(customFont);
		lnPanel.add(ln);
		lnPanel.add(lastnameField);
		
		JPanel agePanel = new JPanel();
		agePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel age = new JLabel("Age:");
		age.setFont(customFont);
		agePanel.add(age);
		agePanel.add(ageField);
		
		JPanel locPanel = new JPanel();
		locPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel loc = new JLabel("Location:");
		loc.setFont(customFont);
		locPanel.add(loc);
		locPanel.add(locField);
		
		passFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
		passFrame.add(passwordLabel);
		passFrame.add(passwordField);
		
		pass2Frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		pass2Frame.add(password2Label);
		pass2Frame.add(password2Field);
		
		eFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
		eFrame.add(emailLabel);
		eFrame.add(emailField);
		
		pFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
		pFrame.add(picLabel);
		filedialog.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ImagesLoading a = new ImagesLoading(NewUser.this);
			}
			
		});
		pFrame.add(filedialog);
		
		iFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
		iFrame.setLayout(new BoxLayout(iFrame, BoxLayout.Y_AXIS));
		iFrame.add(Box.createGlue());
		iFrame.add(interestLabel);
		iFrame.add(Box.createGlue());
		
		tFrame.add(thingadooButton);
		
		JLabel categoriesTextLabel = new JLabel("Categories");
		categoriesTextLabel.setFont(customFont);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2));
        active = new JCheckBox("Active");
        active.setFont(customFont);
        chilling = new JCheckBox("Chilling");
        chilling.setFont(customFont);
        extreme = new JCheckBox("Extreme");
        extreme.setFont(customFont);
        learning = new JCheckBox("Learning");
        learning.setFont(customFont);
        movies = new JCheckBox("Movies");
        movies.setFont(customFont);
        museums = new JCheckBox("Museums");
        museums.setFont(customFont);
        outdoors = new JCheckBox("Outdoors");
        outdoors.setFont(customFont);
        romantic = new JCheckBox("Romantic");
        romantic.setFont(customFont);
        
        checkboxes = new Vector<JCheckBox>();
        checkboxes.add(active);
        checkboxes.add(chilling);
        checkboxes.add(extreme);
        checkboxes.add(learning);
        checkboxes.add(movies);
        checkboxes.add(museums);
        checkboxes.add(outdoors);
        checkboxes.add(romantic);
        

        buttonPanel.add(active);
        buttonPanel.add(chilling);
        buttonPanel.add(extreme);
        buttonPanel.add(learning);
        buttonPanel.add(movies);
        buttonPanel.add(museums);
        buttonPanel.add(outdoors);
        buttonPanel.add(romantic);
        buttonPanel.setBackground(new Color(250,243,233));
        categoriesBox = new JPanel();

        categoriesBox.setBackground(new Color(250,243,233));
        categoriesBox.setLayout(new BoxLayout(categoriesBox, BoxLayout.Y_AXIS));
        JPanel catLabel = new JPanel();
        catLabel.add(categoriesTextLabel);
        catLabel.setBackground(new Color(250,243,233));
        categoriesBox.add(catLabel);
        categoriesBox.add(buttonPanel);
		
        //set backgrounds
        titlePanel.setBackground(new Color(250,243,233));
        fnPanel.setBackground(new Color(250,243,233));
        lnPanel.setBackground(new Color(250,243,233));
        agePanel.setBackground(new Color(250,243,233));
        locPanel.setBackground(new Color(250,243,233));
        passFrame.setBackground(new Color(250,243,233));
        pass2Frame.setBackground(new Color(250,243,233));
        eFrame.setBackground(new Color(250,243,233));
        pFrame.setBackground(new Color(250,243,233));
        categoriesBox.setBackground(new Color(250,243,233));
        buttonFrame.setBackground(new Color(250,243,233));
        tFrame.setBackground(new Color(250,243,233));
        
        //add gui elements into overall layout
		add(titlePanel);
		add(fnPanel);
		add(lnPanel);
		add(agePanel);
		add(locPanel);
		add(passFrame);
		add(pass2Frame);
		add(eFrame);
		add(pFrame);
		add(categoriesBox);
		add(buttonFrame);
		add(tFrame);	
		adminButton = new CuteButton(0, "");
		adminButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Admin a = new Admin(NewUser.this);
			}
			
		});
		add(adminButton);
		setBackground(new Color(250,243,233));
        thingadooButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Create User Started");
				String username = emailField.getText();
				String pass = passwordField.getText();
				String pass2 =  password2Field.getText();
			    String fname = firstnameField.getText();
			    String lname = lastnameField.getText();
			    int age = Integer.parseInt(ageField.getText());
			    int loc = Integer.parseInt(locField.getText());
		    	Vector<Integer> categories = new Vector<Integer>();
		    	Vector <Group> myGroups;
			    if (!pass.equals(pass2)) {
			    	JOptionPane.showMessageDialog(NewUser.this, "Please Enter Matching Passwords");
			        return;
			    }
			    if (username.equals(new String(""))) {
			    	JOptionPane.showMessageDialog(NewUser.this, "Must Have username!");
                    return;
			    }
			    System.out.println("checkbox size is "+checkboxes.size());
		    	for (int i=0; i < checkboxes.size(); i++){
		    		if (checkboxes.get(i).isSelected()){
		    			System.out.println("adding something");
		    			categories.add(i);
		    		}
		    	}
		    	if(categories.size() != 3) {
			    	JOptionPane.showMessageDialog(NewUser.this, "Please Select Three Interests");
			        return;
		    	}
		    	if (profPic == null){
		    		System.out.println("prof pic is null in new user");
		    	}
		    	if (edit == null){
		    		myGroups = new Vector<Group>();
		    	}
		    	else{
		    		myGroups = edit.getGroups();
		    	}
		    	try {
					MessageDigest m = MessageDigest.getInstance("MD5");
					m.reset();
					m.update(pass.getBytes());
					byte[] digest = m.digest();
					BigInteger bigInt = new BigInteger(1,digest);
					pass = bigInt.toString(16);
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		    	System.out.println("admin is "+ admin);
			    User u = new User(username, fname, lname, age, -1, loc, categories, myGroups, pass, admin, profPic);
			    if (edit == null) {
			       u =  client.createUser(u); }
			    else {
			    	u = edit;
			    	u.username = username;
			    	u.firstName = fname;
			    	u.lastName = lname;
			    	u.age = age;
			    	u.zipCode = loc;
			    	u.interests =  categories;
			    	u.password = pass;
			    	u.Admin = 0;
			        if (profPic != null) u.profPic =  new ImageIcon(profPic);
			    	client.updateUser(u);
			    }
			    if (u == null) {
			    	JOptionPane.showMessageDialog(NewUser.this, "Username has Been Taken try another!");
			        return;
			    }
			    mainPage = new MainPage(u, client);
			    mainPage.setVisible(true);
			    if (l!= null) l.mainPage = mainPage;
			    NewUser.this.dispose();
				
			}
		});
        
        	this.getContentPane().setBackground(color);
	}
	
	public void initVariables(){
		if (edit == null){
			thingadooButton = new CuteButton(0, "Create User");
		}
		else {
			thingadooButton = new CuteButton(0, "Update Profile");
		}
		titleLabel = new JLabel("New User: ");
		titleLabel.setFont(font);
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(customFont);
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(customFont);
		password2Label = new JLabel("Re-enter Password: ");
		password2Label.setFont(customFont);
		emailLabel = new JLabel("Email: ");
		emailLabel.setFont(customFont);
		picLabel = new JLabel("Profile Picture: ");
		picLabel.setFont(customFont);
		filedialog = new CuteButton(0, "Browse");
		interestLabel = new JLabel("Interests:");
		interestLabel.setFont(customFont);
		firstnameField = new JTextField();
		firstnameField.setFont(customFont);
		firstnameField.setPreferredSize( new Dimension( 255, 30 ) );
		locField = new JTextField();
		locField.setFont(customFont);
		locField.setPreferredSize( new Dimension( 255, 30 ) );
		ageField = new JTextField();
		ageField.setFont(customFont);
		ageField.setPreferredSize( new Dimension( 300, 30 ) );
		lastnameField = new JTextField();
		lastnameField.setFont(customFont);
		lastnameField.setPreferredSize( new Dimension( 255, 30 ) );
		passwordField = new JPasswordField();
		passwordField.setPreferredSize( new Dimension( 260, 30 ) );
		password2Field = new JPasswordField();
		password2Field.setPreferredSize( new Dimension( 200, 30 ) );
		usernameField = new JPasswordField();
		usernameField.setPreferredSize( new Dimension( 200, 30 ) );
		emailField = new JTextField();
		emailField.setFont(customFont);
		emailField.setPreferredSize( new Dimension( 287, 30 ) );
		if (edit != null){
			firstnameField.setText(edit.firstName);
			lastnameField.setText(edit.lastName);
			ageField.setText(Integer.toString(edit.age));
			usernameField.setText(edit.username);
			locField.setText( Integer.toString(edit.zipCode));
			emailField.setText(edit.username);
		}
		uFrame = new JPanel();
		passFrame = new JPanel();
		pass2Frame = new JPanel();
		eFrame = new JPanel();
		pFrame = new JPanel();
		iFrame = new JPanel();
		buttonFrame = new JPanel();
		tFrame = new JPanel();
		interests = new CheckboxGroup();
	}
}

class Admin extends JFrame{
	private static final long serialVersionUID = 1L;

	Admin(NewUser u){
		super("Thing-a-doo");
		this.setSize(200,200);
		setLayout(new GridLayout(3, 1));
		JLabel secret = new JLabel("Enter secret passcode:");
		secret.setFont(FontLibrary.getTrueTypeFont(14));
		this.add(secret);
		JTextField text = new JTextField();
		text.setFont(FontLibrary.getTrueTypeFont(14));
		text.setPreferredSize(new Dimension (30, 100));
		this.add(text);
		this.setBackground(new Color(250,243,233));
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton submit = new CuteButton(0, "Submit");
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (text.getText().equals("miller is the best")){
					u.admin = 1;
					System.out.println("Admin On");
				}
				
				Admin.this.setVisible(false);
			}
			
		});
		this.add(submit);
		this.setVisible(true);
		this.setBackground(new Color(250,243,233));
	}
}
