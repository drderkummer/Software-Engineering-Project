package com.example.chalmersonthego;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import group5.database.DAO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DAO dao;
	private GoogleMap map;
	private LatLngBounds strictBounds;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Get the instance of GoogleMap
		setUpMapIfNeeded();

		//Open connection to the Database
		dao = new DAO(this);
		dao.open();
		
		//Getting the icon clickable
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onDestroy(){

		//Close connection to the database
		dao.close();

		super.onDestroy();
	}
	
	//Following method is called when launcher icon clicked
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "CLICK", Toast.LENGTH_LONG).show();
		return true;
    }
	

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (map == null) {
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (map != null) {// The Map is verified. It is now safe to manipulate the map.
				
				
				
				
				// When user drag map
				map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
		            @Override
		            public void onCameraChange(CameraPosition position) {	            	
		            	
		            	// Set minimum zoom level
		            	if(position.zoom < 15)
							map.animateCamera(CameraUpdateFactory.zoomTo(15));	         	
		            	
		            	
						// Limits on the map
						LatLng northWest = new LatLng(57.697497,11.985397);
						LatLng southEast = new LatLng(57.678687,11.969347);
						strictBounds = new LatLngBounds(southEast,northWest); 
						
						// If position is within, do nothing.
		            	if(strictBounds.contains(map.getCameraPosition().target))
		            		return;
		            	     	
		            	// Seems that we are out of bound 
		            	double x = map.getCameraPosition().target.latitude;
		            	double y = map.getCameraPosition().target.longitude;
						
						if(x < southEast.latitude)
							x = southEast.latitude;
						if(x > northWest.latitude)	
							x = northWest.latitude;
						if(y < southEast.longitude)
							y = southEast.longitude;
						if(y > northWest.longitude)
							y = northWest.longitude;
						
						LatLng center = new LatLng(x,y);						
						
						// Set new center
						map.moveCamera(CameraUpdateFactory.newLatLng(center));						
		            }
		        });
			}
		}
	}

}