package dat255.group5.database;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class SuggestionsContentProvider extends ContentProvider{
	
	private DAO dao;

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
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
