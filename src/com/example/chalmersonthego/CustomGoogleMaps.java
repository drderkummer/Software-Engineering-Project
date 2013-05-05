package com.example.chalmersonthego;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class CustomGoogleMaps {
	
	private GoogleMap googleMap;
	//Markers currently on the map
	private HashMap<Integer, Marker> markers = new HashMap<Integer, Marker>();

	//The Activity owning the map
	Activity owningActivity;
	
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
		markers.put(mapPosition, m);
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
	boolean setMyPosition(Location location){
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
		}
		return false;
	}
	

	Location getCurrentPosition(){
		// Getting LocationManager object from System Service LOCATION_SERVICE
		LocationManager locationManager = (LocationManager) owningActivity.getSystemService(owningActivity.LOCATION_SERVICE);
		// Creating a criteria object to retrieve provider
		Criteria criteria = new Criteria();
		// Getting the name of the best provider
		String provider = locationManager.getBestProvider(criteria, true);
		// Getting Current Location
		Location location = locationManager.getLastKnownLocation(provider);
		
		return location;
	}
	
	private void setUpMapIfNeeded() {
		if(owningActivity != null && googleMap != null){
			// Check if we were successful in obtaining the map.
				if(!setMyPosition(getCurrentPosition())){
					// Initialize map
					googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
					googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(57.68806,11.977978)));		
				}					

				// When user drag map
				googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
					@Override
					public void onCameraChange(CameraPosition position) {

						// Set minimum zoom level
						if (position.zoom < 15)
							googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

						// Limits on the map
						LatLng northWest = new LatLng(57.697497, 11.985397);
						LatLng southEast = new LatLng(57.678687, 11.969347);
						strictBounds = new LatLngBounds(southEast, northWest);

						// If position is within, do nothing.
						if (strictBounds.contains(googleMap.getCameraPosition().target))
							return;

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
