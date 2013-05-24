package com.example.chalmersonthego.test;

import dat255.group5.chalmersonthego.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestMainActivity extends
		ActivityInstrumentationTestCase2<MainActivity> {
	
	private MainActivity mainActivity;

	public TestMainActivity() {
		super(MainActivity.class);
	}

	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		
		//Because we don't want to be able to touch something on the activity
		setActivityInitialTouchMode(false);
		
		mainActivity = getActivity();
	}
	
	public void testSearch(){
		//here you can create unitTests for the gui and also add more methods
		/**
		SearchView searchView = (SearchView) mainActivity.findViewById(R.id.action_search);
		assertNotNull(searchView);
		TouchUtils.clickView(this, searchView);
		**/
	}

}
