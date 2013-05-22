package com.example.chalmersonthego;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.widget.Toast;

public class ICalReader {
	private String iCal;
	private Activity owningActivity;
	private long enqueue;
	private DownloadManager dm;
	URL page;

	public ICalReader(Activity owningActivity) {
		this.owningActivity = owningActivity;
	}

	public void getWebpageSource(String url) {
		Toast.makeText(owningActivity, url, Toast.LENGTH_LONG).show();
		parseHtml("apa");
		try {
			HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
			HttpGet httpget = new HttpGet(url); // Set the action you want to do
			HttpResponse response = httpclient.execute(httpget); // Execute it
			HttpEntity entity = response.getEntity(); 
			InputStream is = entity.getContent(); // Create an InputStream with the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) // Read line by line
			    sb.append(line + "\n");
			
			String resString = sb.toString(); // Result is here
			is.close(); // Close the stream
			parseHtml(resString);
		} catch (ClientProtocolException e) {
			Toast.makeText(owningActivity, "Error when fetching http",
					Toast.LENGTH_LONG).show();
		} catch (IllegalArgumentException e) {
			Toast.makeText(owningActivity,
					"Error in http, please correct the input and try again",
					Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(owningActivity, "Error when reading from web",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(owningActivity, "Undefined error, try again",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
		
	}

	private void parseHtml(String resString) {
		Calendar c = Calendar.getInstance();
		String day = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
		int yy = c.get(Calendar.YEAR);
	    int mm = c.get(Calendar.MONTH);
	    int dd = c.get(Calendar.DAY_OF_MONTH);
	    String startparse;
	    if(mm<10 && dd<10){
	    	startparse= day +" "+ yy +"-0"+mm+"-0"+dd;
	    }else if(mm<10){
	    	startparse= day +" "+ yy +"-0"+mm+"-"+dd;
	    }else if(dd<10){
	    	startparse= day +" "+ yy +"-"+mm+"-0"+dd;
	    }else{
	    	startparse= day +" "+ yy +"-"+mm+"-"+dd;
	    }
	    
		 
		Toast.makeText(owningActivity,startparse ,
				Toast.LENGTH_LONG).show();
		}

	/**
	 * Downloads the iCal to the phone locally
	 * 
	 * @param url
	 *            , a string with the url to the downloadpage. Has to be a
	 *            direct downloadpath.
	 */
	public void downloadICal(String url) {
		URL requestURL;
		try {
			dm = (DownloadManager) owningActivity.getSystemService("download");
			Request request = new Request(Uri.parse(url));
			enqueue = dm.enqueue(request);

		} catch (Exception e) {
			Toast.makeText(owningActivity, "Error in URL, try again",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		BroadcastReceiver reciever = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {

				}
			}
		};
	}

}
