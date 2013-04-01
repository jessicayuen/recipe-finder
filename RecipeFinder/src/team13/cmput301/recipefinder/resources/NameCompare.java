package team13.cmput301.recipefinder.resources;

import java.util.Comparator;

import team13.cmput301.recipefinder.model.Recipe;

public class NameCompare implements Comparator<Recipe> {

	@Override
	public int compare(Recipe lhs, Recipe rhs) {
		return lhs.getName().compareTo(rhs.getName());
	}

}
