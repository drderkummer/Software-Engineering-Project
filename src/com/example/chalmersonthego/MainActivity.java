package com.example.chalmersonthego;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import group5.database.DAO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private DAO dao;
	private GoogleMap map;
	private LatLngBounds strictBounds;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get the instance of GoogleMap
		setUpMapIfNeeded();

		// Open connection to the Database
		dao = new DAO(this);
		dao.open();

		// Getting the icon clickable
		getActionBar().setDisplayHomeAsUpEnabled(true);

		getActionBar().setHomeButtonEnabled(true);

		
	    // Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      doMySearch(query);
	    }
	    getSharedPreferences(null, 0);
	    insertDataForTheFirstTime();
	}
	public void insertDataForTheFirstTime(){
		String firstTime = "firstTime";
		boolean isFirstTime = true;
	    SharedPreferences  prefs = getSharedPreferences("com.example.android", 0);
	    if(prefs.getBoolean(firstTime, isFirstTime)){
	    	InsertionsOfData.basicDataInsert(dao);
	    	prefs.edit().putBoolean(firstTime, !isFirstTime).commit(); 
	    }
	}
	
	
	private void doMySearch(String searchString){
		Toast.makeText(this, "Searching", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onDestroy() {

		// Close connection to the database
		dao.close();

		super.onDestroy();
	}

	// Following method is called when launcher icon clicked
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case android.R.id.home:

		//There is no ID of this type
		/*case R.id.action_exit:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to exit?")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();
			return true;*/

		case R.id.showLectureHalls:
		case R.id.showComputerRooms:
			if (item.isChecked()) {
				item.setChecked(false);
				// Call remove dots function
			} else {
				item.setChecked(true);
				// Call add dots function
			}
			return true;

		case R.id.action_search:
		case R.id.action_layers:

			return true;

		default:
			Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT)
					.show();
			return true;
		}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the options menu from XML
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bottom_bar, menu);
		
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    searchView.setQueryRefinementEnabled(true);
	    // Assumes current activity is the searchable activity
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    //Do not iconify the widget; expand it by default
	    searchView.setIconifiedByDefault(false); 
		
		return true;
	}

	@Override
	public void onBackPressed() {	
		DialogInterface.OnClickListener dialogClickListener  = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					finish();
					break;
				}
			}
		};		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (map == null) {
			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (map != null) {// The Map is verified. It is now safe to
								// manipulate the map.
				
				// Initialize map
				map.animateCamera(CameraUpdateFactory.zoomTo(15));
				map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(57.68806,11.977978)));
				

				// When user drag map
				map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
					@Override
					public void onCameraChange(CameraPosition position) {

						// Set minimum zoom level
						if (position.zoom < 15)
							map.animateCamera(CameraUpdateFactory.zoomTo(15));

						// Limits on the map
						LatLng northWest = new LatLng(57.697497, 11.985397);
						LatLng southEast = new LatLng(57.678687, 11.969347);
						strictBounds = new LatLngBounds(southEast, northWest);

						// If position is within, do nothing.
						if (strictBounds.contains(map.getCameraPosition().target))
							return;

						// Seems that we are out of bound
						double x = map.getCameraPosition().target.latitude;
						double y = map.getCameraPosition().target.longitude;

						if (x < southEast.latitude)
							x = southEast.latitude;
						if (x > northWest.latitude)
							x = northWest.latitude;
						if (y < southEast.longitude)
							y = southEast.longitude;
						if (y > northWest.longitude)
							y = northWest.longitude;

						LatLng center = new LatLng(x, y);

						// Set new center
						map.moveCamera(CameraUpdateFactory.newLatLng(center));
					}
				});
			}
		}
	}

	// Making an dot on the map
	private void showDotOnMap(double lat, double lon, String label) {
		map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(label));
	}

	// Drawing an building on the map
	private void drawBuilding(double[] edges, int color) {
		// Instantiates a new Polygon object and adds points to define a
		// rectangle
		PolygonOptions rectOptions = new PolygonOptions();

		for (int x = 0; x < edges.length; x++)
			rectOptions.add(new LatLng(edges[x], edges[x + 1]));
		rectOptions.strokeColor(color);
		rectOptions.fillColor(color);

		// Get back the mutable Polygon
		Polygon polygon = map.addPolygon(rectOptions);
	}

}