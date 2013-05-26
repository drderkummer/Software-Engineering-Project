package dat255.group5.chalmersonthego;

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
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.chalmersonthego.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Handles navigation on the map. Like drawing the route etc.
 * 
 * @author Anders Nordin
 */
public class NavigationManager {
	private GoogleMap map;
	TextView tvDistanceDuration;
	// Constans
	final int LINE_COLOR = Color.RED;
	final int LINE_WIDTH = 4;

	/**
	 * Constructor
	 * 
	 * @param googleMap
	 *            , instance of the google map
	 * @param owningActivity
	 *            , which GUI to be modified
	 */
	public NavigationManager(GoogleMap googleMap, Activity owningActivity) {
		this.map = googleMap;
		tvDistanceDuration = (TextView) owningActivity
				.findViewById(R.id.tv_distance_time);
	}

	/**
	 * Draw a straight line between given points
	 * 
	 * @param markerPoints
	 *            , list with all points
	 * @param start
	 *            , starting point
	 */
	public void drawLinesOnMap(ArrayList<LatLng> markerPoints) {
		MarkerOptions markerOptions = new MarkerOptions();
		PolylineOptions lineOptions = new PolylineOptions();

		markerOptions.position(markerPoints.get(0));

		lineOptions.addAll(markerPoints);
		lineOptions.color(LINE_COLOR);
		lineOptions.width(LINE_WIDTH);

		map.addPolyline(lineOptions);
		map.addMarker(markerOptions);
	}

	/**
	 * Draw a line between two given markers
	 * 
	 * @param start
	 *            , starting marker
	 * @param destination
	 *            , destination marker
	 * @return true if successful, otherwise false
	 */
	public boolean drawPathOnMap(LatLng start, LatLng destination) {
		// Checks, whether start and end locations are captured
		if (start != null && destination != null) {

			// Getting URL to the Google Directions API
			String url = createDirectionURL(start, destination);

			// Drawing polyline in the Google Map for the i:th route
			new DownloadTask().execute(url);

			return true;
		}
		return false;
	}

	/**
	 * Create URL string for later to be used in Google Maps API
	 * 
	 * @param start
	 *            , starting point
	 * @param dest
	 *            , destination point
	 * @return return URL string
	 */
	private String createDirectionURL(LatLng start, LatLng dest) {
		// Create strings
		String startStr = "origin=" + start.latitude + "," + start.longitude;
		String destStr = "destination=" + dest.latitude + "," + dest.longitude;
		String sensor = "sensor=false";
		String units_metric = "units=metric";
		String mode_walking = "mode=walking";

		// Return full URL
		return "https://maps.googleapis.com/maps/api/directions/json?"
				+ startStr + "&" + destStr + "&" + sensor + "&" + units_metric
				+ "&" + mode_walking;
	}

	/**
	 * A method to download JSON data from url
	 * 
	 * @param strUrl
	 *            , created earlier with important parameters
	 * @return JSON Data as String
	 * @throws IOException
	 *             , if something occurs during download
	 */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));
			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null)
				sb.append(line);

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	/**
	 * Takes care of the download. Most be done Async because we can't control
	 * the download flow
	 */
	private class DownloadTask extends AsyncTask<String, Void, String> {
		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Execute in UI-thread afterwards
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Calls another ASync task to parse it
			parserTask.execute(result);
		}
	}

	/**
	 * A class to parse the Google Places in JSON format
	 */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
		@Override
		/**
		 * Parsing the data in non-ui thread
		 */
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {
			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionJSONParser parser = new DirectionJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		/**
		 * Executes in UI thread, after the parsing process
		 */
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			String duration = "Not Available";
			String distance = "Not Available";

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					if (j == 0) { // Get distance from the list
						distance = (String) point.get("distance");
						continue;
					} else if (j == 1) { // Get duration from the list
						duration = (String) point.get("duration");
						continue;
					}

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(LINE_WIDTH);
				lineOptions.color(LINE_COLOR);
			}

			// As the download is finished it'll be displayed
			tvDistanceDuration.setText("Distance: " + distance + " Duration: "
					+ duration);
			tvDistanceDuration.setVisibility(TextView.VISIBLE);

			// Drawing polyline in the Google Map for the i-th route
			map.addPolyline(lineOptions);
		}
	}
}