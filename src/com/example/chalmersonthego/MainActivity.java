package com.example.chalmersonthego;

import group5.database.DAO;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements SensorEventListener {

	// Treadmill stuff
	private int steps = 0;
	private float mLimit = 10;
	private float mLastValues[] = new float[3 * 2];
	private float mScale[] = new float[2];
	private float mYOffset;

	private float mLastDirections[] = new float[3 * 2];
	private float mLastExtremes[][] = { new float[3 * 2], new float[3 * 2] };
	private float mLastDiff[] = new float[3 * 2];
	private int mLastMatch = -1;
	SensorManager mSensorManager;
	private Sensor mSensor;
	// End of treadmill

	private DAO dao;
	private CustomGoogleMaps customMaps;
	private NavigationManager navigationManager;

	// Keep track of daymode and nightmode
	Boolean nightModeOn = false;

	// A car array with all the different types of rooms, avaliable to be
	// selected in the show all menu
	protected final CharSequence[] layerOptions = { "Computer Rooms",
			"Lecture Halls", "Group Rooms", "Pubs" };
	// Used to store what layers the user has choosen to show
	protected boolean[] layerSelections = new boolean[layerOptions.length];

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		turnOnLocationsIfNeeded();

		// Get the instance of GoogleMap
		GoogleMap googleMap = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		customMaps = new CustomGoogleMaps(this, googleMap);
		navigationManager = new NavigationManager(googleMap);
		configureUI();

		// Open connection to the Database
		dao = new DAO(this);
		dao.open();
		insertDataForTheFirstTime();
	}

	/**
	 * private void customPath(LatLng from, LatLng to){ //Get length via
	 * standard API. int apiLength = 0;
	 * 
	 * //get current position in LatLng Location currentLocation =
	 * customMaps.getCurrentPosition(); LatLng currentCordinates = new
	 * LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
	 * 
	 * //Get the cloesest Entry from current position to all buildings //get all
	 * buildings ArrayList<String> buildings = dao.getAllFromTable4(); //Loop
	 * over all buildings
	 * 
	 * for(String building : buildings){ LatLng cloestEntry =
	 * dao.getClosestEntry(building, currentCordinates);
	 * 
	 * //Calculate path length to the closestEntry int length = 0; //Get length
	 * via API to this entry if(length < apiLength){ LatLng closestOut =
	 * dao.getClosestEntry(building, to); length += 0; //=
	 * getDistance(cloestEntry,closestOut) if(length <apiLength){ length +=
	 * 0;//getAPILength(closestOut,to); } } }
	 * 
	 * }
	 **/

	/**
	 * This method is used for all configuration of the UI which can't be done
	 * in the xml except the configuration of the map.
	 */
	private void configureUI() {
		ActionBar ab = getActionBar();
		// Getting the icon clickable
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
	}

	/**
	 * This functions checks if the gps is enabled. If it's not the user is
	 * prompted and redirected to turn it on.
	 */
	private void turnOnLocationsIfNeeded() {
		// Prompt the user to turn on gps and other location handlers.
		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			String message = "Enable either GPS or any other location"
					+ " service to find current location.";
			builder.setMessage(message)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface d, int id) {
									startActivity(new Intent(
											Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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
	public void insertDataForTheFirstTime() {
		String firstTime = "firstTime";
		boolean isFirstTime = true;
		SharedPreferences prefs = getSharedPreferences("com.example.android", 0);
		if (prefs.getBoolean(firstTime, isFirstTime)) {
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
	 * Searches for rooms, types and buildings. Priority by order. Room: plot
	 * that room Type: plot all rooms with that type Building: plot the closest
	 * entry
	 * 
	 * @param searchString
	 */
	private void doMySearch(String searchString) {
		LatLng latLng = dao.getRoomCoordinates(searchString);
		ArrayList<String> list = dao.getAllRoomsWithType(searchString);
		// Your current coordinates should be put in the following line
		LatLng currentCoordinates = new LatLng(0.0, 0.0);
		LatLng closestEntry = dao.getClosestEntry(searchString,
				currentCoordinates);
		if (latLng != null) {
			String rname = dao.getName(latLng.latitude, latLng.longitude);
			customMaps.showMarkerOnMap(latLng, rname, dao.getFloor(rname),
					dao.getType(rname));
		} else if (list != null) {
			for (String name : list) {
				latLng = dao.getRoomCoordinates(name);
				customMaps.showMarkerOnMap(latLng, name, dao.getFloor(name),
						dao.getType(name));
			}
		} else if (closestEntry != null) {
			customMaps.showMarkerOnMap(closestEntry, "Put description here",
					null, null);
		} else {
			Toast.makeText(this, searchString + " is not in the database",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	protected void onDestroy() {
		// Close connection to the database
		dao.close();
		super.onDestroy();
	}

	/**
	 * Following method is called when launcher icon clicked
	 * 
	 * @param MenuItem
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_layers:
			Dialog dialog = onCreateDialog(0);
			dialog.show();
			return true;

		case R.id.action_my_location:
			customMaps.setMyPosition();
			return true;

		case R.id.exit:
			finish();
			return true;

		case R.id.action_treadmill:
			startTreadmill();
			return true;

		default:
			Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT)
					.show();
			return true;
		}
	}

	private void startTreadmill() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_GAME);

		int h = 480;
		mYOffset = h * 0.5f;
		mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
		mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the options menu from XML
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		ActionBar ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setQueryRefinementEnabled(true);
		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		// Do not iconify the widget; expand it by default
		searchView.setIconifiedByDefault(false);
		return true;
	}

	@Override
	public void onBackPressed() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
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

	/**
	 * Switch from nightmode to daymode in the app
	 */
	private void switchMode() {
		ActionBar ab = getActionBar();
		if (nightModeOn) {
			// Changing the apperance
			ab.setTitle("Daymode");
			ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(135, 206, 250)));
			ab.setSplitBackgroundDrawable(new ColorDrawable(Color.rgb(135, 206,
					250)));
			ab.setIcon(R.drawable.icon_daymode);
			ab.show();
			customMaps.removeAllMarkerFromMap();
			nightModeOn = false;
		} else {
			ab.setTitle("Nightmode");
			ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
			ab.setSplitBackgroundDrawable(new ColorDrawable(Color.BLACK));
			ab.setIcon(R.drawable.icon_nightmode);
			ab.show();
			customMaps.drawBuildings();
			nightModeOn = true;
		}
	}

	/**
	 * Show all rooms function. Builds upon what layers have been selected in
	 * the popupmenu, and then stored in layerSelection.
	 */
	private void showRooms() {

		if (layerSelections[0]) {
			ArrayList<String> r1 = dao.getAllRoomsWithType("computer room");
			for (int i = 0; i < r1.size(); i++) {
				LatLng coords = dao.getRoomCoordinates(r1.get(i));
				String name = dao.getName(coords.latitude, coords.longitude);
				customMaps.showMarkerOnMap(coords, name, dao.getFloor(name),
						"computer room");
			}
		}
		if (layerSelections[1]) {
			ArrayList<String> r3 = dao.getAllRoomsWithType("lecture hall");
			for (int i = 0; i < r3.size(); i++) {
				LatLng coords = dao.getRoomCoordinates(r3.get(i));
				String name = dao.getName(coords.latitude, coords.longitude);
				customMaps.showMarkerOnMap(coords, name, dao.getFloor(name),
						"lecture hall");
			}
		}
		if (layerSelections[2]) {
			ArrayList<String> r2 = dao.getAllRoomsWithType("group room");
			for (int i = 0; i < r2.size(); i++) {
				LatLng coords = dao.getRoomCoordinates(r2.get(i));
				String name = dao.getName(coords.latitude, coords.longitude);
				customMaps.showMarkerOnMap(coords, name, dao.getFloor(name),
						"group room");
			}
		}
		if (layerSelections[3]) {
			ArrayList<String> r4 = dao.getAllRoomsWithType("pub");
			for (int i = 0; i < r4.size(); i++) {
				LatLng coords = dao.getRoomCoordinates(r4.get(i));
				String name = dao.getName(coords.latitude, coords.longitude);
				customMaps.showMarkerOnMap(coords, name, dao.getFloor(name),
						"pub");
			}
			customMaps.drawBuildings();
		}
	}

	/**
	 * @see android.app.Activity#onCreateDialog(int)
	 * @param id
	 *            of the popupmenu to be created
	 * 
	 *            Creates a popup dialog
	 **/
	protected Dialog onCreateDialog(int id) {
		return new AlertDialog.Builder(this)
				.setTitle("Show all on map")
				.setMultiChoiceItems(layerOptions, layerSelections,
						new DialogSelectionClickHandler())
				.setPositiveButton("OK", new DialogButtonClickHandler())
				.create();
	}

	public class DialogSelectionClickHandler implements
			DialogInterface.OnMultiChoiceClickListener {
		public void onClick(DialogInterface dialog, int clicked,
				boolean selected) {
			Log.i("ME", layerOptions[clicked] + " selected: " + selected);
		}
	}

	/**
	 * 
	 * @author Niklas
	 * 
	 *         Handles buttons on popupmenus
	 */
	public class DialogButtonClickHandler implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int clicked) {
			switch (clicked) {
			case DialogInterface.BUTTON_POSITIVE:
				customMaps.removeAllMarkerFromMap();
				showRooms();
				break;
			}
		}
	}

	/**
	 * Treadmill stuff
	 * 
	 * @param event
	 */
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		synchronized (this) {
			int j = (sensor.getType() == Sensor.TYPE_ACCELEROMETER) ? 1 : 0;
			if (j == 1) {
				float vSum = 0;
				for (int i = 0; i < 3; i++) {
					final float v = mYOffset + event.values[i] * mScale[j];
					vSum += v;
				}
				int k = 0;
				float v = vSum / 3;

				float direction = (v > mLastValues[k] ? 1
						: (v < mLastValues[k] ? -1 : 0));
				if (direction == -mLastDirections[k]) {
					// Direction changed
					int extType = (direction > 0 ? 0 : 1); // minumum or
															// maximum?
					mLastExtremes[extType][k] = mLastValues[k];
					float diff = Math.abs(mLastExtremes[extType][k]
							- mLastExtremes[1 - extType][k]);

					if (diff > mLimit) {

						boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
						boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
						boolean isNotContra = (mLastMatch != 1 - extType);

						if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough
								&& isNotContra) {
							// Step confirmed!
							Log.i("main", "step");
							steps++;
							getActionBar().setTitle("You have taken " + steps + " steps.");
							mLastMatch = extType;
						} else {
							mLastMatch = -1;
						}
					}
					mLastDiff[k] = diff;
				}
				mLastDirections[k] = direction;
				mLastValues[k] = v;
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}
}
