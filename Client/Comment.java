package Client;

import java.io.Serializable;

import javax.swing.ImageIcon;

//Simple Class for Each Comment

public class Comment implements Serializable{
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//What User Made the Comment
    //Profile Picture
    //Text of comment
	public ImageIcon image;
	public String username;
	public String des;
	
	public Comment(String username, String des, ImageIcon profpic){
		this.image = profpic;
		this.username = username;
		this.des = des;
	}
}
