package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class GroupButton extends JButton {
	public int groupID;
	Image image = null;
	String text;
	Image stari;
	
	public GroupButton() {
	}

	public GroupButton(String text, int id) {
		super(text);
		groupID = id;
		setOpaque(false);
		InputStream is;
        Font customFont = null;
        Font newFont = null;
        try {
            is = new FileInputStream("Pacifico.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 25f);
            newFont = customFont.deriveFont(Font.PLAIN, 12);

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (FontFormatException e1) {
        	e1.printStackTrace();
        }
         
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);
        ge.registerFont(newFont);
		this.text = text;
		this.setText(text);
		try {
			image = ImageIO.read(new File("SalmonButton.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setBorderPainted(false);
		this.setFont(newFont);
		
		setBackground(new Color(0, 0, 0, 0));
		setForeground(Color.WHITE);		
	}
	
	protected void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		super.paintComponent(g);
	}
}
