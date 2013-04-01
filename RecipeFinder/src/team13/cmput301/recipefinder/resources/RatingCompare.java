package team13.cmput301.recipefinder.resources;

import java.util.Comparator;

import team13.cmput301.recipefinder.model.Recipe;

/**
 * Comparator used when sorting recipes based on recipe rating
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class RatingCompare implements Comparator<Recipe> {
	@Override
	public int compare(Recipe o1, Recipe o2) {
		return (o1.getRating()>o2.getRating() ? 1 : (o1.getRating()==o2.getRating() ? 0 : -1));
	}
}
