package com.example.chalmersonthego;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import group5.database.DAO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private DAO dao;
	private GoogleMap map;		
	
	// Bound the map accessed from multiple places
	private LatLng northWest = new LatLng(57.697497, 11.985397);
	private LatLng southEast = new LatLng(57.678687, 11.969347);
	private LatLngBounds strictBounds = new LatLngBounds(southEast, northWest);
	
	private HashMap<Integer, Marker> markers = new HashMap<Integer, Marker>();


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

		getSharedPreferences(null, 0);
		insertDataForTheFirstTime();
	}
	// Only executed when installing the app for the first time
	public void insertDataForTheFirstTime(){
		String firstTime = "firstTime";
		boolean isFirstTime = true;
		SharedPreferences  prefs = getSharedPreferences("com.example.android", 0);
		if(prefs.getBoolean(firstTime, isFirstTime)){
			InsertionsOfData.basicDataInsert(dao);
			prefs.edit().putBoolean(firstTime, !isFirstTime).commit(); 
		}
	}
	@Override
	/**
	 * Function is called when a new intent comes.
	 * Especially when a search is performed
	 */
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		// Get the intent, verify the action and get the query
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);
		}
	}

	/**
	 * Searches for rooms, types and buildings. Priority by order.
	 * Room: plot that room
	 * Type: plot all rooms with that type
	 * Building: plot the closest entry
	 * @param searchString
	 */
	private void doMySearch(String searchString){
		LatLng latLng = dao.getRoomCoordinates(searchString);
		ArrayList<String> list = dao.getAllRoomsWithType(searchString);
		//Your current coordinates should be put in the following line
		LatLng currentCoordinates = new LatLng(0.0,0.0);
		LatLng closestEntry = dao.getClosestEntry(searchString, currentCoordinates);
		if(latLng != null){
			showDotOnMap(latLng,"Put description here");
		}else if(list != null){
			for(String name : list){
				latLng = dao.getRoomCoordinates(name);
				showDotOnMap(latLng,"Put description here");
			}
		}else if(closestEntry != null){
			showDotOnMap(closestEntry,"Put description here");
		}else{
			Toast.makeText(this,searchString + " is not in the database" , Toast.LENGTH_LONG).show();
		}

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
			if (item.isChecked()) {
				item.setChecked(false);
				// Call remove dots function
				removeAllMarkerFromMap();
			} else {
				item.setChecked(true);
				// Call add dots function
				ArrayList<String> result = dao.getAllRoomsWithType("lecture hall");
				for(int i=0; i < result.size(); i++){
					//TODO String "computer room" just a placeholder right know. --> getName
					//Marker m = new google.maps.Marker({ position: dao.getRoomCoordinates(result.get(i)), title:"Hello World!" });	
					//mapMarker(m);

					LatLng coords = dao.getRoomCoordinates(result.get(i));
					//String name = dao.getName(coords.latitude, coords.longitude);
					showDotOnMap(coords, dao.getName(coords.latitude, coords.longitude));
				}
			}
			return true;
		case R.id.showComputerRooms:
			if (item.isChecked()) {
				item.setChecked(false);
				// Call remove dots function
				removeAllMarkerFromMap();
			} else {
				item.setChecked(true);
				// Call add dots function
				ArrayList<String> result = dao.getAllRoomsWithType("computer room");
				for(int i=0; i < result.size(); i++){
					//TODO String "computer room" just a placeholder right know. --> getName
					//Marker m = new google.maps.Marker({ position: dao.getRoomCoordinates(result.get(i)), title:"Hello World!" });	
					//mapMarker(m);

					LatLng coords = dao.getRoomCoordinates(result.get(i));
					//String name = dao.getName(coords.latitude, coords.longitude);
					showDotOnMap(coords, dao.getName(coords.latitude, coords.longitude));
				}
			}
			return true;
		case R.id.showGroupRooms:
			if (item.isChecked()) {
				item.setChecked(false);
				// Call remove dots function
				removeAllMarkerFromMap();
			} else {
				item.setChecked(true);
				// Call add dots function
				ArrayList<String> result = dao.getAllRoomsWithType("group room");
				for(int i=0; i < result.size(); i++){
					//TODO String "computer room" just a placeholder right know. --> getName
					//Marker m = new google.maps.Marker({ position: dao.getRoomCoordinates(result.get(i)), title:"Hello World!" });	
					//mapMarker(m);

					LatLng coords = dao.getRoomCoordinates(result.get(i));
					//String name = dao.getName(coords.latitude, coords.longitude);
					showDotOnMap(coords, dao.getName(coords.latitude, coords.longitude));
				}
			}
			return true;
		case R.id.action_search:
		case R.id.action_layers:
		case R.id.action_my_location:
			setMyPosition();				
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
			if (map != null) {
				
				if(!setMyPosition()){
					// Initialize map
					map.animateCamera(CameraUpdateFactory.zoomTo(15));
					map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(57.68806,11.977978)));		
				}				
				
				// When user drag map
				map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
					@Override
					public void onCameraChange(CameraPosition position) {

						// Set minimum zoom level
						if (position.zoom < 15)
							map.animateCamera(CameraUpdateFactory.zoomTo(15));

						// If position is within, do nothing.
						if (strictBounds.contains((map.getCameraPosition().target)))
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

						// Set new center
						LatLng center = new LatLng(x, y);
						map.moveCamera(CameraUpdateFactory.newLatLng(center));
					}
				});
			}
		}
	}

	// Making an dot on the map
	private void showDotOnMap(LatLng latLng, String description) {	// Do we really need a method for a one-liner? // Anders
		map.addMarker(new MarkerOptions().position(latLng).title(description));
	}
	
	
	
	//probably needed to map markers on the map and
	//just delete for ex. "computer room" markers
	private void mapMarker(Marker m){
		int mapPosition = markers.size()-1;
		markers.put(mapPosition, m);

	};


	//not final method!
	//delete all markers from map
	private void removeAllMarkerFromMap(){
		map.clear();
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

	/**
	 * Puts a marker on map where the user's current position is
	 * @return true if position was set, false if otherwise
	 */
	private boolean setMyPosition(){
		Location location = getCurrentPosition();

		if(location!=null){
			LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());			
			
			if(strictBounds.contains(myPosition)){
				Marker hereAmI = map.addMarker(new MarkerOptions()
				.position(myPosition)
				.title("My Location")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.snippet("I am here"));
				hereAmI.showInfoWindow();			

				map.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
				map.moveCamera(CameraUpdateFactory.zoomTo(15));
				
				return true;
			}			
		}
		return false;
	}

	/**
	 * Returns current position
	 * @return Current position as Location
	 */
	private Location getCurrentPosition(){
		// Getting LocationManager object from System Service LOCATION_SERVICE
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		// Creating a criteria object to retrieve provider
		Criteria criteria = new Criteria();
		// Getting the name of the best provider
		String provider = locationManager.getBestProvider(criteria, true);
		// Getting Current Location
		Location location = locationManager.getLastKnownLocation(provider);

		return location;
	}
}