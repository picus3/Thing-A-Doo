package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import utility.FontLibrary;

//GUI//
public class CreateActivity extends JDialog {
	private static final long serialVersionUID = 1L;
	//Will return a new Activity to be pushed to the database
    //Will open an Activity Page
    private JLabel pictureTextLabel, titleTextLabel, addressTextLabel, zipCodeLabel, ratingTextLabel, categoriesTextLabel,
            descriptionTextLabel;
    private JTextField addressTF, ratingTF, titleTF, zipCodeTF;
    private CuteButton uploadPictureButton, submitButton;
    private JTextArea descriptionTA;
    private JPanel titleBox, pictureBox, addressBox, ratingBox, categoriesBox, descriptionBox, submitBox;
    private JPanel buttonPanel;
    private JCheckBox active, chilling, extreme, learning, movies, museums, outdoors, romantic;
    private JScrollPane descriptionJSP;
    private int activityID, creatorID, rating, zipCode;
    private ImageIcon activityPic;
    private String name, address, description;
    private Vector<Integer> categories;
    private Vector<Comment> comments;
    private Vector<JCheckBox> checkboxes;
    private User mainUser;
    private MainClient mainClient;
    public ImageIcon img;
    private MainPage mp;
	final Color color = new Color(250,243,233);
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);

    
    public CreateActivity(User u, MainClient mc) {
        super();
        setSize(500, 400);
        mainUser = u;
        mainClient = mc;
        createGUI();
        makeTheActivity();
        this.setBackground(color);
        
        //create font
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        ge.registerFont(customFont);	
        
    }

    public void setMainPage(MainPage a){
    	mp = a;
    }
 
    private void createGUI() {
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pictureTextLabel = new JLabel("Picture");
        pictureTextLabel.setFont(customFont);
        pictureTextLabel.setMinimumSize(new Dimension(50, 10));
        uploadPictureButton = new CuteButton(0,"Browse");
        uploadPictureButton.setToolTipText("upload a photo");
        pictureBox = new JPanel();
        pictureBox.setBackground(color);
        pictureBox.setLayout(new BoxLayout(pictureBox, BoxLayout.X_AXIS));
        pictureBox.add(pictureTextLabel);
        pictureBox.add(uploadPictureButton);
        pictureBox.add(Box.createHorizontalGlue());

        titleTextLabel = new JLabel("Activity Name");
        titleTextLabel.setFont(customFont);
        titleTF = new JTextField(15);
        titleTF.setFont(customFont);
        titleBox = new JPanel();
        titleBox.add(titleTextLabel);
        titleBox.add(titleTF);


        addressTextLabel = new JLabel("Address");
        addressTextLabel.setFont(customFont);
        addressTF = new JTextField(15);
        addressTF.setFont(customFont);
        zipCodeLabel = new JLabel("Zip Code");
        zipCodeLabel.setFont(customFont);
        zipCodeTF = new JTextField(5);
        zipCodeTF.setFont(customFont);
        addressBox = new JPanel();
        addressBox.setLayout(new BoxLayout(addressBox, BoxLayout.X_AXIS));
        addressBox.add(addressTextLabel);
        addressBox.add(addressTF);
        addressBox.add(zipCodeLabel);
        addressBox.add(zipCodeTF);

        ratingTextLabel = new JLabel("Rating (1-5)");
        ratingTextLabel.setFont(customFont);
        ratingTF = new JTextField(2);
        ratingTF.setFont(customFont);
        ratingBox = new JPanel();
        ratingBox.setLayout(new BoxLayout(ratingBox, BoxLayout.X_AXIS));
        ratingBox.add(ratingTextLabel);
        ratingBox.add(ratingTF);

        categoriesTextLabel = new JLabel("Categories");
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
        categoriesBox.setLayout(new BoxLayout(categoriesBox, BoxLayout.Y_AXIS));
        categoriesBox.add(categoriesTextLabel);
        categoriesBox.add(buttonPanel);

        descriptionBox = new JPanel();
        descriptionTextLabel = new JLabel("Description");
        descriptionTextLabel.setFont(customFont);
        // descriptionBox.setLayout(new BoxLayout(descriptionBox, BoxLayout.Y_AXIS));
        descriptionTA = new JTextArea(3, 30);
        descriptionTA.setFont(customFont);
        descriptionTA.setEditable(true);
        descriptionBox.add(descriptionTextLabel);
        descriptionJSP = new JScrollPane(descriptionTA);	
        descriptionBox.add(descriptionJSP);
        descriptionJSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        submitButton = new CuteButton(0,"Submit");
        submitBox = new JPanel();
        submitBox.setLayout(new BoxLayout(submitBox, BoxLayout.X_AXIS));
        submitBox.add(Box.createHorizontalGlue());
        submitBox.add(Box.createHorizontalGlue());
        submitBox.add(submitButton);
        
        //set backgrounds
        titleBox.setBackground(new Color(250,243,233));
        pictureBox.setBackground(new Color(250,243,233));
        addressBox.setBackground(new Color(250,243,233));
        ratingBox.setBackground(new Color(250,243,233));
        categoriesBox.setBackground(new Color(250,243,233));
        descriptionBox.setBackground(new Color(250,243,233));
        submitBox.setBackground(new Color(250,243,233));

        //overallGUI
        this.add(titleBox);
        this.add(pictureBox);
        this.add(addressBox);
        this.add(ratingBox);
        this.add(categoriesBox);
        this.add(descriptionBox);
        this.add(submitBox);

        setVisible(true);
    }
    
    private void makeTheActivity()
    {
    	submitButton.addActionListener(new ActionListener(){
    	
    		@Override
    		public void actionPerformed(ActionEvent e) {
		    	//what do i make the id of the activity
		    	activityID = -1;
		    	rating = Integer.parseInt(ratingTF.getText());
		    	address = addressTF.getText();
		    	zipCode = Integer.parseInt(zipCodeTF.getText());
		    	if (CreateActivity.this.img == null){
		    		System.out.println("fuck");
		    	}
		    	activityPic = CreateActivity.this.img;
		    	name = titleTF.getText();
		    	description = descriptionTA.getText();
		    	//add in categories by checking which checkbox is selected
		    	categories = new Vector<Integer>();
		    	for (int i=0; i < checkboxes.size(); i++)
		    	{
		    		if (checkboxes.get(i).isSelected())
		    		{
		    			categories.add(i);
		    		}
		    	}
		    	comments = new Vector<Comment>();
		    	//get creator id
		    	creatorID = mainUser.userID;
		    	Activity activityToAdd = new Activity(activityID, rating, zipCode, activityPic, name, comments, description, address, categories, creatorID);
		    	activityToAdd =  mainClient.createActivity(activityToAdd);

		    	//mainClient.user.pastactivites.add(activityToAdd);
		    	//mainClient.updateUser(mainClient.user);
		    
		    	//mainClient.user.pastactivites.addElement(activityToAdd);

		    	mainClient.login.mainPage.addPastActivity(activityToAdd);
		    	mainClient.updateUser(mainClient.user);

		    	//mp.addFeedActivity(activityToAdd);
		    	CreateActivity.this.dispose();
    		}
    	});
    	uploadPictureButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageLoadingActivity a = new ImageLoadingActivity(CreateActivity.this);
			}
		});
    	System.out.println("activity made");
    		
    }
    	
    	
    	
    	
    	
    
    
    
}
// bhav stark king of the north young wolf