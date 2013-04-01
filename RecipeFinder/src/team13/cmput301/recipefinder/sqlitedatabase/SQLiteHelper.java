package team13.cmput301.recipefinder.sqlitedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Responsible for SQL database definitions.
 * 
 * CMPUT301 W13 T13
 * @author Han (Jim) Wen, Jessica Yuen, Shen Wei Liao, Fangyu Li
 */
public class SQLiteHelper extends SQLiteOpenHelper {
	 
	private static final String DATABASE_NAME = "recipes.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_RECIPE = "recipes";
	public static final String RECIPE_COL_ID = "_id";
	public static final String RECIPE_COL_NAME = "name";
	public static final String RECIPE_COL_DESC = "description";
	public static final String RECIPE_COL_AUTHOR = "author";
	public static final String RECIPE_COL_FAVE = "fave";
	public static final String RECIPE_COL_RATING = "rating";
	public static final String RECIPE_COL_DATE = "date";
	public static final String RECIPE_COL_UUID = "uuid";
	public static final String RECIPE_COL_NUM_RATINGS = "num_ratings";
	public static final String RECIPE_COL_TOTAL_RATING = "total_ratings";
	
	public static final String TABLE_INSTR = "instructions";
	public static final String INSTR_COL_ID = "_id";
	public static final String INSTR_COL_INSTR = "instruction";
	
	public static final String TABLE_INGRED = "ingredients";
	public static final String INGRED_COL_ID = "_id";
	public static final String INGRED_COL_INGRED = "ingredient";
	
	public static final String TABLE_PHOTO = "photos";
	public static final String PHOTO_COL_ID = "_id";
	public static final String PHOTO_COL_AUTHOR = "author";
	public static final String PHOTO_COL_PHOTO = "photo";
	public static final String PHOTO_COL_DATE = "date";
	
	public static final String COL_USER_REFERENCE = "userID";
	
	/* SQL statement for creating recipes table */
	private static final String CREATE_RECIPE_TABLE = 
			"create table if not exists " 
			+ TABLE_RECIPE + "(" + RECIPE_COL_ID 
			+ " integer primary key autoincrement, "
			+ RECIPE_COL_NAME + " text not null, " 
			+ RECIPE_COL_DESC + " text not null, "
			+ RECIPE_COL_AUTHOR + " text not null, "
			+ RECIPE_COL_FAVE + " integer not null, "
			+ RECIPE_COL_RATING + " real not null, "
			+ RECIPE_COL_DATE + " text not null, "
			+ RECIPE_COL_UUID + " text not null, " 
			+ RECIPE_COL_NUM_RATINGS + " integer not null,"
			+ RECIPE_COL_TOTAL_RATING + " real not null);";
	
	/* SQL statement for creating instructions table */
	private static final String CREATE_INSTR_TABLE = 
			"create table if not exists " 
			+ TABLE_INSTR + "(" + INSTR_COL_ID 
			+ " integer primary key autoincrement, "
			+ INSTR_COL_INSTR + " text not null, " 
			+ COL_USER_REFERENCE + " integer not null, " 
			+ " FOREIGN KEY (" + COL_USER_REFERENCE + ") REFERENCES "
			+ TABLE_RECIPE + " (" + RECIPE_COL_ID + ") ON DELETE CASCADE);";
	
	/* SQL statement for creating ingredients table */
	private static final String CREATE_INGRED_TABLE = 
			"create table if not exists " 
			+ TABLE_INGRED + "(" + INGRED_COL_ID 
			+ " integer primary key autoincrement, "
			+ INGRED_COL_INGRED + " text not null, " 
			+ COL_USER_REFERENCE + " integer not null, " 
			+ " FOREIGN KEY (" + COL_USER_REFERENCE + ") REFERENCES "
			+ TABLE_RECIPE + " (" + RECIPE_COL_ID + ") ON DELETE CASCADE);";
			
	/* SQL statement for creating photos table */
	private static final String CREATE_PHOTO_TABLE = 
			"create table if not exists " 
			+ TABLE_PHOTO + "(" + PHOTO_COL_ID 
			+ " integer primary key autoincrement, "
			+ PHOTO_COL_AUTHOR + " text not null, " 
			+ PHOTO_COL_PHOTO + " text not null, "
			+ PHOTO_COL_DATE + " text not null, "
			+ COL_USER_REFERENCE + " integer not null, " 
			+ " FOREIGN KEY (" + COL_USER_REFERENCE + ") REFERENCES "
			+ TABLE_RECIPE + " (" + RECIPE_COL_ID + ") ON DELETE CASCADE);";
	
	/**
	 * Constructor
	 * @param context The activity context
	 */
	public SQLiteHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	/**
	 * Creates table definitions
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_RECIPE_TABLE);
		db.execSQL(CREATE_INSTR_TABLE);
		db.execSQL(CREATE_INGRED_TABLE);
		db.execSQL(CREATE_PHOTO_TABLE);
	}

	@Override
	/**
	 * Upgrades database versions, dropping existing tables.
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(), 
				"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTR);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGRED);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTO);
		onCreate(db);
	}
}
