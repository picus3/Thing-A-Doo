package Client;

import javax.swing.*;

import utility.FontLibrary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

//GUI
//This will create a GUI in order for Creating A group
public class CreateGroup extends JFrame {
	private static final long serialVersionUID = 1L;
	JLabel title, gName, gDes;
	JTextField nameField;
	JTextArea desField;
	JButton submit, addMoreUsers;
	JPanel top, bottom, titlePanel, addPanel;
	JScrollPane userFields;
	Vector<JTextField> nameHolder;
	Vector<JTextField> emailHolder;
	private MainClient client;
	final Color color = new Color(250,243,233);
	private Font font = FontLibrary.getTrueTypeFont(28);
	private Font customFont = FontLibrary.getTrueTypeFont(14);


	public CreateGroup(MainClient client) {
		super("thingadoo");
		setSize(500, 400);
		setLocation(100,50);
		initVariables();
		makePanels();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		addEvents();

		font = font.deriveFont(Font.BOLD);
		this.client = client;

		titlePanel.setBackground(new Color(250,243,233));
		top.setBackground(new Color(250,243,233));
		addPanel.setBackground(new Color(250,243,233));
		//this.setBackground(new Color(250,243,233));

		this.add(titlePanel);
		this.add(top);
		this.add(userFields);
		this.add(addPanel);
		this.setBackground(color);
		
		//create font
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        ge.registerFont(customFont);		
	}


	
	public void initVariables(){
		title = new JLabel("Create Group");
		title.setFont(font);
		gName = new JLabel("Group Name:");
		gName.setFont(customFont);
		gDes = new JLabel("Group Description:");
		gDes.setFont(customFont);
		addMoreUsers = new CuteButton(0, "Add More Users");
		submit = new CuteButton(0, "Create Group!");
		nameField = new JTextField(10);
		nameField.setFont(customFont);
		desField = new JTextArea(2,10);
		desField.setFont(customFont);

		top = new JPanel();
		bottom = new JPanel();
		titlePanel = new JPanel();
		addPanel = new JPanel();

		//trying to set bg of jsp
		bottom.setBackground(new Color(250,243,233));
		userFields = new JScrollPane(bottom);
		userFields.getViewport().setOpaque(false);
		userFields.setBorder(null);
		userFields.setBackground(new Color(250,243,233));

		userFields.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		userFields.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		nameHolder = new Vector<>();
		emailHolder = new Vector<>();
	}

	public void makePanels(){
		titlePanel.add(title);
		addPanel.add(addMoreUsers);
		addPanel.add(submit);
		
		Box firstBox = Box.createHorizontalBox();
		firstBox.add(Box.createHorizontalGlue());
		firstBox.add(gName);
		firstBox.add(nameField);
		firstBox.add(Box.createHorizontalGlue());
		Box secondBox = Box.createHorizontalBox();
		secondBox.add(Box.createHorizontalGlue());
		secondBox.add(gDes);
		secondBox.add(desField);
		secondBox.add(Box.createHorizontalGlue());
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		top.add(firstBox);
		top.add(secondBox);

		bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

		//create initial 4 components
		for (int i = 0; i < 4; i++) {
			//name fields
			JPanel initialComponent = new JPanel();
			initialComponent.setBackground(new Color(250,243,233));
			JLabel temp =  new JLabel("Name:");
			temp.setFont(customFont);
			initialComponent.add(temp);
			JTextField nameInput = new JTextField(8);
			nameInput.setFont(customFont);
			nameHolder.add(nameInput);
			initialComponent.add(nameInput);

			//email fields
			JLabel newTemp = new JLabel("Email:");
			newTemp.setFont(customFont);
			initialComponent.add(newTemp);
			JTextField emailInput = new JTextField(15);
			emailInput.setFont(customFont);
			emailHolder.add(emailInput);
			initialComponent.add(emailInput);

			bottom.add(initialComponent);


		}

	}

	private void addEvents() {
		addMoreUsers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel initialComponent = new JPanel();
				initialComponent.setBackground(color);
				initialComponent.setBackground(new Color(250,243,233));
				JLabel name = new JLabel("Name:");
				name.setFont(customFont);
				initialComponent.add(name);
				JTextField nameInput = new JTextField(8);
				nameInput.setFont(customFont);
				nameHolder.add(nameInput);
				initialComponent.add(nameInput);

				//email fields
				JLabel email = new JLabel("Email:");
				email.setFont(customFont);
				initialComponent.add(email);
				JTextField emailInput = new JTextField(15);
				emailInput.setFont(customFont);
				emailHolder.add(emailInput);
				initialComponent.add(emailInput);

				bottom.add(initialComponent);

				bottom.revalidate();
				bottom.repaint();
			}
		});

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<User> users = new Vector<User>(); 
				for(int i = 0; i < emailHolder.size(); i++) {
					if (!emailHolder.get(i).getText().equals("")) {
						User u = client.getUserFromUsername(emailHolder.get(i).getText());
						if (u == null) {
							JOptionPane.showMessageDialog(CreateGroup.this, "Sorry " + emailHolder.get(i).getText() + " Doesn't Exist");
						    return;
						}
						if (u.userID == client.user.userID) {
							JOptionPane.showMessageDialog(CreateGroup.this, "Silly you can't add yourself to a group twice!");
                            return;
						}
						users.addElement(u);
					}
				}
				users.addElement(client.user);
				Group g = client.createGroup(new Group(-1, users, nameField.getText()));
				System.out.println(g.toString());
				for (int i = 0; i < users.size(); i++) {
					users.get(i).groups.add(g);
					client.updateUser(users.get(i));
				}
				client.user = client.getUser(client.user.userID);
               client.login.mainPage.addGroup(g);
				CreateGroup.this.dispose();
			}
		});
	}

	public void createGroup() {
		Boolean allFieldsFilledOut = true;
		for (int i = 0; i < nameHolder.size(); i++) {
			//makes sure user has filled in necessary information to create a group
			if (nameHolder.get(i).getText().equals("") || (emailHolder.get(i).getText().equals(""))) {
				allFieldsFilledOut = false;
			}
		}

		//create alert that more has to be done
		if (allFieldsFilledOut == false) {
			JOptionPane.showMessageDialog(null, "Fill out all name and email fields", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		//create group
		else {
			Vector<User> groupMembers = new Vector<>();
			for (int i = 0; i < emailHolder.size(); i++) {
				//this gets us the user from the email
				User temp = client.getUserFromUsername(emailHolder.get(i).getText());
				groupMembers.add(temp);
			}

			//TODO: fix this function so that it works properly
			Group g = new Group(0, groupMembers, nameField.getText());
			client.createGroup(g);
		}
	}


}
