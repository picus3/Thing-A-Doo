package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import Client.Activity;
import Client.Group;
import Client.User;
import javafx.util.Pair;
import utility.Search;



///This will be where all requests are made for Data and then passed back
public class DataMain {
	private Connection con;
	
    public DataMain() {
		try  {
			new com.mysql.jdbc.Driver();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
    
    public void Connect() {
    	try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ThingADoo?user=root&password=shit");
		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    public void Stop() {
    	try{con.close();} catch (SQLException e) {e.printStackTrace();}
    }
    
    public String getPass(int userID) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ThingADoo.Users WHERE userID=?");
			ps.setInt(1, userID);
			ResultSet r = ps.executeQuery();
			if (!r.next()) return null;
			return r.getString(3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public User getUserFromName(String username) {
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ThingADoo.Users WHERE username=?");
			ps.setString(1, username);
			ResultSet r = ps.executeQuery();
			if (!r.next()) return null;
			String file = r.getString(7);
			return (User)getObject("./Users/info/" + file);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private Object getObject(String filepath) {
		InputStream file = null;
		InputStream buffer = null;
		 ObjectInput input = null;
		try {
			file = new FileInputStream(filepath);
			 buffer =  new BufferedInputStream(file);
		     input = new ObjectInputStream (buffer);
		     return input.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				file.close();
				buffer.close();
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	      
		return null;
	}
	
	public User getUser(int iD) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ThingADoo.Users WHERE userID=?");
			ps.setInt(1, iD);
			ResultSet r = ps.executeQuery();
			if (!r.next()) return null;
			String file = r.getString(7);
			return (User)getObject("./Users/info/" + file);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
    
    
    

    
	public Group getGroup(int iD) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ThingADoo.Groups WHERE groupId=?");
			ps.setInt(1, iD);
			ResultSet r = ps.executeQuery();
			if (!r.next()) return null;
			String file = r.getString(4);
			return (Group)getObject("./Groups/info/" + file);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Activity getActivity(int iD) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ThingADoo.Activity WHERE activityID=?");
			ps.setInt(1, iD);
			System.out.println("Got Activty in Datamain ID : " + iD);
			ResultSet r = ps.executeQuery();
			if (!r.next()) return null;
			String file = r.getString(7);
			return (Activity)getObject("./Activities/info/" + file);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveObject(String filepath, Object obj) {
		OutputStream file = null;
	      OutputStream buffer = null;
	      ObjectOutput output = null;

	      try {
	    	  file = new FileOutputStream(filepath);
		      buffer = new BufferedOutputStream(file);
			  output = new ObjectOutputStream(buffer);
			  output.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				output.close();
				buffer.close();
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	      
	
     }
	
	public int addUser(User user) {
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO Users (username, pword, fname, lname, admin) VALUES(?, ?, ?, ?, ? )");
			ps.setString(1, user.username);
			ps.setString(2, user.password);
			ps.setString(3, user.firstName);
			ps.setString(4, user.lastName);
			ps.setInt(5, user.Admin);
			ps.executeUpdate();
			PreparedStatement pstwo = con.prepareStatement("SELECT * FROM ThingADoo.Users WHERE username=?");
            pstwo.setString(1, user.username);
        	ResultSet r = pstwo.executeQuery();
        	r.next();
           int ID =	r.getInt(1);
            user.userID = ID;
			String file = new String(ID + ".ser");
			saveObject("./Users/info/" + file, user);
			PreparedStatement psthree = con.prepareStatement("UPDATE ThingADoo.Users SET serilizedfile=?   WHERE userID=?");
            psthree.setString(1, file);
            psthree.setInt(2, ID);
            psthree.executeUpdate();
            return ID;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	

	public void updateUser(User obj) {
		saveObject("./Users/info/" + obj.userID + ".ser", obj);
	}
	
	public void updateGroup(Group obj) {
		saveObject("./Groups/info/" + obj.ID + ".ser", obj);		
	}
	public void updateActivity(Activity obj) {
		saveObject("./Activities/info/" + obj.ID + ".ser", obj);		
		
	}
	
	
	
	public int addGroup(Group obj) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("INSERT INTO Groups (groupname) VALUES(?)");
			ps.setString(1, obj.name);
			ps.executeUpdate();
			PreparedStatement pstwo = con.prepareStatement("SELECT * FROM ThingADoo.Groups WHERE groupname=?");
	        pstwo.setString(1, obj.name);
	    	ResultSet r = pstwo.executeQuery();
	    	r.next();
	        int ID = r.getInt(1);
	        obj.ID = ID;
			String file = new String(ID + ".ser");
			saveObject("./Groups/info/" + file, obj);
			PreparedStatement psthree = con.prepareStatement("UPDATE ThingADoo.Groups SET serilizedfile=?   WHERE groupID=?");
	        psthree.setString(1, file);
	        psthree.setInt(2, ID);
	        psthree.executeUpdate();
	        return ID;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
      	
	}
	
	public int addActivity(Activity obj) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("INSERT INTO Activity (activityname, location, categories, keywordsfile, creator) VALUES(?, ?, ?, ?, ?)");
			ps.setString(1, obj.name);
			ps.setInt(2, obj.zipcode);
			String categories = parseVector(obj.categories);
			ps.setString(3, categories);
			ps.setString(4, new String(obj.name + " " + obj.description + " " + obj.address));
			ps.setInt(5, obj.creator);
			ps.executeUpdate();
			PreparedStatement pstwo = con.prepareStatement("SELECT * FROM ThingADoo.Activity WHERE activityname=?");
	        pstwo.setString(1, obj.name);
	    	ResultSet r = pstwo.executeQuery();
	    	r.next();
	        int ID = r.getInt(1);
	        obj.ID = ID;
			String file = new String(ID + ".ser");
			saveObject("./Activities/info/" + file, obj);
			PreparedStatement psthree = con.prepareStatement("UPDATE ThingADoo.Activity SET serilizedfile=?   WHERE activityID=?");
	        psthree.setString(1, file);
	        psthree.setInt(2, ID);
	        psthree.executeUpdate();
	        return ID;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
		
	}
	
	private String parseVector(Vector<Integer> categories) {
		String result = new String(categories.get(0).toString());
		for (int i = 1; i < categories.size(); i++) {
			result = result + " " +  new String(categories.get(i).toString());
		}
		return result;
	}
	
	public Vector<Integer> categorySearch(Vector obj) {
		Vector<Integer> matches = new Vector<Integer>();
		try {
		  for (int i = 0; i < obj.size(); i++) {
			PreparedStatement ps;
			ps = con.prepareStatement("SELECT * FROM Activity WHERE categories LIKE ?");
			ps.setString(1, new String("%" + ((Integer)obj.get(i)).toString() + "%"));
			System.out.println(ps.toString());
			ResultSet r = ps.executeQuery();
			while(r.next()) {
				System.out.println("HI");
				matches.addElement(new Integer(r.getInt(1)));
			}
		  }
		  return matches;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Integer> keywordSerch(Vector obj) {
		Vector<Integer> matches = new Vector<Integer>();
		try {
		  for (int i = 0; i < obj.size(); i++) {
			PreparedStatement ps;
			ps = con.prepareStatement("SELECT * FROM Activity WHERE keywordsfile LIKE ?");
			ps.setString(1, new String ( "%" + (String)obj.get(i) + "%"));
			ResultSet r = ps.executeQuery();
			while(r.next()) {
				matches.addElement(new Integer(r.getInt(1)));
			}
		  }
		  return matches;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Pair<Integer, Integer>> locationSearch(Integer obj) {
		Vector<Pair<Integer, Integer>> matches = new Vector<Pair<Integer, Integer>>();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ThingADoo.Activity");
			ResultSet r = ps.executeQuery();
			while(r.next()) {
				int ID = r.getInt(1);
				int zip = r.getInt(3);
				matches.addElement(new Pair<Integer, Integer>(new Integer(ID), new Integer(zip)));
			}
			
			return matches;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void delete(Object obj) {
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE ThingADoo.Activity SET serilizedfile=?   WHERE activityID=?");
			 ps.setString(1, "9999.ser");
			
		    ps.setInt(2, ((Activity)obj).ID);
			 ps.executeUpdate();	
			 ((Activity)obj).ID = 9999;
				String file = new String("9999.ser");
				saveObject("./Groups/info/" + file, obj);
	     } catch (SQLException e) {
	    	 e.printStackTrace();
	     }
		
	}
	
////////////////////////////////////
/////NEED TO IMPLEMENT/////////////
//////////////////////////////////
	
	//Going to return 


	
}
