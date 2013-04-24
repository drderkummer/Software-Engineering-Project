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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private DAO dao;
	private GoogleMap map;
	private LatLngBounds strictBounds;
	private DialogInterface.OnClickListener dialogClickListener;

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

		// Listener for exit event
		dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					finish();
					break;
				}
			}
		};

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
		case R.id.exit:
		case android.R.id.home:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to exit?")
					.setPositiveButton("Yes", dialogClickListener)
					.setNegativeButton("No", dialogClickListener).show();
			return true;

		case R.id.showLectureHalls: case R.id.showComputerRooms:
			if (item.isChecked()) {
				item.setChecked(false);
				//Call remove dots function
			} else {
				item.setChecked(true);
				//Call add dots function
			}
			return true;

		case R.id.action_search: case R.id.action_layers:
			
			return true;
			

		default:
			Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT)
					.show();
			return true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {

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
			if (map != null) {// The Map is verified. It is now safe to
								// manipulate the map.

				// When user drag map
				map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
					@Override
					public void onCameraChange(CameraPosition position) {

						// Set minimum zoom level
						if (position.zoom < 15)
							map.animateCamera(CameraUpdateFactory.zoomTo(15));

						// Limits on the map
						LatLng northWest = new LatLng(57.697497, 11.985397);
						LatLng southEast = new LatLng(57.678687, 11.969347);
						strictBounds = new LatLngBounds(southEast, northWest);

						// If position is within, do nothing.
						if (strictBounds.contains(map.getCameraPosition().target))
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

						LatLng center = new LatLng(x, y);

						// Set new center
						map.moveCamera(CameraUpdateFactory.newLatLng(center));
					}
				});
			}
		}
	}

}