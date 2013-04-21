package group5.database;

import java.util.ArrayList;
import com.google.android.gms.maps.model.LatLng;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	
	public ArrayList<String> getAllFromTable4(){
		
		 String select = "SELECT * FROM " + DBHelper.TABLE_4_NAME;
		 Cursor c = database.rawQuery(select, null);
		 ArrayList<String> result = new ArrayList<String>(c.getCount());
		 if(c.getCount() == 0){
			 return null;
		 }else{
			 while(c.moveToNext()){
				 result.add(c.getString(0));
			 }
			 c.close();
			 return result;
		 }
	}
	/**
	 * 
	 * @param building
	 * @return
	 */
	public LatLng getClosestEntry(String building, LatLng cordinates){
		Double x = cordinates.latitude;
		Double y = cordinates.longitude;
		String math = " ((" + x + " - " + DBHelper.TABLE_1_COLUMN_1 + ") * (" + x + " - " + DBHelper.TABLE_1_COLUMN_1 + ") + " +
				" (" + y + " - " + DBHelper.TABLE_1_COLUMN_2 + ") * (" + y + " - " + DBHelper.TABLE_1_COLUMN_2 + ")) ";
		String select = "SELECT " + DBHelper.TABLE_1_COLUMN_1 + ", " + DBHelper.TABLE_1_COLUMN_2 +
				" FROM " + DBHelper.TABLE_1_NAME + " WHERE " + DBHelper.TABLE_1_COLUMN_3 + " = '" +
				building + "' AND " + math + " = (SELECT MIN("+ math + ") FROM " + DBHelper.TABLE_1_NAME +  " WHERE " +
				DBHelper.TABLE_1_COLUMN_3 + " = '" + building + "')";
		Cursor c = database.rawQuery(select, null);
		 if(c.getCount() == 0){
			 return null;
		 }else if (c.moveToFirst()){
				 LatLng latLng = new LatLng(c.getDouble(0),c.getDouble(1));
				 c.close();
				 return latLng;
		}

		return null;
	}
}
