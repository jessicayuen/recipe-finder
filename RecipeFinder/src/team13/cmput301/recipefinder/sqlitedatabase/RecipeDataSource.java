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

public class RecipeDataSource {

	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;

	private String[] allColumns = { SQLiteHelper.RECIPE_COL_ID,
			SQLiteHelper.RECIPE_COL_NAME,
			SQLiteHelper.RECIPE_COL_DESC,
			SQLiteHelper.RECIPE_COL_AUTHOR, 		
			SQLiteHelper.RECIPE_COL_FAVE,		
			SQLiteHelper.RECIPE_COL_RATING,
			SQLiteHelper.RECIPE_COL_DATE,
			SQLiteHelper.RECIPE_COL_UUID };
	
	private String[] instrColumns = { SQLiteHelper.INSTR_COL_ID,
			SQLiteHelper.INSTR_COL_INSTR };
	
	private String[] ingredColumns = { SQLiteHelper.INGRED_COL_ID,
			SQLiteHelper.INGRED_COL_INGRED };
	
	private String[] photoColumns = { SQLiteHelper.PHOTO_COL_ID,
			SQLiteHelper.PHOTO_COL_AUTHOR, 
			SQLiteHelper.PHOTO_COL_DATE,
			SQLiteHelper.PHOTO_COL_PHOTO };

	public RecipeDataSource(Context context) {
		dbHelper = new SQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void insertRecipe(Recipe recipe) {
		ContentValues values = new ContentValues();
		ContentValues instructions = new ContentValues();
		ContentValues ingredients = new ContentValues();
		ContentValues photos = new ContentValues();

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

		long id = database.insert(SQLiteHelper.TABLE_RECIPE, null, values);
		recipe.setSqlID(id);

		List<String> instructionsList = recipe.getInstructions();
		for (int i = 0; i < instructionsList.size(); i++) {
			instructions.put(SQLiteHelper.INSTR_COL_INSTR, 
					instructionsList.get(i));
			instructions.put(SQLiteHelper.COL_USER_REFERENCE, id);
		}
		if (instructionsList.size() > 0)
			database.insert(SQLiteHelper.TABLE_INSTR, null, instructions);

		List<String> ingredientsList = recipe.getIngredients();
		for (int i = 0; i < ingredientsList.size(); i++) {
			ingredients.put(SQLiteHelper.INGRED_COL_INGRED, 
					ingredientsList.get(i));
			ingredients.put(SQLiteHelper.COL_USER_REFERENCE, id);
		}
		if (ingredientsList.size() > 0)
			database.insert(SQLiteHelper.TABLE_INGRED, null, ingredients);

		List<Photo> photosList = recipe.getPhotos();
		for (int i = 0; i < photosList.size(); i++) {
			String photo = Photo.encodeTobase64(photosList.get(i).getPhoto());

			photos.put(SQLiteHelper.PHOTO_COL_AUTHOR, 
					photosList.get(i).getAuthor());
			photos.put(SQLiteHelper.PHOTO_COL_DATE, 
					photosList.get(i).getDate().toString());
			photos.put(SQLiteHelper.PHOTO_COL_PHOTO, photo);
			photos.put(SQLiteHelper.COL_USER_REFERENCE, id);
		}
		if (photosList.size() > 0)
			database.insert(SQLiteHelper.TABLE_PHOTO, null, photos);
	}

	public void replaceRecipe(Recipe recipe, int row) {
		ContentValues values = new ContentValues();
		ContentValues instructions = new ContentValues();
		ContentValues ingredients = new ContentValues();
		ContentValues photos = new ContentValues();

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
		
		database.update(SQLiteHelper.TABLE_RECIPE, values,
				new String(SQLiteHelper.RECIPE_COL_ID + " = " + row), null);
		
		List<String> instructionsList = recipe.getInstructions();
		for (int i = 0; i < instructionsList.size(); i++) {
			instructions.put(SQLiteHelper.INSTR_COL_INSTR, 
					instructionsList.get(i));
			instructions.put(SQLiteHelper.COL_USER_REFERENCE, row);
		}
		
		if (instructionsList.size() > 0)
			database.update(SQLiteHelper.TABLE_INSTR, instructions,
					new String(SQLiteHelper.COL_USER_REFERENCE + " = " + row), null);
		
		List<String> ingredientsList = recipe.getIngredients();
		for (int i = 0; i < ingredientsList.size(); i++) {
			ingredients.put(SQLiteHelper.INGRED_COL_INGRED, 
					ingredientsList.get(i));
			ingredients.put(SQLiteHelper.COL_USER_REFERENCE, row);
		}
	
		if (instructionsList.size() > 0)
			database.update(SQLiteHelper.TABLE_INGRED, ingredients,
					new String(SQLiteHelper.COL_USER_REFERENCE + " = " + row), null);

		List<Photo> photosList = recipe.getPhotos();
		for (int i = 0; i < photosList.size(); i++) {
			String photo = Photo.encodeTobase64(photosList.get(i).getPhoto());

			photos.put(SQLiteHelper.PHOTO_COL_AUTHOR, 
					photosList.get(i).getAuthor());
			photos.put(SQLiteHelper.PHOTO_COL_DATE, 
					photosList.get(i).getDate().toString());
			photos.put(SQLiteHelper.PHOTO_COL_PHOTO, photo);
			photos.put(SQLiteHelper.COL_USER_REFERENCE, row);
		}
		
		if (photosList.size() > 0)
			database.update(SQLiteHelper.TABLE_PHOTO, photos,
					new String(SQLiteHelper.COL_USER_REFERENCE + " = " + row), null);
	}
	
	public void deleteRecipe(Recipe recipe) {
		long id = recipe.getSqlID();
		database.delete(SQLiteHelper.TABLE_RECIPE, SQLiteHelper.RECIPE_COL_ID
				+ " = " + id, null);
	}

	public Recipe getRecipe(long sqlID) {
		Cursor cursor = database.query(SQLiteHelper.TABLE_RECIPE,
				allColumns, new String(SQLiteHelper.RECIPE_COL_ID + " = " 
				+ sqlID), null, null, null, null);
		
		cursor.moveToFirst();
		Recipe recipe = cursorToRecipe(cursor);
		cursor.close();
		return recipe;
	}
	
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
		
		Cursor photoCursor = database.query(SQLiteHelper.TABLE_PHOTO,
				photoColumns, new String(SQLiteHelper.PHOTO_COL_ID + " = " + 
				cursor.getLong(0)), null, null, null, null);
		
		photoCursor.moveToFirst();
		while (!photoCursor.isAfterLast()) {
			String imageString = photoCursor.getString(2);
			Bitmap bitmap = Photo.decodeBase64(imageString);
					
			Photo photo = new Photo(photoCursor.getString(1), 
					bitmap, new Date(photoCursor.getString(3)));
			
			photos.add(photo);
			photoCursor.moveToNext();
		}	
		
		photoCursor.close();
		return photos;
	}
}
