package group5.database;

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
}
