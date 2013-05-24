package com.example.chalmersonthego.test;

import dat255.group5.chalmersonthego.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.SearchView;
/**
 * This method class can be used to test clicking buttons on the activity.
 * But we thought it makes more sense to do this manually to test the
 * user experience and use automatic UnitTests for underlying functions.
 * @author Fredrik
 *
 */
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
	/**
	 * This method can be used to click buttons
	 */
	public void testSearch(){
		assertTrue(true);
	}

}
