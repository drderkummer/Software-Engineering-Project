package dat255.group5.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Holds SQLite Database. Don't call this. Use DAO instead.
 * 
 * @author Fredrik
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	// Constants for whole database
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "ChalmersOnTheGo";

	// Constants for table 4
	public static final String TABLE_4_NAME = "Buildings";
	public static final String TABLE_4_COLUMN_1 = "name";

	// Constants for table 1
	public static final String TABLE_1_NAME = "Entries";
	public static final String TABLE_1_COLUMN_1 = "x";
	public static final String TABLE_1_COLUMN_2 = "y";
	public static final String TABLE_1_COLUMN_3 = "building";


	// Constants for table 2
	public static final String TABLE_2_NAME = "Types";
	public static final String TABLE_2_COLUMN_1 = "name";
	// Constants for table 3
	public static final String TABLE_3_NAME = "Rooms";
	public static final String TABLE_3_COLUMN_1 = "name";
	public static final String TABLE_3_COLUMN_2 = "xCord";
	public static final String TABLE_3_COLUMN_3 = "yCord";
	public static final String TABLE_3_COLUMN_4 = "type";
	public static final String TABLE_3_COLUMN_5 = "building";
	public static final String TABLE_3_COLUMN_6 = "floor";

	// Constants for table 5
	public static final String TABLE_5_NAME = "Pubs";
	public static final String TABLE_5_COLUMN_1 = "name";
	public static final String TABLE_5_COLUMN_2 = "picture";

	// Create tables
	private static final String DATABASE_TABLE_4_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_4_NAME + " (" + TABLE_4_COLUMN_1 + " TEXT PRIMARY KEY)";
	private static final String DATABASE_TABLE_1_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_1_NAME
			+ " ("
			+ TABLE_1_COLUMN_1
			+ " REAL, "
			+ TABLE_1_COLUMN_2
			+ " REAL, "
			+ TABLE_1_COLUMN_3
			+ " REFERENCES "
			+ TABLE_4_NAME
			+ "("
			+ TABLE_4_COLUMN_1
			+ ") , "
			+ "PRIMARY KEY("
			+ TABLE_1_COLUMN_1 + "," + TABLE_1_COLUMN_2 + ")" + ")";
	private static final String DATABASE_TABLE_2_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_2_NAME + " (" + TABLE_2_COLUMN_1 + " TEXT PRIMARY KEY)";
	private static final String DATABASE_TABLE_3_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_3_NAME
			+ " ("
			+ TABLE_3_COLUMN_1
			+ " TEXT PRIMARY KEY, "
			+ TABLE_3_COLUMN_2
			+ " REAL, "
			+ TABLE_3_COLUMN_3
			+ " REAL, "
			+ TABLE_3_COLUMN_4
			+ " REFERENCES "
			+ TABLE_2_NAME
			+ ","
			+ TABLE_3_COLUMN_5
			+ " REFERENCES "
			+ TABLE_1_NAME
			+ "("
			+ TABLE_1_COLUMN_3
			+ ") , "
			+ TABLE_3_COLUMN_6
			+ " TEXT,"
			+ " UNIQUE ("
			+ TABLE_3_COLUMN_2
			+ ","
			+ TABLE_3_COLUMN_3
			+ ","
			+ TABLE_3_COLUMN_6 + ")" + ")";
	private static final String DATABASE_TABLE_5_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_5_NAME
			+ " ("
			+ TABLE_5_COLUMN_1
			+ " REFERENCES "
			+ TABLE_3_NAME + " PRIMARY KEY, " + TABLE_5_COLUMN_2 + " INTEGER)";

	/**
	 * 
	 * @param context
	 *            "this" works
	 */
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Called when the database is created for the first time. This is where the
	 * creation of tables and the initial population of the tables should
	 * happen.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_TABLE_4_CREATE);
		db.execSQL(DATABASE_TABLE_1_CREATE);
		db.execSQL(DATABASE_TABLE_2_CREATE);
		db.execSQL(DATABASE_TABLE_3_CREATE);
		db.execSQL(DATABASE_TABLE_5_CREATE);
	}

	/**
	 * Called when database is upgraded
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		String drop = "DROP TABLE IF EXISTS ";
		db.execSQL(drop + DATABASE_TABLE_5_CREATE);
		db.execSQL(drop + DATABASE_TABLE_4_CREATE);
		db.execSQL(drop + DATABASE_TABLE_3_CREATE);
		db.execSQL(drop + DATABASE_TABLE_2_CREATE);
		db.execSQL(drop + DATABASE_TABLE_1_CREATE);
		onCreate(db);
	}

	/*
	 * this method checks whether the database is setup or not
	 */
	public static boolean databaseExists() {

		SQLiteDatabase dbFile = null;
		try {
			dbFile = SQLiteDatabase.openDatabase(DATABASE_NAME, null,
					SQLiteDatabase.OPEN_READONLY);
			dbFile.close();
		} catch (SQLiteException e) {
			// DB does not exist!
		}
		if (dbFile != null) {
			return true;
		} else {
			return false;
		}
	}
}