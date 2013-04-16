package com.example.chalmersonthego;

import group5.database.DAO;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Open connection to the Database
        dao = new DAO(this);
        dao.open();
    }
    
    @Override
    protected void onDestroy(){
    	
    	//Close connection to the databse
    	dao.close();
    }
}