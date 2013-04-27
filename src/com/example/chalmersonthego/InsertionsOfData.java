package com.example.chalmersonthego;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import group5.database.DAO;

public class InsertionsOfData {
	/**
	 * This method should be called only ones during the first launch of the Activity
	 * @param dao
	 */
	public static void basicDataInsert(DAO dao){
		
			try{
				dao.open();
				//insert into 'entries' table:
				dao.insertIntoTable1(57.687815, 11.979233, "EDIT");
				//insert into 'types' table:
				dao.insertIntoTable2("Lecture Hall");
				//insert into 'rooms' table:
				dao.insertIntoTable3("EL41", 57.687798, 11.978876, "Lecture Hall", "EDIT", "4");
				//insert into 'buildings' table:
				dao.insertIntoTable4("EDIT");
				dao.close();
				}catch (SQLiteException e){
			
		}
		
		//The following is only crap but I needed it for testing
		//dao.insertIntoTable2("EDIT");
	}
}
