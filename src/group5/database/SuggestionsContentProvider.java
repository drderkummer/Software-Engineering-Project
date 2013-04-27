package group5.database;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class SuggestionsContentProvider extends ContentProvider{
	
	private DAO dao;
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		dao = new DAO(getContext());
		dao.open();
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		String query = arg0.getLastPathSegment(); 
		if (SearchManager.SUGGEST_URI_PATH_QUERY.equals(query)) {
			// user hasn't entered anything
			// thus return a default cursor
			return null;
		} else {
			// query contains the users search
			// return a cursor with appropriate data
			return dao.suggestionsCursor(query);
		}
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		
		return 0;
	}

}
