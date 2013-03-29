/**
 * Responsible for interactions with the database - including
 * the addition, removal, retrieving of recipes.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */

package team13.cmput301.recipefinder.sqlitedatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import team13.cmput301.recipefinder.model.Photo;
import team13.cmput301.recipefinder.model.Recipe;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RecipeDataSource {

	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;

	/* Recipe table columns */
	private String[] allColumns = { 
			SQLiteHelper.RECIPE_COL_ID,
			SQLiteHelper.RECIPE_COL_NAME,
			SQLiteHelper.RECIPE_COL_DESC,
			SQLiteHelper.RECIPE_COL_AUTHOR, 		
			SQLiteHelper.RECIPE_COL_FAVE,		
			SQLiteHelper.RECIPE_COL_RATING,
			SQLiteHelper.RECIPE_COL_DATE,
			SQLiteHelper.RECIPE_COL_UUID };

	/* Instruction table columns */
	private String[] instrColumns = { 
			SQLiteHelper.INSTR_COL_ID,
			SQLiteHelper.INSTR_COL_INSTR };

	/* Ingredient table columns */
	private String[] ingredColumns = { 
			SQLiteHelper.INGRED_COL_ID,
			SQLiteHelper.INGRED_COL_INGRED };

	/* Photo table columns */
	private String[] photoColumns = { 
			SQLiteHelper.PHOTO_COL_ID,
			SQLiteHelper.PHOTO_COL_AUTHOR, 
			SQLiteHelper.PHOTO_COL_PHOTO,
			SQLiteHelper.PHOTO_COL_DATE,};

	/**
	 * Constructor - instantiates SQLiteHelper to retrieve table definitions
	 * @param context The activity context
	 */
	public RecipeDataSource(Context context) {
		dbHelper = new SQLiteHelper(context);
	}

	/**
	 * Opens a connection with the database
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * Closes connection with the database
	 */
	public void close() {
		dbHelper.close();
	}

	/**
	 * Inserts a recipe into the the database
	 * @param recipe The recipe to be inserted
	 */
	public void insertRecipe(Recipe recipe) {
		ContentValues values; 

		values = insertRecipeValues(recipe);
		long id = database.insert(SQLiteHelper.TABLE_RECIPE, null, values);
		recipe.setSqlID(id);

		List<String> instructionsList = recipe.getInstructions();
		values = insertInstructionValues(instructionsList, (int) id);
		if (instructionsList.size() > 0)
			database.insert(SQLiteHelper.TABLE_INSTR, null, values);

		List<String> ingredientsList = recipe.getIngredients();
		values = insertIngredientValues(ingredientsList, (int) id);
		if (ingredientsList.size() > 0)
			database.insert(SQLiteHelper.TABLE_INGRED, null, values);

		List<Photo> photosList = recipe.getPhotos();
		values = insertPhotoValues(photosList, (int) id);
		if (photosList.size() > 0)
			database.insert(SQLiteHelper.TABLE_PHOTO, null, values);
	}

	/**
	 * Replaces a existing row with the new Recipe
	 * @param recipe The recipe to replace with
	 * @param row The row containing the recipe to replace
	 */
	public void replaceRecipe(Recipe recipe, int row) {
		ContentValues values; 

		values = insertRecipeValues(recipe);
		database.update(SQLiteHelper.TABLE_RECIPE, values, new String(
				SQLiteHelper.RECIPE_COL_ID + " = " + row), null);

		List<String> instructionsList = recipe.getInstructions();
		values = insertInstructionValues(instructionsList, row);
		database.delete(SQLiteHelper.TABLE_INSTR, 
				SQLiteHelper.COL_USER_REFERENCE + " = " + row, null);
		if (instructionsList.size() > 0)
			database.insert(SQLiteHelper.TABLE_INSTR, null, values);
		
		List<String> ingredientsList = recipe.getIngredients();
		values = insertIngredientValues(ingredientsList, row);
		database.delete(SQLiteHelper.TABLE_INGRED, 
				SQLiteHelper.COL_USER_REFERENCE + " = " + row, null);
		if (ingredientsList.size() > 0)
			database.insert(SQLiteHelper.TABLE_INGRED, null, values);

		List<Photo> photosList = recipe.getPhotos();
		values = insertPhotoValues(photosList, row);
		database.delete(SQLiteHelper.TABLE_PHOTO, 
				SQLiteHelper.COL_USER_REFERENCE + " = " + row, null);
		if (photosList.size() > 0)
			database.insert(SQLiteHelper.TABLE_PHOTO, null, values);
	}

	/**
	 * Delete a recipe from the database
	 * @param recipe The recipe to be removed
	 */
	public void deleteRecipe(Recipe recipe) {
		long id = recipe.getSqlID();
		database.delete(SQLiteHelper.TABLE_RECIPE, SQLiteHelper.RECIPE_COL_ID
				+ " = " + id, null);
		database.delete(SQLiteHelper.TABLE_INGRED, SQLiteHelper.RECIPE_COL_ID
				+ " = " + id, null);
		database.delete(SQLiteHelper.TABLE_INSTR, SQLiteHelper.RECIPE_COL_ID
				+ " = " + id, null);
		database.delete(SQLiteHelper.TABLE_PHOTO, SQLiteHelper.RECIPE_COL_ID
				+ " = " + id, null);
	}

	/**
	 * Retrieve a recipe from the database
	 * @param sqlID The row in the database to retrieve the recipe from
	 * @return The recipe
	 */
	public Recipe getRecipe(long sqlID) {
		Cursor cursor = database.query(SQLiteHelper.TABLE_RECIPE,
				allColumns, new String(SQLiteHelper.RECIPE_COL_ID + " = " 
						+ sqlID), null, null, null, null);

		cursor.moveToFirst();
		Recipe recipe = cursorToRecipe(cursor);
		cursor.close();
		return recipe;
	}

	/**
	 * Get all the recipes in the database
	 * @return All recipes
	 */
	public List<Recipe> getAllRecipes() {
		List<Recipe> recipes = new ArrayList<Recipe>();

		Cursor cursor = database.query(SQLiteHelper.TABLE_RECIPE,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Recipe recipe = cursorToRecipe(cursor);
			recipes.add(recipe);
			cursor.moveToNext();
		}

		cursor.close();
		return recipes;
	}
	
	private Recipe cursorToRecipe(Cursor cursor) {	
		long sqlID = cursor.getLong(0);
		String name = cursor.getString(1);
		String description = cursor.getString(2);
		String author = cursor.getString(3);
		int temp = cursor.getInt(4);
		boolean fave;
		if (temp == 1) 
			fave = true;
		else
			fave = false;
		float rating = cursor.getFloat(5);
		Date date = new Date(cursor.getString(6));
		UUID uuid = new UUID(name.hashCode(), author.hashCode());

		List<String> instructions = getInstructions(cursor);
		List<String> ingredients = getIngredients(cursor);
		List<Photo> photos = getPhotos(cursor);

		return new Recipe(name, description, author, ingredients,
				instructions, photos, rating, date, fave, uuid, sqlID);
	}

	private List<String> getInstructions(Cursor cursor) {
		List<String> instructions = new ArrayList<String>();

		Cursor instrCursor = database.query(SQLiteHelper.TABLE_INSTR,
				instrColumns, new String(SQLiteHelper.INSTR_COL_ID + " = " + 
						cursor.getLong(0)), null, null, null, null);

		instrCursor.moveToFirst();
		while (!instrCursor.isAfterLast()) {
			instructions.add(instrCursor.getString(1));
			instrCursor.moveToNext();
		}	

		instrCursor.close();
		return instructions;
	}

	private List<String> getIngredients(Cursor cursor) {
		List<String> ingredients = new ArrayList<String>();

		Cursor ingredCursor = database.query(SQLiteHelper.TABLE_INGRED,
				ingredColumns, new String(SQLiteHelper.INGRED_COL_ID + " = " + 
						cursor.getLong(0)), null, null, null, null);

		ingredCursor.moveToFirst();
		while (!ingredCursor.isAfterLast()) {
			ingredients.add(ingredCursor.getString(1));
			ingredCursor.moveToNext();
		}	

		ingredCursor.close();
		return ingredients;
	}

	private List<Photo> getPhotos(Cursor cursor) {
		List<Photo> photos = new ArrayList<Photo>();

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		
		Cursor photoCursor = database.query(SQLiteHelper.TABLE_PHOTO,
				photoColumns, new String(SQLiteHelper.PHOTO_COL_ID + " = " + 
						cursor.getLong(0)), null, null, null, null);

		photoCursor.moveToFirst();
		while (!photoCursor.isAfterLast()) {
			Bitmap bitmap = Photo.decodeBase64(photoCursor.getString(2));
			
			Photo photo = new Photo(photoCursor.getString(1), bitmap, 
					new Date(photoCursor.getString(3)));

			photos.add(photo);
			photoCursor.moveToNext();
		}	

		photoCursor.close();
		return photos;
	}
	private ContentValues insertRecipeValues(Recipe recipe) {
		ContentValues values = new ContentValues();

		values.put(SQLiteHelper.RECIPE_COL_AUTHOR, recipe.getAuthor());
		values.put(SQLiteHelper.RECIPE_COL_DATE, recipe.getDate().toString());
		values.put(SQLiteHelper.RECIPE_COL_DESC, recipe.getDescription());
		if (recipe.isFave())
			values.put(SQLiteHelper.RECIPE_COL_FAVE, 1);
		else
			values.put(SQLiteHelper.RECIPE_COL_FAVE, 0);
		values.put(SQLiteHelper.RECIPE_COL_NAME, recipe.getName());
		values.put(SQLiteHelper.RECIPE_COL_RATING, recipe.getRating());
		values.put(SQLiteHelper.RECIPE_COL_UUID, recipe.getId());

		return values;
	}

	private ContentValues insertInstructionValues(List<String> instructions,
			int row) {
		ContentValues values = new ContentValues();

		for (int i = 0; i < instructions.size(); i++) {
			values.put(SQLiteHelper.INSTR_COL_INSTR, instructions.get(i));
			values.put(SQLiteHelper.COL_USER_REFERENCE, row);
		}

		return values;
	}

	private ContentValues insertIngredientValues(List<String> ingredients,
			int row) {
		ContentValues values = new ContentValues();

		for (int i = 0; i < ingredients.size(); i++) {
			values.put(SQLiteHelper.INGRED_COL_INGRED, ingredients.get(i));
			values.put(SQLiteHelper.COL_USER_REFERENCE, row);
		}

		return values;
	}

	private ContentValues insertPhotoValues(List<Photo> photos, int row) {
		ContentValues values = new ContentValues();

		for (int i = 0; i < photos.size(); i++) {
			
			String photo = photos.get(i).getEncodedPhoto();
			
			values.put(SQLiteHelper.PHOTO_COL_AUTHOR, 
					photos.get(i).getAuthor());
			values.put(SQLiteHelper.PHOTO_COL_DATE, 
					photos.get(i).getDate().toString());
			values.put(SQLiteHelper.PHOTO_COL_PHOTO, photo);
			values.put(SQLiteHelper.COL_USER_REFERENCE, row);
		}

		return values;
	}
}
