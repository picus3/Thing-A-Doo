package Server;

public class Interests {
	
	public static final String[] CATEGORIES = {"Active", "Chillin", "Extreme",
			"Learning", "Movies", "Museums", "Outdoors", "Romantic"};
	
	
	public static String getInterestString(int i){
		
		// in case some dingus tries using an invalid index
		if(i < 0 || i >= CATEGORIES.length) {
			return null;
		}
		return CATEGORIES[i];
	}	
}
