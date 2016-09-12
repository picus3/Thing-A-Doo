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

public class CuteButton extends JButton{
	private static final long serialVersionUID = 1L;
	Image image = null;
	String text;
	boolean star;
	Image stari;
	
	public CuteButton(int i, String text) {
		super(text);
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
		star = false;
		this.setText(text);
		LookAndFeel previousLF = UIManager.getLookAndFeel();
		try {
			if (i == 0){
				stari = ImageIO.read(new File("Star.PNG"));
				image = ImageIO.read(new File("SalmonButton.png"));
			}
			else{
				stari = ImageIO.read(new File("Star.PNG"));
				image = ImageIO.read(new File("Assignment3Images/images/cards/cardBack_red.png"));
			}
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
		if (star == true){
			g.drawImage(stari, this.getWidth()-10, 0, 10, 10, null);
		}
		super.paintComponent(g);
	}
	
	protected void paintBorder(Graphics g) {
		//paint no border
	}
	
	public void staroff(){
		star = false;
		repaint();
	}
	
	public void staron(){
		star = true;
		repaint();
	}
}
