package com.example.chalmersonthego;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import group5.database.DAO;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private DAO dao;
	private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Get the instance of GoogleMap
        setUpMapIfNeeded();
        
        //Open connection to the Database
        dao = new DAO(this);
        dao.open();
    }
    
    @Override
    protected void onDestroy(){
    	
    	//Close connection to the databse
    	dao.close();
    	
    	super.onDestroy();
    }
    
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                                .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                // The Map is verified. It is now safe to manipulate the map.

            }
        }
    }
}