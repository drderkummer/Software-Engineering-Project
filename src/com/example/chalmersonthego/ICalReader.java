package com.example.chalmersonthego;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

public class ICalReader {
	private String iCal;
	private Activity owningActivity;
	private long enqueue;
	private DownloadManager dm;

	public ICalReader(Activity owningActivity) {
		this.owningActivity = owningActivity;
	}

	/**
	 * Downloads the iCal to the phone locally
	 * 
	 * @param url, a string with the url to the downloadpage. Has to be a direct downloadpath.
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
