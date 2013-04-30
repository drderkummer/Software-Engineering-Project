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
	/**
	 * Tests insertIntoTable4 and getAllFromTable4
	 */
	public void testInsertAndGetFromTable4(){
		String building = "EDIT";
		dao.insertIntoTable4(building);
		ArrayList<String> result = dao.getAllFromTable4();
		Assert.assertEquals(building, result.get(0));
	}
	/**
	 * Tests inserIntroTable2 and getAllFromTable2
	 */
	public void testInsertIntoTable2AndGetAllFromTable2(){
		//Insert
		String type1 = "Group";
		String type2 = "Computer";
		String type3 = "Lecture";
		dao.insertIntoTable2(type1);
		dao.insertIntoTable2(type2);
		dao.insertIntoTable2(type3);
		
		//Get All
		ArrayList<String> results = dao.getAllFromTable2();
		
		//Check if the inserted values is in the result
		Assert.assertTrue(results.contains(type1));
		Assert.assertTrue(results.contains(type2));
		Assert.assertTrue(results.contains(type3));
	}
	/**
	 * Tests insertIntoTable1 and getClosestEntry
	 */
	public void testInsertAndGetFromTable1(){
		Double x = 1.0;
		Double y = 2.0;
		LatLng cord = new LatLng(x,y);
		String building = "EDIT";
		dao.insertIntoTable1(x, y, building);
		LatLng result = dao.getClosestEntry("EDIT",cord);
		Assert.assertEquals(cord, result);
	}
	/**
	 * Tests getClosestEntry and insertIntoTable1
	 */
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
	/**
	 * Tests getRoomCoordinates and insertIntoTable1
	 */
	public void testGetRoomCoordinatesAndInsertIntoTable3(){
		String room = "EA";
		double xCord = 9.9;
		double yCord = 5.5;
		LatLng in = new LatLng(xCord,yCord);
		String building ="EDIT";
		String type = "lecture";
		dao.insertIntoTable2(type);
		dao.insertIntoTable4(building);
		dao.insertIntoTable3(room, xCord, yCord, type, building, "1");
		LatLng result = dao.getRoomCoordinates(room);
		Assert.assertEquals(in,result);
	}
	/**
	 * Tests getAllRooms
	 */
	public void testGetAllRoomsInBuilding(){
		String room = "EA";
		String room2 = "EB";
		String falseRoom = "XX";
		
		double xCord = 9.9;
		double yCord = 5.5;
		
		String building ="EDIT";
		String falseBuilding = "YY";
		String type = "lecture";
		
		dao.insertIntoTable2(type);
		dao.insertIntoTable4(building);
		dao.insertIntoTable4(falseBuilding);
		dao.insertIntoTable3(room, xCord, yCord, type, building, "1");
		dao.insertIntoTable3(room2, xCord, yCord, type, building, "2");
		dao.insertIntoTable3(falseRoom, xCord, yCord, type, falseBuilding, "1");
		
		ArrayList<String> results = dao.getAllRoomsInBuilding(building);
		Assert.assertTrue(results.size() == 2);
		Assert.assertTrue(results.contains(room));
		Assert.assertTrue(results.contains(room2));
		Assert.assertFalse(results.contains(falseRoom));
		
	}
	
	/**
	 * Tests getAllRoomsWithType
	 */
	public void testGetAllRoomsWithType(){
		String room = "EA";
		String room2 = "EB";
		String falseRoom = "XX";
		
		double xCord = 9.9;
		double yCord = 5.5;
		
		String building ="EDIT";
		String falseType = "YY";
		String type = "lecture";
		
		dao.insertIntoTable2(type);
		dao.insertIntoTable2(falseType);
		dao.insertIntoTable4(building);
		
		dao.insertIntoTable3(room, xCord, yCord, type, building, "1");
		dao.insertIntoTable3(room2, xCord, yCord, type, building, "2");
		dao.insertIntoTable3(falseRoom, xCord, yCord, falseType, building, "1");
		
		ArrayList<String> results = dao.getAllRoomsInBuilding(building);
		Assert.assertTrue(results.size() == 2);
		Assert.assertTrue(results.contains(room));
		Assert.assertTrue(results.contains(room2));
		Assert.assertFalse(results.contains(falseRoom));
		
	}
	/**
	 * Tests suggestions
	 */
	public void testSuggestions(){
		String type = "lecture";
		String building = "EDIT";
		String room = "EA";
		
		dao.insertIntoTable2(type);
		dao.insertIntoTable4(building);
		dao.insertIntoTable3(room, 1.0, 1.0, type, building, "first");
		
		ArrayList<String> list = dao.suggestions("e");
		Assert.assertTrue(list.size() == 3);
		
		list = dao.suggestions("di");
		Assert.assertTrue(list.size() == 1);
		
		list = dao.suggestions("ir");
		Assert.assertNull(list);
	}
	/**
	*Tests method getRoomName
	*/
	public void testGetName(){
		Double x6 = 1.0;
		Double y6 = 2.0;
		String name = "ED222";
		dao.insertIntoTable3(name, x6, y6, "computer room", "EDIT","3");
		String result = dao.getName(x6, y6);
		Assert.assertEquals(name, result);
	}
}
