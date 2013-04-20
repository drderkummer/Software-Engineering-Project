package group5.database;

import com.google.android.gms.maps.model.LatLng;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
/**
 * Data Access object. Like a layer on top of the Database
 * 
 * @author Fredrik
 *
 */
public class DAO {
	
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	
	/**
	 * Empty Constructor
	 * @param context for example this
	 */
	public DAO(Context context){
		dbHelper = new DBHelper(context);
	}
	
	/**
	 * Needs to be called before any other operation on the database is performed.
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	/**
	 * Call this when you are done modifying the database.
	 */
	public void close() {
		dbHelper.close();
	}
	
	public void insertIntoTable1(double x, double y, String building){
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_1_COLUMN_1, x);
		values.put(DBHelper.TABLE_1_COLUMN_2, y);
		values.put(DBHelper.TABLE_1_COLUMN_3, building);
		database.insert(DBHelper.TABLE_1_NAME, null, values);
	}
	
	public void insertIntoTable2(String name){
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_2_COLUMN_1, name);
		database.insert(DBHelper.TABLE_1_NAME, null, values);
	}
	
	public void insertIntoTable3(String name, double xCord, double yCord, String type, double xClosestEntry, double yClosestEntry){
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_3_COLUMN_1,name);
		values.put(DBHelper.TABLE_3_COLUMN_2,xCord);
		values.put(DBHelper.TABLE_3_COLUMN_3,yCord);
		values.put(DBHelper.TABLE_3_COLUMN_4,type);
		values.put(DBHelper.TABLE_3_COLUMN_5,xClosestEntry);
		values.put(DBHelper.TABLE_3_COLUMN_6,yClosestEntry);
		database.insert(DBHelper.TABLE_3_NAME, null, values);
	}
	
	public void insertIntoTable4(String name){
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_4_COLUMN_1,name);
		database.insert(DBHelper.TABLE_4_NAME, null, values);
	}
	/**
3 Vilka ingångar finns angivna för byggnaden
4 Beräkna vilken ingång som ligger närmast din nuvarande position
	 * 
	 * @param building
	 * @return
	 */
	public LatLng getClosestEntryForBuilding(String building){
		String query = "SELECT " + DBHelper.TABLE_1_COLUMN_1 + ", " + DBHelper.TABLE_1_COLUMN_2 +
				" FROM " + DBHelper.TABLE_1_NAME +
				" WHERE " + DBHelper.TABLE_1_COLUMN_3 + " = " + building + " AND " + "(" +
				"POW(" + DBHelper.TABLE_1_COLUMN_1 + ",2) + POW(" + DBHelper.TABLE_1_COLUMN_2 + ",2)) = (SELECT MIN(POW(" +
				DBHelper.TABLE_1_COLUMN_1 + ",2) + POW(" + DBHelper.TABLE_1_COLUMN_2 + ",2)) FROM " + DBHelper.TABLE_1_NAME +  "WHERE " +
				DBHelper.TABLE_1_COLUMN_3 + " = " + building + ")";
		return null;
	}
}
