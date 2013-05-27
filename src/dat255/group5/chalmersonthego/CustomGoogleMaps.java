package dat255.group5.chalmersonthego;

import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dat255.group5.database.DAO;

/**
 * Mainly responsible to manipulate the map.
 * @author Anders Nordin
 */
public class CustomGoogleMaps {

	// Constants
	final GoogleMap googleMap;
	final Activity owningActivity;
	private DAO dao;
	final LatLng northEast = new LatLng(57.693648,11.980124);
	final LatLng southWest = new LatLng(57.681467,11.976304);
	
	private ArrayList<MarkerOptions> markerOptionsArray = new ArrayList<MarkerOptions>();
	public Marker highlighted, hereAmI;
	private NavigationManager navManager;

	// Used for showing the custom view button in the map
	private ViewGroup infoWindow;
	private TextView infoTitle, infoSnippet;

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

		this.infoWindow = (ViewGroup) owningActivity.getLayoutInflater()
				.inflate(R.layout.info_window, null);
		this.infoTitle = (TextView) infoWindow.findViewById(R.id.title);
		this.infoSnippet = (TextView) infoWindow.findViewById(R.id.snippet);

		googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				infoTitle.setText(marker.getTitle());
				infoSnippet.setText(marker.getSnippet());

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
				printNavigationPopup(marker.getPosition(), marker.getTitle());
			}
		});
	}
	
	/**
	 * Redraw all markers on the map.
	 */
	public void reDrawMarkers() {
		googleMap.clear();

		for (MarkerOptions m : markerOptionsArray)
			googleMap.addMarker(m);
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
		LatLngBounds strictBounds = new LatLngBounds(southWest, northEast);

		return (strictBounds.contains(latlng));
	}

	/**
	 * Shows the navigation dialog
	 * @param latlng with triggered marker
	 * @param wantToGo where the user wants to go
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

		// Add suggestions depending on where the user clicked.
		if(isLocationInBound(getCurrentLocation())){		
			navStart.setText("My Position");
			allSearchStrings.add("My Position");
		}		
		if(!wantToGo.equals("My Position")){
			navDest.setText(wantToGo);			
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
				String start = navStart.getText().toString();
				start = SpaceTokenizer.removeLastCharIfSpace(start);
				
				String dest = navDest.getText().toString();
				dest = SpaceTokenizer.removeLastCharIfSpace(dest);

				LatLng startCoord;
				LatLng destCoord;

				if(start.equals("My Position"))
					startCoord = getCurrentLocation();	
				else
					startCoord = dao.getRoomCoordinates(start);	
				
				if(dest.equals("My Position") || dest.equals("Targeted position"))
					destCoord = latlng;
				else
					destCoord = dao.getRoomCoordinates(dest);	
				
				if(start.equals("My Position") && dest.equals("Targeted position"))
					startCoord = getCurrentLocation();
				
				if (navManager.drawPathOnMap(startCoord,destCoord)){
					removeAllMarkerFromMap();
					navManager.drawPathOnMap(startCoord,destCoord);

					Marker startMarker = CustomGoogleMaps.this.googleMap
							.addMarker(new MarkerOptions().position(startCoord)
									.title("Start").draggable(true)
									.snippet("Start Location"));					
					startMarker.showInfoWindow();
					
					Marker destMarker = CustomGoogleMaps.this.googleMap
							.addMarker(new MarkerOptions().position(destCoord)
									.title("Destination").draggable(true)
									.snippet("Destination Location"));					
					destMarker.showInfoWindow();					
				}
				else
					Toast.makeText(owningActivity, "Could not display navigation",
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
				markerOptions = new MarkerOptions()
				 .position(latLng)
				 .title(title).snippet("floor: " + floor)
				 .icon(BitmapDescriptorFactory
						.fromAsset("sauna.png"));
			} else if (type.equalsIgnoreCase("restaurant")) {
				markerOptions = new MarkerOptions()
				.position(latLng)
				.title(title)
				.snippet("floor: " + floor)
				.icon(BitmapDescriptorFactory
						.fromAsset("restaurant.png"));
			} else if (type.equalsIgnoreCase("pub")) {
				markerOptions = new MarkerOptions()
				.position(latLng)
				.title(title).snippet("floor: " + floor)
				.icon(BitmapDescriptorFactory
						.fromAsset("pub.png"));
			} else if (type.equalsIgnoreCase("gym")) {
				markerOptions = new MarkerOptions()
				.position(latLng)
				.title(title).snippet("floor: " + floor)
				.icon(BitmapDescriptorFactory
						.fromAsset("gym.png"));
			} else if (type.equalsIgnoreCase("cinema")) {
				markerOptions = new MarkerOptions()
				.position(latLng)
				.title(title).snippet("floor: " + floor)
				.icon(BitmapDescriptorFactory
						.fromAsset("cinema.png"));
			} else if (type.equalsIgnoreCase("billiard room")) {
				markerOptions = new MarkerOptions()
				.position(latLng)
				.title(title)
				.snippet("floor: " + floor)
				.icon(BitmapDescriptorFactory
						.fromAsset("billiard.png"));
			} else if (type.equalsIgnoreCase("atm")) {
				markerOptions = new MarkerOptions()
				.position(latLng)
				.title(title).snippet("floor: " + floor)
				.icon(BitmapDescriptorFactory
						.fromAsset("atm.png"));	
			} else if (type.equalsIgnoreCase("entrance")) {
				markerOptions = new MarkerOptions()
				.position(latLng)
				.title(title).snippet("")
				.icon(BitmapDescriptorFactory
						.fromAsset("entrance.png"));
			} else if (type.equalsIgnoreCase("microwave")){
				markerOptions = new MarkerOptions()
				.position(latLng)
				.title(title).snippet("floor: " + floor)
				.icon(BitmapDescriptorFactory
					.fromAsset("microwave.png"));
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
						.fromAsset("map_blue_dot.png"))
						.snippet("I am here");
				hereAmI = CustomGoogleMaps.this.googleMap
						.addMarker(markeroptions);
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
		LocationManager service = (LocationManager) owningActivity.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = service.getBestProvider(criteria, false);
		Location location = service.getLastKnownLocation(provider);
		// return(new LatLng(location.getLatitude(),location.getLongitude()));
		return(new LatLng(57.687679,11.979036));
	}

	/**
	 * If needed initiate the map
	 */
	private void setUpMapIfNeeded() {
		// Check if map has been instantiated, if not. Set up!
		if (owningActivity != null && googleMap != null) {
			if (!drawMyPosition()) {
				// Initialize map center
				googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
				googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
						57.688326,11.978064)));
			}

			// When user change the map view
			googleMap
			.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
				@Override
				public void onCameraChange(CameraPosition position) {
					// Set minimum zoom level
					if (position.zoom < 15) {
						googleMap.animateCamera(CameraUpdateFactory
								.zoomTo(15));
						Toast.makeText(owningActivity, "Minimum zoom",
								Toast.LENGTH_LONG).show();
					}

					// If position is within, do nothing.
					if (isLocationInBound(googleMap.getCameraPosition().target))
						return;

					// Seems that we are out of bound
					double x = googleMap.getCameraPosition().target.latitude;
					double y = googleMap.getCameraPosition().target.longitude;

					if (x < southWest.latitude)
						x = southWest.latitude;
					if (x > northEast.latitude)
						x = northEast.latitude;
					if (y < southWest.longitude)
						y = southWest.longitude;
					if (y > northEast.longitude)
						y = northEast.longitude;

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
