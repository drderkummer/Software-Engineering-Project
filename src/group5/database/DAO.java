package group5.database;

import java.util.ArrayList;

import com.example.chalmersonthego.R;
import com.google.android.gms.maps.model.LatLng;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
/**
 * Data Access object. Like a layer on top of the Database
 * 
 * @author Fredrik
 *
 */
public class DAO{
	//Instance of the SQLite database
	private SQLiteDatabase database;
	//Object of the database handler class
	private DBHelper dbHelper;
	/**
	 * Empty Constructor
	 * Gets an instance of the Helper
	 * @param context for example this
	 */
	public DAO(Context context){
		dbHelper = new DBHelper(context);
	}	
	/**
	 * Needs to be called before any other operation on the database is performed.
	 * Get a writable database
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}	
	/**
	 * Call this when you are done modifying the database.
	 * Closes connection to database. Sameobject can be used again by calling open()
	 */
	public void close() {
		dbHelper.close();
	}
	/**
	 * Insert into Table 1. Provide all columns
	 * @param x
	 * @param y
	 * @param building
	 * Tested by testInsertAndGetFromTable1,testGetRoomCoordinatesAndInsertIntoTable1
	 */
	public void insertIntoTable1(double x, double y, String building){
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_1_COLUMN_1, x);
		values.put(DBHelper.TABLE_1_COLUMN_2, y);
		values.put(DBHelper.TABLE_1_COLUMN_3, building);
		database.insert(DBHelper.TABLE_1_NAME, null, values);
	}
	/**
	 * Insert into Table 2. Provide all columns
	 * @param name
	 * Tested in testInsertIntoTable2AndGetAllFromTable2,testGetRoomCoordinatesAndInsertIntoTable1
	 */
	public void insertIntoTable2(String name){
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_2_COLUMN_1, name);
		database.insert(DBHelper.TABLE_2_NAME, null, values);
	}
	/**
	 * Insert into Table 3. Provide all columns
	 * @param name 
	 * @param xCord 
	 * @param yCord 
	 * @param type 
	 * @param building 
	 * @param floor
	 * Tested testGetRoomCoordinatesAndInsertIntoTable3
	 */
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
	/**
	 * Insert into Table 4. Provide all columns.
	 * @param name
	 * Tested in testInsertAndGetFromTable4
	 */
	public void insertIntoTable4(String name){
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_4_COLUMN_1,name);
		database.insert(DBHelper.TABLE_4_NAME, null, values);
	}
	
	public void insertIntoTable5(String name, int drawablePicture){
		ContentValues values = new ContentValues();
		values.put(DBHelper.TABLE_5_COLUMN_1,name);
		values.put(DBHelper.TABLE_5_COLUMN_2,drawablePicture);
		database.insert(DBHelper.TABLE_5_NAME, null, values);
	}
	
	/**
	 * Get all columns from Table 4
	 * @return ArrayList<String> of columns.
	 * Tested in testInsertAndGetFromTable4
	 */
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
	 * Gets all from table 2 as ArrayList of String
	 * @return
	 * Tested in testInsertIntoTable2AndGetAllFromTable2
	 */
	public ArrayList<String> getAllFromTable2(){
		
		 String select = "SELECT * FROM " + DBHelper.TABLE_2_NAME;
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
	 * @param room
	 * @return
	 * Tested by testGetRoomCoordinatesAndInsertIntoTable1
	 */
	public LatLng getRoomCoordinates(String room){
		String[] columns ={DBHelper.TABLE_3_COLUMN_2, DBHelper.TABLE_3_COLUMN_3};
		String selection = DBHelper.TABLE_3_COLUMN_1 + " LIKE '" + room + "'";
		
		Cursor c = database.query(DBHelper.TABLE_3_NAME, columns, selection, null, null, null, null);
		if(c.moveToFirst()){
			return new LatLng(c.getDouble(0),c.getDouble(1));
		}else{
			return null;
		}
	}
	/**
	 * Calculates the closest entry for for the given building when the users
	 * current position is also given.
	 * @param building
	 * @return
	 * Tested by testCalculationOfClosestEntry
	 */
	public LatLng getClosestEntry(String building, LatLng currentCordinates){
		Double x = currentCordinates.latitude;
		Double y = currentCordinates.longitude;
		String math = " ((" + x + " - " + DBHelper.TABLE_1_COLUMN_1 + ") * (" + x + " - " + DBHelper.TABLE_1_COLUMN_1 + ") + " +
				" (" + y + " - " + DBHelper.TABLE_1_COLUMN_2 + ") * (" + y + " - " + DBHelper.TABLE_1_COLUMN_2 + ")) ";
		String select = "SELECT " + DBHelper.TABLE_1_COLUMN_1 + ", " + DBHelper.TABLE_1_COLUMN_2 +
				" FROM " + DBHelper.TABLE_1_NAME + " WHERE " + DBHelper.TABLE_1_COLUMN_3 + " LIKE '" +
				building + "' AND " + math + " = (SELECT MIN("+ math + ") FROM " + DBHelper.TABLE_1_NAME +  " WHERE " +
				DBHelper.TABLE_1_COLUMN_3 + " LIKE '" + building + "')";
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
	/**
	 * 
	 * @param building
	 * @return
	 * Tested by testGetAllRoomsInBuilding
	 */
	public ArrayList<String> getAllRoomsInBuilding(String building){
		ArrayList<String> result = new ArrayList<String>();
		String[] columns ={DBHelper.TABLE_3_COLUMN_1};
		String selection = DBHelper.TABLE_3_COLUMN_5 +" = '"+ building + "'";
		Cursor c = database.query(DBHelper.TABLE_3_NAME, columns, selection, null, null, null, null);
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
	 * @param xCord, ycoord
	 * @return name
	 * tested by  testGetName
	 */
	public String getName(double xCoord, double yCoord){
		String[] columns ={DBHelper.TABLE_3_COLUMN_1};
		String selection =DBHelper.TABLE_3_COLUMN_2 + " = '" + xCoord + "' AND " + DBHelper.TABLE_3_COLUMN_3 + " = '" + yCoord + "'";
		Cursor c = database.query(DBHelper.TABLE_3_NAME, columns, selection, null, null, null, null);
		if(c.moveToFirst()){
			return new String(c.getString(0));			
		}
		return null;
	}
	/**
	 * @param name
	 * @return floor
	 * tested by testGetFloor
	 */
	public String getFloor(String name){
		String[] columns ={DBHelper.TABLE_3_COLUMN_6};
		String selection =DBHelper.TABLE_3_COLUMN_1 + " = '" + name + "'";
		Cursor c = database.query(DBHelper.TABLE_3_NAME, columns, selection, null, null, null, null);
		if(c.moveToFirst()){
			return new String(c.getString(0));
		}
		return null;
	}
	/**
	 * @param name
	 * @return type
	 * tested by testGetType
	 */
	public String getType(String name){
		String[] columns ={DBHelper.TABLE_3_COLUMN_4};
		String selection = DBHelper.TABLE_3_COLUMN_1 + " = '" + name + "'";
		Cursor c = database.query(DBHelper.TABLE_3_NAME, columns, selection, null, null, null, null);
		if(c.moveToFirst()){
			return new String(c.getString(0));
		}
		return null;
	}
	/**
	 * 
	 * @param type
	 * @return
	 * Tested by testGetAllRoomsWithType
	 */
	public ArrayList<String> getAllRoomsWithType(String type){
		ArrayList<String> result = new ArrayList<String>();
		String[] columns ={DBHelper.TABLE_3_COLUMN_1};
		String selection = DBHelper.TABLE_3_COLUMN_4 + " LIKE '" + type +"'";
		Cursor c = database.query(DBHelper.TABLE_3_NAME, columns, selection, null, null, null, null);
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
	public Cursor suggestionsCursor(String searchString){
		String query1 = " SELECT " + DBHelper.TABLE_4_COLUMN_1 + 
				", '" + DBHelper.TABLE_4_NAME +"' "+ SearchManager.SUGGEST_COLUMN_TEXT_2 +
				","+ "NULL " + SearchManager.SUGGEST_COLUMN_ICON_1 +
				" FROM " + DBHelper.TABLE_4_NAME + 
				" WHERE " + DBHelper.TABLE_4_COLUMN_1 + " LIKE '%" + searchString + "%' ";
		String query2 = " SELECT " + DBHelper.TABLE_2_COLUMN_1 + 
				", '" + DBHelper.TABLE_2_NAME +"' "+ SearchManager.SUGGEST_COLUMN_TEXT_2 + 
				","+ "NULL " + SearchManager.SUGGEST_COLUMN_ICON_1 +
				" FROM " + DBHelper.TABLE_2_NAME + 
				" WHERE " + DBHelper.TABLE_2_COLUMN_1 + " LIKE '%" + searchString + "%' ";
		String query3 = " SELECT " + DBHelper.TABLE_3_COLUMN_1 + 
				", '" + DBHelper.TABLE_3_NAME +"' "+ SearchManager.SUGGEST_COLUMN_TEXT_2 + 
				","+ DBHelper.TABLE_5_COLUMN_2 + " " + SearchManager.SUGGEST_COLUMN_ICON_1 +
				" FROM " + DBHelper.TABLE_3_NAME + " NATURAL LEFT JOIN " + DBHelper.TABLE_5_NAME +
				" WHERE " + DBHelper.TABLE_3_COLUMN_1 + " LIKE '%" + searchString + "%' ";
		String fromUnion = " FROM ("+ query1 + " UNION " + query2 + " UNION " + query3 + ")";
		String select = "SELECT _ROWID_ " + BaseColumns._ID + ", name " +
				SearchManager.SUGGEST_COLUMN_QUERY + ", name " + SearchManager.SUGGEST_COLUMN_TEXT_1 +
				", " + SearchManager.SUGGEST_COLUMN_TEXT_2 + ", " + SearchManager.SUGGEST_COLUMN_ICON_1 ;
		String query = select + fromUnion;
		Cursor c = database.rawQuery(query, null);
		return c;
	}
	/**
	 * I think this function can be called while typing to get suggestions
	 * @return
	 * Tested by testSuggestions
	 */
	public ArrayList<String> suggestions(String searchString){ 
		Cursor c = suggestionsCursor(searchString);
		ArrayList<String> result = new ArrayList<String>();
		 if(c.getCount() == 0){
			 return null;
		 }else{
			 while(c.moveToNext()){
				 result.add(c.getString(1));
			 }
			 c.close();
			 return result;
		 }
	}
}
