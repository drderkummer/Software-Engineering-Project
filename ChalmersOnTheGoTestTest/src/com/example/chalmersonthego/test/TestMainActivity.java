package com.example.chalmersonthego.test;

import com.example.chalmersonthego.MainActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;

public class TestMainActivity extends
		ActivityInstrumentationTestCase2<MainActivity> {
	
	private MainActivity mainActivity;

	public TestMainActivity(Class<MainActivity> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		
		//Because we don't want to be able to touch something on the activity
		setActivityInitialTouchMode(false);
		
		mainActivity = getActivity();
	}
	
	public void testX(){
		//Clicks on a button
		//The '0' should be a id of a button
		TouchUtils.clickView(this, mainActivity.findViewById(0));
	}

}
