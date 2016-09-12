package Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
class imageLoad extends Canvas{
	Image img;
	public imageLoad(Image img){
		this.img = img;
	}
	public void paint (Graphics g){
		if (img != null){
			g.drawImage(img, 400, 100, 200, 200, this);
		}
	}
	public void setImage (Image im){
		img = im;
	}
}

public class ImagesLoading implements ActionListener{
	public Image mainImage;
	NewUser user;
	JFrame frame;
	Label infoLabel;
	Button browseButton;
	JButton confirm;
	Image Image1;
	imageLoad Canvas1;
	FileDialog fd = new FileDialog(frame,"Open", FileDialog.LOAD);
	
	public ImagesLoading(NewUser user){
		this.user = user;
		initVars();
	}
	
	void initVars (){
		confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ImagesLoading.this.user.profPic = mainImage;
				ImagesLoading.this.frame.setVisible(false);
			}
			
		});
		frame = new JFrame ("Thing-a-doo");
		browseButton = new Button("Browse");
		infoLabel = new Label("Choose Your Image!");
		frame.setSize(400,400);
		frame.setLocation(200,200);
		frame.setBackground(Color.lightGray);
		frame.setLayout(new FlowLayout());
		frame.add(infoLabel);
		frame.add(browseButton);
		frame.add(confirm);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		browseButton.addActionListener(this);
		Canvas1 = new imageLoad(null);
		Canvas1.setSize(1000,1000);
		frame.add(Canvas1);
		frame.show();
	}
	
	void imageload (){
		fd.show();
		if(fd.getFile() == null){
			infoLabel.setText("Please Try Again");
		}
		else{
			String d = (fd.getDirectory() + fd.getFile());
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image1 = toolkit.getImage(d);
			Canvas1.setImage(Image1);
			mainImage = Image1;
			Canvas1.repaint();
		}
	}
	public void windowClosing(WindowEvent e){}
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}

	public void actionPerformed(ActionEvent event){
		Button check = (Button)event.getSource();
		if(check == browseButton){
			imageload();
		}
	}
}