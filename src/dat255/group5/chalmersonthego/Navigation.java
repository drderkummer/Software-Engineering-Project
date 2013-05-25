package dat255.group5.chalmersonthego;

import com.example.chalmersonthego.R;
import com.example.chalmersonthego.R.layout;
import com.example.chalmersonthego.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

public class Navigation extends Activity {
	public static final String[] COUNTRIES = new String[] {
		"Belgium", "France", "Italy", "Germany", "Spain"
	};	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);
		
		/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(owningActivity,
				R.layout.navigation, COUNTRIES);
		MultiAutoCompleteTextView navStart = (MultiAutoCompleteTextView) owningActivity.findViewById(R.id.nav_search_start);
		MultiAutoCompleteTextView navDest = (MultiAutoCompleteTextView) owningActivity.findViewById(R.id.nav_search_dest);
		

		navStart.setAdapter(adapter);
		navDest.setAdapter(adapter);

		navStart.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		navDest.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	*/}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}
