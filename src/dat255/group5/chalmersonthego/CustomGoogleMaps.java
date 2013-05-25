package dat255.group5.chalmersonthego;

import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chalmersonthego.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import dat255.group5.database.DAO;

public class CustomGoogleMaps {

	// Constants
	final GoogleMap googleMap;
	final Activity owningActivity;
	private DAO dao;
	final LatLng northWest = new LatLng(57.697497, 11.985397);
	final LatLng southEast = new LatLng(57.678687, 11.969347);

	private ArrayList<MarkerOptions> markerOptionsArray = new ArrayList<MarkerOptions>();
	public Marker highlighted, hereAmI;

	private NavigationManager navManager;

	// Used for showing the custom view button in the map
	private OnInfoWindowElemTouchListener infoButtonListener;
	private ViewGroup infoWindow;
	private TextView infoTitle, infoSnippet;
	private Button infoButton;

	/**
	 * Redraw all markers on the map.
	 */
	public void reDrawMarkers() {
		googleMap.clear();

		for (MarkerOptions m : markerOptionsArray)
			googleMap.addMarker(m);
	}

	/**
	 * Constructor.
	 * 
	 * @param owningActivity
	 *            , the calling Activity
	 * @param googleMap
	 *            , in instance of GoogleMap
	 */
	public CustomGoogleMaps(final Activity owningActivity, GoogleMap googleMap,
			DAO dao) {
		this.owningActivity = owningActivity;
		this.googleMap = googleMap;
		this.dao = dao;

		navManager = new NavigationManager(googleMap, owningActivity);

		setUpMapIfNeeded();

		// Get fragment to the custom veiws code
		final MapFragment mapFragment = (MapFragment) owningActivity
				.getFragmentManager().findFragmentById(R.id.map);
		// TODO Fix this so that the parse goes through to get the button to
		// work
		// final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)
		// findViewById(R.layout.activity_main);
		this.infoWindow = (ViewGroup) owningActivity.getLayoutInflater()
				.inflate(R.layout.info_window, null);
		this.infoTitle = (TextView) infoWindow.findViewById(R.id.title);
		this.infoSnippet = (TextView) infoWindow.findViewById(R.id.snippet);
		this.infoButton = (Button) infoWindow.findViewById(R.id.button);
		this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
				owningActivity.getResources().getDrawable(
						R.drawable.icon_daymode), owningActivity.getResources()
						.getDrawable(R.drawable.icon_nightmode)) {
			@Override
			protected void onClickConfirmed(View v, Marker marker) {
				// Here we can perform some action triggered after clicking the
				// button
				Toast.makeText(owningActivity,
						marker.getTitle() + "'s button clicked!",
						Toast.LENGTH_SHORT).show();
			}
		};

		// Further setting up the customizeable infowindows on the map
		this.infoButton.setOnTouchListener(infoButtonListener);
		googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				infoTitle.setText(marker.getTitle());
				infoSnippet.setText(marker.getSnippet());
				infoButtonListener.setMarker(marker);

				return infoWindow;
			}
		});

		// Creates a marker for target position. Possible to drag around.
		googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
			@Override
			public void onMapLongClick(LatLng point) {
				if (highlighted != null)
					highlighted.remove();

				highlighted = CustomGoogleMaps.this.googleMap
						.addMarker(new MarkerOptions().position(point)
								.title("Targeted position").draggable(true)
								.snippet("You've marked this location"));

				highlighted.showInfoWindow();
			}
		});

		// When user click info window on marker. Show navigation options
		googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			public void onInfoWindowClick(Marker marker) {
				printNavigationPopup(marker.getPosition(), "Target Position");
			}
		});
	}

	/**
	 * Checks whether given location is inside the limited area
	 * 
	 * @param location
	 *            , given location to test
	 * @return true if inside, otherwise false
	 */
	public boolean isLocationInBound(LatLng latlng) {
		// Bounds the map to a given area
		LatLngBounds strictBounds = new LatLngBounds(southEast, northWest);

		return (strictBounds.contains(latlng));
	}

	/*
	 * 
	 * Om man är på campus (Min Pos, tomt) annars (tomt, tomt) Anropad från
	 * markör - (Markör, tomt) KLICK OK: - Printa vägen + duration + distance
	 */
	public void printNavigationPopup(final LatLng latlng,
			final String wantToGo) {

		// Create Dialog
		final Dialog myDialog = new Dialog(owningActivity);
		myDialog.setContentView(R.layout.navigation_dialog);

		// Init view
		final MultiAutoCompleteTextView navStart = (MultiAutoCompleteTextView) myDialog
				.findViewById(R.id.nav_search_start);
		final MultiAutoCompleteTextView navDest = (MultiAutoCompleteTextView) myDialog
				.findViewById(R.id.nav_search_dest);
		Button navOk = (Button) myDialog.findViewById(R.id.navOk);
		Button navCancel = (Button) myDialog.findViewById(R.id.navCancel);
		
		myDialog.setTitle("Show route");

		ArrayList<String> allSearchStrings = dao.getAllRooms();
		
		if(isLocationInBound(getCurrentLocation())){		
			navStart.setText("My Position");
			allSearchStrings.add("My Position");
		}
		
		if(wantToGo.equals("Target Position")){
			navDest.setText(wantToGo);			
			allSearchStrings.add("Target Position");
		}
		
		// Init with alla searchable strings
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(owningActivity,
				android.R.layout.simple_dropdown_item_1line, allSearchStrings);
		
		

		navStart.setAdapter(adapter);
		navDest.setAdapter(adapter);

		// Set ending char
		navStart.setTokenizer(new SpaceTokenizer());
		navDest.setTokenizer(new SpaceTokenizer());

		navOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Get final search string
				String start = navStart.getText().toString()
						.replaceAll("\\s", "");
				String dest = navDest.getText().toString()
						.replaceAll("\\s", "");

				LatLng _latlng = latlng;
				
				if(!arrivedFrom.equals("Target Position") && !arrivedFrom.equals("My Position"))
					_latlng = dao.getRoomCoordinates(start);			


				if (!wantToGo.equals("Target Position")
						&& !wantToGo.equals("My Position"))
					_latlng = dao.getRoomCoordinates(start);

				if (navManager.drawPathOnMap(_latlng,
						dao.getRoomCoordinates(dest)))
					Toast.makeText(owningActivity, "Success",
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(owningActivity, "FAILURE",
							Toast.LENGTH_SHORT).show();
				myDialog.dismiss();
			}
		});

		navCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				myDialog.cancel();
			}
		});

		myDialog.show();
	}

	/**
	 * Show the specified marker with specified information on the map
	 * 
	 * @param latLng
	 *            , the coordinates of the marker
	 * @param title
	 *            , the title of the marker
	 * @param floor
	 *            , the floor the room is on
	 * @param type
	 *            , the types of the room
	 */
	void showMarkerOnMap(LatLng latLng, String title, String floor, String type) {
		if ((latLng != null && title != null && floor != null && type != null)) {
			MarkerOptions markerOptions;
			if (type.equalsIgnoreCase("computer room")) {
				markerOptions = new MarkerOptions()
						.position(latLng)
						.title(title)
						.snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory
								.fromAsset("computerroom.png"));
			} else if (type.equalsIgnoreCase("lecture hall")) {
				markerOptions = new MarkerOptions()
						.position(latLng)
						.title(title)
						.snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory
								.fromAsset("lecturehall.png"));
			} else if (type.equalsIgnoreCase("group room")) {
				markerOptions = new MarkerOptions()
						.position(latLng)
						.title(title)
						.snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory
								.fromAsset("grouproom.png"));
			} else if (type.equalsIgnoreCase("sauna")) {
				markerOptions = new MarkerOptions().position(latLng)
						.title(title).snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory.fromAsset("sauna.png"));
			} else if (type.equalsIgnoreCase("restaurant")) {
				markerOptions = new MarkerOptions()
						.position(latLng)
						.title(title)
						.snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory
								.fromAsset("restaurant.png"));
			} else if (type.equalsIgnoreCase("pub")) {
				markerOptions = new MarkerOptions().position(latLng)
						.title(title).snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory.fromAsset("pub.png"));
			} else if (type.equalsIgnoreCase("gym")) {
				markerOptions = new MarkerOptions().position(latLng)
						.title(title).snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory.fromAsset("gym.png"));
			} else if (type.equalsIgnoreCase("cinema")) {
				markerOptions = new MarkerOptions().position(latLng)
						.title(title).snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory.fromAsset("cinema.png"));
			} else if (type.equalsIgnoreCase("billiard room")) {
				markerOptions = new MarkerOptions()
						.position(latLng)
						.title(title)
						.snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory.fromAsset("billiard.png"));
			} else if (type.equalsIgnoreCase("atm")) {
				markerOptions = new MarkerOptions().position(latLng)
						.title(title).snippet("floor: " + floor)
						.icon(BitmapDescriptorFactory.fromAsset("atm.png"));
			} else {
				markerOptions = new MarkerOptions().position(latLng)
						.title(title).snippet("floor: " + floor + type);
			}
			markerOptionsArray.add(markerOptions);
			googleMap.addMarker(markerOptions);
		}
	}

	/**
	 * Removes all markers from array and map.
	 */
	void removeAllMarkerFromMap() {
		markerOptionsArray.clear();
		navManager.tvDistanceDuration.setVisibility(TextView.GONE);
		googleMap.clear();
	}

	/**
	 * Instantiates a new Polygon object and adds points to define a rectangle
	 */
	public void drawBuildings() {
		// Draw NC
		PolygonOptions nc = new PolygonOptions();
		nc.add(new LatLng(57.687141, 11.978657));
		nc.add(new LatLng(57.687376, 11.978448));
		nc.add(new LatLng(57.687438, 11.978673));
		nc.add(new LatLng(57.687199, 11.97889));
		nc.strokeColor(Color.rgb(170, 69, 0));
		nc.fillColor(Color.rgb(170, 69, 0));
		googleMap.addPolygon(nc);

		// Draw Golden I
		PolygonOptions gi = new PolygonOptions();
		gi.add(new LatLng(57.692787, 11.975036));
		gi.add(new LatLng(57.692847, 11.975272));
		gi.add(new LatLng(57.693002, 11.975139));
		gi.add(new LatLng(57.692960, 11.974888));
		gi.fillColor(Color.rgb(138, 43, 226));
		gi.strokeColor(Color.rgb(138, 43, 226));
		googleMap.addPolygon(gi);
	}

	/**
	 * Puts a marker on map where the user's current position is
	 * 
	 * @return true if position was set, false if otherwise
	 */
	public boolean drawMyPosition() {
		LatLng latlng = getCurrentLocation();

		if (latlng != null) {
			Location location = new Location("Current Position");
			location.setLatitude(latlng.latitude);
			location.setLongitude(latlng.longitude);

			// If position is in range. Print out a marker.
			if (isLocationInBound(latlng)) {
				if (hereAmI != null)
					hereAmI.remove();

				MarkerOptions markeroptions = new MarkerOptions()
						.position(latlng)
						.title("My Location")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
						.snippet("I am here");
				hereAmI = CustomGoogleMaps.this.googleMap
						.addMarker(markeroptions);
				hereAmI.showInfoWindow();
				markerOptionsArray.add(markeroptions);

				googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
				googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));

				return true;
			}
			Toast.makeText(owningActivity, "You are not on campus",
					Toast.LENGTH_LONG).show();
		}
		return false;
	}

	/**
	 * Returns the current location. Does not check if position is in bound.
	 * 
	 * @return user's current location
	 */
	public LatLng getCurrentLocation() {
		LocationManager locationManager = (LocationManager) owningActivity
				.getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(new Criteria(), true);
		Location location = locationManager.getLastKnownLocation(provider);

		return new LatLng(location.getLatitude(), location.getLongitude());
	}

	private void setUpMapIfNeeded() {
		// Check if map has been instantiated, if not. Set up!
		if (owningActivity != null && googleMap != null) {
			if (!drawMyPosition()) {
				// Initialize map center
				googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
				googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
						57.68806, 11.977978)));
			}

			// When user change the map view
			googleMap
					.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
						@Override
						public void onCameraChange(CameraPosition position) {
							// Set minimum zoom level
							if (position.zoom < 14) {
								googleMap.animateCamera(CameraUpdateFactory
										.zoomTo(14));
								Toast.makeText(owningActivity, "Minimum zoom",
										Toast.LENGTH_LONG).show();
							}

							// If position is within, do nothing.
							if (isLocationInBound(googleMap.getCameraPosition().target))
								return;

							Toast.makeText(owningActivity,
									"Outside restricted area",
									Toast.LENGTH_LONG).show();

							// Seems that we are out of bound
							double x = googleMap.getCameraPosition().target.latitude;
							double y = googleMap.getCameraPosition().target.longitude;

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
							googleMap.moveCamera(CameraUpdateFactory
									.newLatLng(center));
						}
					});
		}
	}

	/**
	 * Get all markers
	 * 
	 * @return arrayList with all markersOptions
	 */
	public ArrayList<MarkerOptions> getMarkerOptionsArray() {
		return markerOptionsArray;
	}

	/**
	 * Set all markers to array
	 * 
	 * @param markerOptionsArray
	 *            containing all markers
	 */
	public void setMarkerOptionsArray(
			ArrayList<MarkerOptions> markerOptionsArray) {
		this.markerOptionsArray = markerOptionsArray;
	}
}
