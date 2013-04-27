package com.example.chalmersonthego;

import group5.database.DAO;

public class InsertionsOfData {
	/**
	 * This method should be called only ones during the first launch of the Activity
	 * @param dao
	 */
	public static void insert(DAO dao){
		//The following is only crap but I needed it for testing
		dao.insertIntoTable2("EDIT");
	}
}
