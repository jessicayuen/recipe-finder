package team13.cmput301.recipefinder.resources;

import java.util.Comparator;

import team13.cmput301.recipefinder.model.Recipe;

/**
 * Comparator used to sort the recipe when sorted by author
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class AuthorCompare implements Comparator<Recipe> {

	@Override
	public int compare(Recipe lhs, Recipe rhs) {
		return lhs.getAuthor().toLowerCase().compareTo(rhs.getAuthor().toLowerCase());
	}

}
