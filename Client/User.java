package Client;

import javax.swing.*;

import jdk.internal.util.xml.impl.Pair;

import java.awt.Image;
import java.io.Serializable;
import java.util.Vector;

//This class will contain all the info of a User
/* Details of a user:
First Name
Last Name
Age
User ID
Location
Interests as Strings
Past Activities
Array of groups
Array of desired activities */

public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String username;
    public String firstName;
    public String lastName;
   
    public int age;
    public int userID;
    public int zipCode;
    public Vector<Activity> activities;
    public Vector<Activity> pastactivites;
    public Vector<Integer> interests;
    public Vector<Group> groups;
    public Vector<Conversation> messages;
    public ImageIcon profPic;
    public int lastlogin; //will be used to help figure out which messages are new
    public Boolean newMessages; //To Signify a new Message
    public String password;
    public int Admin; // 1 is true zero is false

    public User(String username, String fname, String lname, int ageParameter, int uID, int zip, Vector<Integer> interestedCategories, Vector<Group> groups, String password, int Admin, Image pic) {
        profPic = new ImageIcon(pic);
    	firstName = fname;
        lastName = lname;
        this.password = password;
        newMessages = new Boolean(false);
        this.Admin = Admin;
        this.username = username;
        age = ageParameter;
        userID = uID;
        zipCode = zip;
        interests = interestedCategories;
        messages = new Vector<Conversation>();
        activities = new Vector<Activity>();
        pastactivites =  new Vector<Activity>();
        if (groups == null) {
            this.groups = new Vector<Group>();
        }
        else  this.groups = groups;
        this.activities = new Vector<Activity>();
        //addFakeActivities();
    }

    //Will be Used for the Server
    //Pass this when fetching a User
    public User(int ID) {
         userID = ID;
	}

    //For the Server Use
    public User(String name, int i) {
        this.username = name;
        this.userID = i;
    }
    
    public void removeToDo(Activity temp){
    	for (int i = 0; i < activities.size(); i++){
    		if (activities.get(i) == temp){
    			activities.remove(i);
    			System.out.println("removing one");
    		}
    	}
    }
    
    public void adminRemoveActivity(Activity a){
    	for (int i = 0; i < activities.size(); i++){
    		if (activities.get(i) == a){
    			activities.remove(i);
    		}
    	}
    	for (int i = 0; i < pastactivites.size(); i++){
    		if (pastactivites.get(i) == a){
    			pastactivites.remove(i);
    		}
    	}
    }

    //getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Vector<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Vector<Activity> activities) {
        this.activities = activities;
    }

    public Vector<Group> getGroups() {
        return groups;
    }

    public void setGroups(Vector<Group> groups) {
        this.groups = groups;
    }
    
    public void addGroup(Group g){
    	this.groups.add(g);
    }

    public Vector<Integer> getInterests() {
        return interests;
    }

    public void setInterests(Vector<Integer> interests) {
        this.interests = interests;
    }

    public void setProfPic(ImageIcon ii) {
        profPic = ii;
    }
    
    public ImageIcon getProfPic() {
    	return profPic;
    }
    
    private void addFakeActivities() {
    		
	    	Activity temp = new Activity(117, 5, 90007, null, "Gaming with homies", null, "Its ok when you're single", "no address", null, 1);
	    	Activity temp2 = new Activity(88, 4, 90089, null, "FOOOTBALLLL", null, "Do it all the time", "850 W 37th St, Los Angeles, CA", null, 2);
	    	Activity temp3= new Activity(111, 5, 90210, null, "Celebrity Site Seeing", null, "Find celebrities, take a selfie with them, then insta it", "Beverly Hills", null, 3);
	    	activities.add(temp);
	    	activities.add(temp2);
	    	activities.add(temp3);
    }
    
}



