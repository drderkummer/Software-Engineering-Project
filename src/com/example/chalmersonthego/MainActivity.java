package com.example.chalmersonthego;

import group5.database.DAO;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private DAO dao;
	private CustomGoogleMaps customMaps;
	private NavigationManager navigationManager;
	
	//helping variables needed to check the state of the checkbox menu
	Boolean lectureHallsAreChecked = false;
	Boolean computerRoomsAreChecked = false;
	Boolean groupRoomsAreChecked = false;

	@SuppressLint("NewApi") 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		turnOnLocationsIfNeeded();

		// Get the instance of GoogleMap
		GoogleMap googleMap = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		customMaps = new CustomGoogleMaps(this,googleMap);
		navigationManager = new NavigationManager(googleMap);

		configureUI();

		// Open connection to the Database
		dao = new DAO(this);
		dao.open();
		insertDataForTheFirstTime();
		
		//Setting up the actionbar
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
		ab.setTitle("Daymode");
	}
	/**
	private void customPath(LatLng from, LatLng to){
		//Get length via standard API.
		int apiLength = 0;
		
		//get current position in LatLng
		Location currentLocation = customMaps.getCurrentPosition();
		LatLng currentCordinates = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
		
		//Get the cloesest Entry from current position to all buildings
		//get all buildings
		ArrayList<String> buildings = dao.getAllFromTable4();
		//Loop over all buildings
		
		for(String building : buildings){
			LatLng cloestEntry = dao.getClosestEntry(building, currentCordinates);
			
			//Calculate path length to the closestEntry
			int length = 0; //Get length via API to this entry
			if(length < apiLength){
				LatLng closestOut = dao.getClosestEntry(building, to);
				length += 0; //= getDistance(cloestEntry,closestOut)
				if(length <apiLength){
					length += 0;//getAPILength(closestOut,to);
				}
			}
		}
				
	}**/
	
	
	
	/**
	 * This method is used for all configuration of the UI which
	 * can't be done in the xml except the configuration of the map.
	 */
	private void configureUI(){
		// Getting the icon clickable
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}
	

	
	/**
	 * This functions checks if the gps is enabled.
	 * If it's not the user is prompted and redirected to turn it on.
	 */
	private void turnOnLocationsIfNeeded(){
		//Prompt the user to turn on gps and other location handlers.
		LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
	    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        String message = "Enable either GPS or any other location"
		            + " service to find current location.";
		        builder.setMessage(message)
		            .setPositiveButton("Yes",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface d, int id) {
		                    	startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		                        d.dismiss();
		                    }
		            })
		            .setNegativeButton("No",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface d, int id) {
		                        d.cancel();
		                    }
		            });
		        builder.create().show();
	    }
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
			String rname = dao.getName(latLng.latitude, latLng.longitude);
			customMaps.showMarkerOnMap(latLng, rname, dao.getFloor(rname), dao.getType(rname));
		}else if(list != null){
			for(String name : list){
				latLng = dao.getRoomCoordinates(name);
				customMaps.showMarkerOnMap(latLng, name, dao.getFloor(name), dao.getType(name));
			}
		}else if(closestEntry != null){
			customMaps.showMarkerOnMap(closestEntry,"Put description here", null, null);
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
	
	/**
	 * shows all group rooms on map 
	 */
    public void showGroupRooms(){
               ArrayList<String> r2 = dao.getAllRoomsWithType("group room");
               for(int i=0; i < r2.size(); i++){
                       LatLng coords = dao.getRoomCoordinates(r2.get(i));
                       String name = dao.getName(coords.latitude, coords.longitude);
                       customMaps.showMarkerOnMap(coords, name, dao.getFloor(name),"group room");
               }
    }
    
    /**
     * shows all computer rooms on the map
     */
    public void showCompRooms(){
               ArrayList<String> r1 = dao.getAllRoomsWithType("computer room");
               for(int i=0; i < r1.size(); i++){
                       LatLng coords = dao.getRoomCoordinates(r1.get(i));
                       String name = dao.getName(coords.latitude, coords.longitude);
                       customMaps.showMarkerOnMap(coords, name, dao.getFloor(name),"computer room");
               }
       }
     
    /**
     * shows all lecture halls on the map
     */
    public void showLectHalls(){
               ArrayList<String> r3 = dao.getAllRoomsWithType("lecture hall");
               for(int i = 0; i < r3.size() ; i++){
                       LatLng coords = dao.getRoomCoordinates(r3.get(i));
                       String name = dao.getName(coords.latitude, coords.longitude);
                       customMaps.showMarkerOnMap(coords, name, dao.getFloor(name), "lecture hall");
               }
       }

	
	/** Following method is called when launcher icon clicked
	 * @param MenuItem
	 * 
	 * IF MenuItem == CheckBoxes we need to do:
	 * 		1. clear map from markers
	 * 		2. check if CheckBox is Checked or NotChecked
	 * 				set new status relation to that
	 * 				set status of boolean value
	 * 		3. check status from other CheckBoxes
	 * 		4. show markers from checked CheckBoxes
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	
		switch (item.getItemId()) {
			//There is no ID of this type
			/*case R.id.action_exit:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to exit?")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();
			return true;*/
		
		case R.id.action_layers:
			return true;

		case R.id.showLectureHalls:
			customMaps.removeAllMarkerFromMap();
			if(item.isChecked()){
				item.setChecked(false);
				lectureHallsAreChecked = false;
			}else{
				item.setChecked(true);
				lectureHallsAreChecked = true;
				showLectHalls();
			}
			if(computerRoomsAreChecked) showCompRooms();
			if(groupRoomsAreChecked) showGroupRooms();
			return true;
			
		case R.id.showComputerRooms:
			customMaps.removeAllMarkerFromMap();
			if(item.isChecked()){
				item.setChecked(false);
				computerRoomsAreChecked = false;
			}else{
				item.setChecked(true);
				showCompRooms();
				computerRoomsAreChecked = true;
			}
			if(lectureHallsAreChecked) showLectHalls();
			if(groupRoomsAreChecked) showGroupRooms();
			return true;
			
		case R.id.showGroupRooms:
			customMaps.removeAllMarkerFromMap();
			if (item.isChecked()){
				item.setChecked(false);
				groupRoomsAreChecked = false;
			}else{
				item.setChecked(true);
				showGroupRooms();
				groupRoomsAreChecked = true;
			}
			if(lectureHallsAreChecked) showLectHalls();
			if(computerRoomsAreChecked) showCompRooms();
			return true;
		
		case R.id.action_my_location:
			customMaps.setMyPosition();
			return true;
			
		case R.id.action_modes:
			
			//TODO fix this fugly code
			ActionBar ab = getActionBar();
			if(ab.getTitle().charAt(0)=='D'){
				ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
				ab.setTitle("Nightmode");
			}else{
				ab.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
				ab.setTitle("Daymode");
			}
			
			customMaps.drawBuildings();
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
		
		inflater.inflate(R.menu.main_menu, menu);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

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
}