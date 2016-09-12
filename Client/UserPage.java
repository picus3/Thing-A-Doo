package Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import Server.Interests;
import utility.Photo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

//GUI//
//This will be used to actually display the User Page
public class UserPage extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JPanel rightTopPanel, userPanel, picPanel, infoPanel, pastActivitiesPanel;
    private CuteButton createActivityButton, messagesButton, editButton;
    private JLabel ageLabel, locationLabel, interestLabel, nameLabel;
    private JPanel groupsye;
    private Border border, loweredbevel, raisedbevel;
    private User u;
	final Color color = new Color(250,243,233);

    
    public UserPage(User u) {
    	this.u = u;
    	this.setSize(300,500);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
    	border = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
    	setUpRightPanel();   
    	this.setBackground(color);	
    }
    
    private void rightVariables(){
    	rightTopPanel = new JPanel();

		userPanel = new JPanel();
		picPanel = new JPanel();

		MakePic a = new MakePic(u.profPic.getImage());
		a.setPreferredSize(new Dimension(100,100));
		a.setBorder(border);

		picPanel.add(a);

		pastActivitiesPanel = new JPanel();
		pastActivitiesPanel.setLayout(new BoxLayout(pastActivitiesPanel, BoxLayout.Y_AXIS));
		JPanel jp = new JPanel();
		JLabel paLabel = new JLabel("Past Activites:");
    	jp.add(paLabel);
    	pastActivitiesPanel.add(jp);
    	JPanel babyActivityPanel = new JPanel();
    	babyActivityPanel.setLayout(new BoxLayout(babyActivityPanel, BoxLayout.Y_AXIS));
    	
    	Vector<Activity> feed = u.getActivities();

		for (int i = 0; i < feed.size(); i++) {
			Activity temp = feed.get(i);
			JPanel temppanel = getCuteActivityBox(temp);
			babyActivityPanel.add(temppanel);
    	}

		pastActivitiesPanel.setBorder(border);
		pastActivitiesPanel.add(babyActivityPanel);

		infoPanel = new JPanel();
		nameLabel = new JLabel(u.getFirstName() + " " + u.getLastName());
		ageLabel = new JLabel("Age: " + u.getAge());
		locationLabel = new JLabel("Location: " + u.getZipCode());
    	String interestString = "";
    	Vector<Integer> temp = u.getInterests();
    	System.out.println(temp);
    	for (int i = 0; i < temp.size(); i++){
    		if (i == 0){
				interestString = interestString + Interests.getInterestString(temp.get(i));
			} else {
				interestString = interestString + ", " + Interests.getInterestString(temp.get(i));
			}
    	}
    	interestLabel = new JLabel("Interests: " + interestString);
    	
 
    }
    
    private void setUpRightPanel(){
    	rightVariables();

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


		userPanel.setLayout(new GridLayout(2, 1));
		userPanel.add(picPanel);
		userPanel.add(infoPanel);

		//set Backgrounds
		picPanel.setBackground(new Color(250,243,233));
		infoPanel.setBackground(new Color(250,243,233));
		userPanel.setBackground(new Color(250,243,233));
		pastActivitiesPanel.setBackground(new Color(250,243,233));
		
		add(userPanel, BorderLayout.CENTER);
		add(pastActivitiesPanel, BorderLayout.SOUTH);
	}

    /*public static void main (String [] agrs) throws IOException{
    	 Vector<Comment> ugh = new Vector<Comment>();
         for (int i = 0; i < 5; i++) {
             ugh.add(new Comment("sam picus", "this activity page is so cool", null));
         }
         Vector<Integer> tempvec = new Vector<Integer>();
         tempvec.add(2);
         tempvec.add(3);
         tempvec.add(1);
         Vector<Group> groupvec = new Vector<Group>();
         Image n = null;
         User temp = new User("bob@f.edu", "Ankush", "Prasad", 21, 1234, 789000, tempvec, groupvec, "hi", 0, ImageIO.read(new File("ankush.jpg") ));
    
    	UserPage a = new UserPage(temp);
    	a.setVisible(true);
    	
    }*/
    
	private JPanel getCuteActivityBox(Activity temp) {
		JPanel temppanel = new JPanel();
		temppanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		temppanel.setPreferredSize(new Dimension(300, 60));
		temppanel.setBorder(border);
		
		
		MakePic a = new MakePic(temp.photo.getImage());
		a.setPreferredSize(new Dimension(40,40));
		a.setVisible(true);
		a.setBorder(border);
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
}
