package team13.cmput301.recipefinder.resources;

import java.util.Comparator;

import team13.cmput301.recipefinder.model.Recipe;

public class AuthorCompare implements Comparator<Recipe> {

	@Override
	public int compare(Recipe lhs, Recipe rhs) {
		return lhs.getAuthor().compareTo(rhs.getAuthor());
	}

}
