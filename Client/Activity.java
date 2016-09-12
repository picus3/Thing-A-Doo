package Client;

import javax.swing.*;

import java.awt.Image;
import java.io.Serializable;
import java.util.Vector;

///This Will contain all of the basic data for an Activity

public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;
	public int rating;
	public int zipcode;
	public String description;
	public int ID;
	public ImageIcon photo;
	public String name;
	public Vector<Comment> comments;
	public Vector<Integer> categories;
	public String address;
	public int creator;

	public Activity(int ID, int rating, int zipcode, ImageIcon photo, String n, Vector<Comment> comments, String des, String address, Vector<Integer> categories, int CreatorId) {
		this.ID = ID;
		this.categories = categories;
		this.rating = rating;
		this.zipcode = zipcode;
		this.creator = CreatorId;
		this.photo = photo;
		this.name = n;
		this.comments = comments;
		this.description = des;
		this.address = address;
	}
	//Will be Used for the Server when Fetching an Activity
	public Activity(int ID) {
		this.ID = ID;
	}
	
	public void setComment(Vector<Comment> c){
		comments = c;
	}

}
