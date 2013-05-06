package com.example.chalmersonthego;

import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class CustomGoogleMaps {

	private GoogleMap googleMap;
	//Markers currently on the map
	private HashMap<String, Marker> markers = new HashMap<String, Marker>();

	//The Activity owning the map
	Activity owningActivity;

	TextView tvDistanceDuration;
	private NavigationManager navManager;

	// Bounds the map to two points
	private LatLng northWest = new LatLng(57.697497, 11.985397);
	private LatLng southEast = new LatLng(57.678687, 11.969347);
	private LatLngBounds strictBounds = new LatLngBounds(southEast, northWest);

	/**
	 * 
	 * @param owningActivity the calling Activity
	 * @param googleMap in instance of GoogleMap
	 */
	public CustomGoogleMaps(Activity owningActivity, GoogleMap googleMap){
		this.owningActivity = owningActivity;
		this.googleMap = googleMap;

		navManager = new NavigationManager(googleMap);	

		tvDistanceDuration = (TextView) owningActivity.findViewById(R.id.tv_distance_time);		

		//Set up the map
		setUpMapIfNeeded();

	}
	/**
	 * Show the specified marker with specified information on the map
	 * @param latLng the coordinates of the marker
	 * @param title the title of the marker
	 * @param floor the floor the room is on
	 * @param type the types of the room
	 */
	void showMarkerOnMap(LatLng latLng, String title, String floor, String type) {
		if(type.equalsIgnoreCase("computer room")){
			googleMap.addMarker(new MarkerOptions()
			.position(latLng)
			.title(title)
			.snippet("floor: " + floor)
			.icon(BitmapDescriptorFactory.fromAsset("computerroom.png")));
		}else if(type.equalsIgnoreCase("lecture hall")){
			googleMap.addMarker(new MarkerOptions()
			.position(latLng)
			.title(title)
			.snippet("floor: " + floor)
			.icon(BitmapDescriptorFactory.fromAsset("lecturehall.png")));
		}else if(type.equalsIgnoreCase("group room")){
			googleMap.addMarker(new MarkerOptions()
			.position(latLng)
			.title(title)
			.snippet("floor: " + floor)
			.icon(BitmapDescriptorFactory.fromAsset("grouproom.png")));
		}else{
			googleMap.addMarker(new MarkerOptions()
			.position(latLng)
			.title(title)
			.snippet("floor: " + floor + type));
		}
	}
	//probably needed to map markers on the map and
	//just delete for ex. "computer room" markers
	private void mapMarker(Marker m){
		int mapPosition = markers.size()-1;
		// markers.put(mapPosition, m);
	};


	//not final method!
	//delete all markers from map
	void removeAllMarkerFromMap(){
		googleMap.clear();
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
		Polygon polygon = googleMap.addPolygon(rectOptions);
	}

	/**
	 * Puts a marker on map where the user's current position is
	 * @param location2 
	 * @return true if position was set, false if otherwise
	 */
	public boolean setMyPosition(){
		Location location = getCurrentPosition();

		if(location!=null){
			LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());			

			if(strictBounds.contains(myPosition)){
				Marker hereAmI = googleMap.addMarker(new MarkerOptions()
				.position(myPosition)
				.title("My Location")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.snippet("I am here"));
				hereAmI.showInfoWindow();			

				googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
				googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));

				return true;
			}
			Toast.makeText(owningActivity, "Can't display location outside campus", Toast.LENGTH_LONG).show();
		}
		return false;
	}

	/**
	 * Get current position 
	 * @return my current location
	 */
	private Location getCurrentPosition(){

		LocationManager locationManager = (LocationManager) owningActivity.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);

		return location;
	}

	/**
	 * Check if map has been instantiated, if not. Set up!
	 */
	private void setUpMapIfNeeded() {
		if(owningActivity != null && googleMap != null){
			// Check if we were successful in obtaining the map.
			if(!setMyPosition()){
				// Initialize map
				googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
				googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(57.68806,11.977978)));		
			}

			/* POSSIBLE CODE TO TEST FUNCTIONS		
					navManager.drawPathOnMap(northWest, southEast);
					tvDistanceDuration.setText(navManager.getDurationDistanceStr());	
					tvDistanceDuration.setVisibility(View.VISIBLE);
			 */
			

			// When user drag map
			googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
				@Override
				public void onCameraChange(CameraPosition position) {

					// Set minimum zoom level
					if (position.zoom < 14){
						googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
						Toast.makeText(owningActivity, "Minimum zoom", Toast.LENGTH_LONG).show();
					}

					// Limits on the map
					LatLng northWest = new LatLng(57.697497, 11.985397);
					LatLng southEast = new LatLng(57.678687, 11.969347);
					strictBounds = new LatLngBounds(southEast, northWest);

					// If position is within, do nothing.
					if (strictBounds.contains(googleMap.getCameraPosition().target))
						return;

					Toast.makeText(owningActivity, "Outside restricted area", Toast.LENGTH_LONG).show();

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
					googleMap.moveCamera(CameraUpdateFactory.newLatLng(center));
				}
			});
		}
	}


}
