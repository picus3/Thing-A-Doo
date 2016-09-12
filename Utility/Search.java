package utility;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import Client.Activity;
import javafx.util.Pair;

//This will take a user in and perform a search of the database
//It will have multiple methods to perform different types of searches

/*
 * Static class
 */
public class Search {
	
	public static Vector<Activity> sortResultsByKeywordHits(Vector<Activity> searchResult) {
		
		TreeMap<Activity, Integer> keywordHits = new TreeMap<Activity, Integer>();
		
		for(Activity a : searchResult) {
			if(keywordHits.containsKey(a)) {
				keywordHits.replace(a, keywordHits.get(a) + 1);
			} else {
				keywordHits.put(a, 1);
			}
		}
		
		searchResult.sort(new Comparator<Activity>() {

			public int compare(Activity a1, Activity a2) {
				
				int hits1 = keywordHits.get(a1);
				int hits2 = keywordHits.get(a2);
				
				if(hits1 < hits2) {
					return -1;
				}
				
				if(hits1 > hits2) {
					return 1;
				}
				
				return 0;
			}
		});
		
		removeSearchResultDuplicates(searchResult);
		
		return searchResult;
	}
	
	public static Vector<Activity> sortResultsByLocation(Vector<Activity> searchResult, int zipCode) {
		
		searchResult.sort(new Comparator<Activity>() {

			public int compare(Activity a1, Activity a2) {
				double d1 = 0;
				double d2 = 0;
				try {
					d1 = calculateZipDistance.getDistance(a1.zipcode, zipCode);
					d2 = calculateZipDistance.getDistance(a2.zipcode, zipCode);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(d1 < d2) {
					return -1;
				}
				if(d1 == d2) {
					return 0;
				}
				return 1;
			}
		});

		removeSearchResultDuplicates(searchResult);
		return searchResult;
	}
	
	public static Vector<Activity> sortResultsByRating(Vector<Activity> searchResult) {
		
		searchResult.sort(new Comparator<Activity>() {

			@Override
			public int compare(Activity a1, Activity a2) {
				double d1 = a1.rating;
				double d2 = a2.rating;
				
				if(d1 > d2) {
					return -1;
				}
				if(d1 == d2) {
					return 0;
				}
				return 1;
			}
		});
		removeSearchResultDuplicates(searchResult);
		return searchResult;
	}
	
	public static void removeSearchResultDuplicates(Vector<Activity> searchResult) {
		if (searchResult.size() ==0) return;
		Activity prev = searchResult.get(0);
		for (int i = 1; i < searchResult.size(); i++) {
			if (prev.ID == searchResult.get(i).ID) {
				searchResult.remove(i);
				i--;
			} else {
				prev = searchResult.get(i);
			}
		}
		
	}
    
	//THIS WILL BE PASSED A VECTOR WITH A PAIR THE FIRST IS THE ACTIVITY ID, THE SECOND IS A ZIPCODE 
	//THAT ACTIVITY. THIS SHOULD RETURN THE ACTITIES ID'S SORTED SO THAT THE CLOSETS ONE IS THE FIRST ONE
	//IN 
	public static Vector<Integer> searhcLocation(Vector<Pair<Integer, Integer>> matches, int UserZip) {
		matches.sort(new Comparator<Pair<Integer, Integer>>() {

			public int compare(Pair<Integer, Integer> a1, Pair<Integer, Integer> a2) {
				double d1 = 0;
				double d2 = 0;
				try {
					d1 = calculateZipDistance.getDistance(a1.getValue(), UserZip);
					d2 = calculateZipDistance.getDistance(a2.getValue(), UserZip);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(d1 < d2) {
					return -1;
				}
				if(d1 == d2) {
					return 0;
				}
				return 1;
			}
		});
		Vector<Integer> searchResult = new Vector<>();
		for (int i = 0; i < matches.size(); i++) {
			searchResult.addElement(matches.get(i).getKey());
		}
		return searchResult;
	}
	
}
