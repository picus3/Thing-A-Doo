package utility;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

//This is a photo processing class
//Pass in photo and it it will convert it to proper hieght and size to be saved in Client 
//and Be used throughout the program.
public class Photo {
	
	private Image image;
	
	public Photo(ImageIcon ii) {
		image = ii.getImage();
	}
	
	public Photo(Image image) {
		this.image = image;
	}

    public Photo(String filePath) {
        image = null;
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Image getPhotoWithDimension(int width, int height) {
    	
    	return image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }
    // potato
    public Image getPhotoWithDimension(Dimension d) {
    	return image.getScaledInstance((int)d.getWidth(), (int)d.getHeight(), Image.SCALE_DEFAULT);
    }
    
    public JButton getPhotoButtonOfSize(int width, int height) {
    	JButton jb = new JButton();
    	jb.setSize(width, height);
    	jb.setIcon(new ImageIcon(getPhotoWithDimension(width, height)));
    	
    	return jb;
    }
    
    public JButton getPhotoButtonOfSize(Dimension d) {
    	JButton jb = new JButton();
    	jb.setSize(d);
    	jb.setIcon(new ImageIcon(getPhotoWithDimension(d)));
    	return jb;
    }

}
