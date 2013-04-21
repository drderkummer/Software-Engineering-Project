package com.example.chalmersonthego.test;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import group5.database.DAO;
/**
 * This class should test all the methods in DAO.java
 * @author Fredrik
 *
 */
public class DAOTest extends AndroidTestCase {
	
	private DAO dao;

	protected void setUp() throws Exception {
		super.setUp();
		RenamingDelegatingContext context 
        = new RenamingDelegatingContext(getContext(), "test_");
		dao = new DAO(context);
		dao.open();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		dao.close();
	}
	////////////////////Test Cases//////////////////////////////////
	public void testInsertAndGetFromTable4(){
		String building = "EDIT";
		dao.insertIntoTable4(building);
		ArrayList<String> result = dao.getAllFromTable4();
		Assert.assertEquals(building, result.get(0));
	}
	public void testInsertAndGetFromTable1(){
		Double x = 1.0;
		Double y = 2.0;
		LatLng cord = new LatLng(x,y);
		String building = "EDIT";
		dao.insertIntoTable1(x, y, building);
		LatLng result = dao.getClosestEntry("EDIT",cord);
		Assert.assertEquals(cord, result);
	}
	public void testCalculationOfClosestEntry(){
		//My current coordinates
		LatLng currentCoord = new LatLng(0.0,0.0);
		
		String building = "EDIT";
		
		Double x1 = 1.0;
		Double y1 = 2.0;
		dao.insertIntoTable1(x1, y1, building);
		
		Double x2 = 1.0;
		Double y2 = 1.0;
		dao.insertIntoTable1(x2, y2, building);
		
		Double x3 = 10.0;
		Double y3 = 1.0;
		dao.insertIntoTable1(x3, y3, building);
		
		Double x4 = 1.0;
		Double y4 = 0.5;
		LatLng expectedCord = new LatLng(x4,y4);
		dao.insertIntoTable1(x4, y4, building);
		
		Double x5 = 10.0;
		Double y5 = 10.0;
		dao.insertIntoTable1(x5, y5, building);
		
		LatLng result = dao.getClosestEntry(building,currentCoord);
		Assert.assertEquals(expectedCord, result);
	}
}
