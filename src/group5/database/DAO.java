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
	
	public void insertIntoTable3(String name, double xCord, double yCord, String type, String building, String floor){
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_3_COLUMN_1,name);
		values.put(DBHelper.TABLE_3_COLUMN_2,xCord);
		values.put(DBHelper.TABLE_3_COLUMN_3,yCord);
		values.put(DBHelper.TABLE_3_COLUMN_4,type);
		values.put(DBHelper.TABLE_3_COLUMN_5,building);
		values.put(DBHelper.TABLE_3_COLUMN_6,floor);
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
	
	public LatLng getRoomCoordinates(String room){
		String[] columns ={DBHelper.TABLE_3_COLUMN_2, DBHelper.TABLE_3_COLUMN_3};
		String selection = DBHelper.TABLE_3_COLUMN_1 + " = ?s";
		String[] selectionArgs = {room};
		
		Cursor c = database.query(DBHelper.TABLE_3_NAME, columns, selection, selectionArgs, null, null, null);
		if(c.moveToFirst()){
			return new LatLng(c.getDouble(0),c.getDouble(2));
		}else{
			return null;
		}
	}
	/**
	 * Calculates the closest entry for for the given building when the users
	 * current position is also given.
	 * @param building
	 * @return
	 */
	public LatLng getClosestEntry(String building, LatLng currentCordinates){
		Double x = currentCordinates.latitude;
		Double y = currentCordinates.longitude;
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
	ArrayList<String> getAllRooms(String building){
		ArrayList<String> result = new ArrayList<String>();
		String[] columns ={DBHelper.TABLE_3_COLUMN_1};
		String selection = DBHelper.TABLE_3_COLUMN_5 + " = ?s";
		String[] selectionArgs = {building};
		Cursor c = database.query(DBHelper.TABLE_3_NAME, columns, selection, selectionArgs, null, null, null);
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
	
	ArrayList<String> getAllRoomsWithType(String type){
		ArrayList<String> result = new ArrayList<String>();
		String[] columns ={DBHelper.TABLE_3_COLUMN_1};
		String selection = DBHelper.TABLE_3_COLUMN_4 + " = ?s";
		String[] selectionArgs = {type};
		Cursor c = database.query(DBHelper.TABLE_3_NAME, columns, selection, selectionArgs, null, null, null);
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
	 * I think this function can be called while typing to get suggestions
	 * @return
	 */
	public ArrayList<String> suggestions(String searchString){ 
		ArrayList<String> result = new ArrayList<String>();
		String query1 = "(SELECT " + DBHelper.TABLE_4_COLUMN_1 + " FROM " + DBHelper.TABLE_4_NAME + " WHERE " + DBHelper.TABLE_4_COLUMN_1 + " = '%" + searchString + "%')";
		String query2 = "(SELECT " + DBHelper.TABLE_2_COLUMN_1 + " FROM " + DBHelper.TABLE_2_NAME + " WHERE " + DBHelper.TABLE_2_COLUMN_1 + " = '%" + searchString + "%')";
		String query3 = "(SELECT " + DBHelper.TABLE_3_COLUMN_1 + " FROM " + DBHelper.TABLE_3_NAME + " WHERE " + DBHelper.TABLE_3_COLUMN_1 + " = '%" + searchString + "%')";

		String select = query1 + " UNION " + query2 + " UNION " + query3;

		Cursor c = database.rawQuery(select, null);
		
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
}
