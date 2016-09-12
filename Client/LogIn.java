package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

///GUI////
//This will handle all of the Logging of the user
public class LogIn extends JFrame {
	private static final long serialVersionUID = 1L;
	
	JTextField usernameField;
	JPasswordField passwordField;
	CuteButton thingadooButton, createAccountButton;
	JLabel usernameLabel, passwordLabel, login;
	JPanel usernamePane, passwordPane, acctPane, loginPane, thingPane;
	public MainClient client;
	public MainPage mainPage;
	final Color color = new Color(218,103,103);

	public LogIn(MainClient c){
		super("thingadoo");
		
		// font
		InputStream is;
		Font font = null;
		try {
			is = new FileInputStream("Pacifico.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 25f);

		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    ge.registerFont(font);
	    
	    
		client = c;
		setSize(350,250);
		initVariables();
		this.setBackground(color);
		setLocation(200,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(5, 1));
		
		loginPane.add(login);
				
		usernamePane.add(usernameLabel);
		usernameField.setPreferredSize( new Dimension( 200, 30 ) );
        Font newFont = font.deriveFont(Font.PLAIN, 16);
		usernameField.setFont(newFont);
		usernamePane.add(usernameField);

		passwordPane.add(passwordLabel);
		passwordField.setPreferredSize( new Dimension( 200, 30 ) );
		passwordPane.add(passwordField);
		
		thingadooButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
                System.out.println("Getting a User:" + System.currentTimeMillis());
				User u = client.Login(usernameField.getText(), passwordField.getText());
				 if ( u == null) {
					 JOptionPane.showMessageDialog(LogIn.this, "Username or Password Incorect");
					 return;
				 }
				System.out.println("Got a User" + System.currentTimeMillis());
				 mainPage = new MainPage(u, client);
				 client.mainPage = mainPage;
				 System.out.println("Made Main Page" + System.currentTimeMillis());
				System.out.println(u.firstName + "Logged In!");
				LogIn.this.setVisible(false);
			}
			
			
		});
		thingPane.add(thingadooButton);
		
		acctPane.setLayout(new BoxLayout(acctPane, BoxLayout.X_AXIS));
		acctPane.add(Box.createHorizontalGlue());
		acctPane.add(createAccountButton);
		acctPane.add(Box.createHorizontalGlue());
		createAccountButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int x =0;
				x = x+1;
				LogIn.this.setVisible(false);
				NewUser u = new NewUser(client, null, LogIn.this);
				u.setVisible(true);
			}
			
		});
		acctPane.add(Box.createHorizontalGlue());
		acctPane.add(Box.createHorizontalGlue());
		acctPane.add(Box.createHorizontalGlue());
		acctPane.add(Box.createHorizontalGlue());

		

		
		add(loginPane);
		add(usernamePane);
		add(passwordPane);
		add(thingPane);
		add(acctPane);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void initVariables(){
		InputStream is;
        Font font = null;
        try {
            is = new FileInputStream("Pacifico.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 25f);

        } catch (FontFormatException | IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		thingadooButton = new CuteButton(0,"Thing-a-doo!");
		createAccountButton = new CuteButton(0,"Create Account");		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(font);
		passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(font);
		login = new JLabel("Login!");
		loginPane = new JPanel();
		loginPane.setBackground(color);
		usernamePane = new JPanel();
		usernamePane.setBackground(color);
		passwordPane = new JPanel();
		passwordPane.setBackground(color);
		thingPane = new JPanel();
		thingPane.setBackground(color);
		acctPane = new JPanel();
		acctPane.setBackground(color);
	}
	
	protected void paintComponent(Graphics g) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image border = toolkit.getImage("borderp.png");
		g.drawImage(border, 0, 0, 200, 100, this);
	}
}