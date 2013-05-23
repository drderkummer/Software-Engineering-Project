package com.example.chalmersonthego;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

public class CustomGoogleMaps {

	private GoogleMap googleMap;

	private ArrayList<MarkerOptions> markerOptionsArray = new ArrayList<MarkerOptions>();

	// The Activity owning the map
	final Activity owningActivity;

	TextView tvDistanceDuration;
	private NavigationManager navManager;

	// Bounds the map to two points
	private LatLng northWest = new LatLng(57.697497, 11.985397);
	private LatLng southEast = new LatLng(57.678687, 11.969347);
	private LatLngBounds strictBounds = new LatLngBounds(southEast, northWest);

	// Used for showing the custom view button in the map
	private OnInfoWindowElemTouchListener infoButtonListener;
	private ViewGroup infoWindow;
	private TextView infoTitle;
	private TextView infoSnippet;
	private Button infoButton;

	public void rePrint(){
		for (MarkerOptions m : markerOptionsArray){
			googleMap.addMarker(m);
		}
	}

	/**
	 * 
	 * @param owningActivity
	 *            the calling Activity
	 * @param googleMap
	 *            in instance of GoogleMap
	 */
	public CustomGoogleMaps(final Activity owningActivity, GoogleMap googleMap) {
		this.owningActivity = owningActivity;
		this.googleMap = googleMap;

		navManager = new NavigationManager(googleMap);

		tvDistanceDuration = (TextView) owningActivity
				.findViewById(R.id.tv_distance_time);

		// Set up the map
		setUpMapIfNeeded();

		/**
		 * Code for getting the custom info buttons to work
		 */
		// Get fragment to the custom veiws code
		final MapFragment mapFragment = (MapFragment) owningActivity.getFragmentManager()
				.findFragmentById(R.id.map);
		// TODO Fix this so that the parse goes through to get the button to work
		//final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout) findViewById(R.layout.activity_main);
		this.infoWindow = (ViewGroup) owningActivity.getLayoutInflater().inflate(
				R.layout.info_window, null);
		this.infoTitle = (TextView) infoWindow.findViewById(R.id.title);
		this.infoSnippet = (TextView) infoWindow.findViewById(R.id.snippet);
		this.infoButton = (Button) infoWindow.findViewById(R.id.button);
		this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
				owningActivity.getResources().getDrawable(R.drawable.icon_daymode),
				owningActivity.getResources().getDrawable(R.drawable.icon_nightmode)) {
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
				// Setting up the infoWindow with current's marker info
				infoTitle.setText(marker.getTitle());
				infoSnippet.setText(marker.getSnippet());
				infoButtonListener.setMarker(marker);

				// We must call this to set the current marker and infoWindow
				// references
				// to the MapWrapperLayout
				//mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
				return infoWindow;
			}
		});
		
		googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
            	// Creating an instance of MarkerOptions
                MarkerOptions markerOptions = new MarkerOptions();
 
                // Setting position for the marker
                markerOptions.position(point);
 
                // Setting custom icon for the marker
               // markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.));
 
                // Setting title for the infowindow
                markerOptions.title(point.latitude + ","+point.longitude);
 
                // Adding the marker to the map
                markerOptionsArray.add(markerOptions);
                rePrint();
            }
        });
		
		
		
	}

	/**
	 * Show the specified marker with specified information on the map
	 * 
	 * @param latLng
	 *            the coordinates of the marker
	 * @param title
	 *            the title of the marker
	 * @param floor
	 *            the floor the room is on
	 * @param type
	 *            the types of the room
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
			} else {
				markerOptions = new MarkerOptions().position(latLng)
						.title(title).snippet("floor: " + floor + type);
			}
			markerOptionsArray.add(markerOptions);
			googleMap.addMarker(markerOptions);
		}
	}

	/**
	 * removes all markers from the map
	 */
	void removeAllMarkerFromMap() {
		markerOptionsArray.clear();
		googleMap.clear();
	}

	// Drawing an building on the map
	public void drawBuildings() {
		// Instantiates a new Polygon object and adds points to define a
		// rectangle

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
	public boolean setMyPosition() {
		Location location = getCurrentPosition();

		if (location != null) {
			LatLng myPosition = new LatLng(location.getLatitude(),
					location.getLongitude());

			if (strictBounds.contains(myPosition)) {
				Marker hereAmI = googleMap
						.addMarker(new MarkerOptions()
						.position(myPosition)
						.title("My Location")
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
								.snippet("I am here"));
				hereAmI.showInfoWindow();

				googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
				googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));

				return true;
			}
			Toast.makeText(owningActivity,
					"Can't display location outside campus", Toast.LENGTH_LONG)
					.show();
		}
		return false;
	}

	/**
	 * Get current position
	 * @return my current location
	 */
	private Location getCurrentPosition() {

		LocationManager locationManager = (LocationManager) owningActivity
				.getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(new Criteria(), true);
		Location location = locationManager.getLastKnownLocation(provider);

		return location;
	}

	/**
	 * Check if map has been instantiated, if not. Set up!
	 */
	private void setUpMapIfNeeded() {
		if (owningActivity != null && googleMap != null) {
			// Check if we were successful in obtaining the map.
			if (!setMyPosition()) {
				// Initialize map
				googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
				googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
						57.68806, 11.977978)));
			}

			/*
			 * POSSIBLE CODE TO TEST FUNCTIONS
			 * navManager.drawPathOnMap(northWest, southEast);
			 * tvDistanceDuration.setText(navManager.getDurationDistanceStr());
			 * tvDistanceDuration.setVisibility(View.VISIBLE);
			 */

			// When user drag map
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

					// Limits on the map
					LatLng northWest = new LatLng(57.697497, 11.985397);
					LatLng southEast = new LatLng(57.678687, 11.969347);
					strictBounds = new LatLngBounds(southEast,
							northWest);

					// If position is within, do nothing.
					if (strictBounds.contains(googleMap
							.getCameraPosition().target))
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

			/*
			 * googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			 * 
			 * @Override public View getInfoWindow(Marker arg0) { return null; }
			 * 
			 * // Defines the contents of the InfoWindow
			 * 
			 * @Override public View getInfoContents(Marker arg0) { View v =
			 * null; // Getting the position from the marker LatLng latLng =
			 * arg0.getPosition();
			 * 
			 * return v; } });
			 */

			googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				public void onInfoWindowClick(Marker marker) {
					Location l = getCurrentPosition();
					LatLng LatLngMyPos = new LatLng(l.getLatitude(), l.getLongitude());
					if (!strictBounds.contains(LatLngMyPos))
						return;

					navManager.drawPathOnMap(LatLngMyPos,
							marker.getPosition());
				}
			});			
		}

	}
	public ArrayList<MarkerOptions> getMarkerOptionsArray() {
		return markerOptionsArray;
	}

	public void setMarkerOptionsArray(ArrayList<MarkerOptions> markerOptionsArray) {
		this.markerOptionsArray = markerOptionsArray;
	}
}
