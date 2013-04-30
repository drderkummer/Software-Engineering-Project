package com.example.chalmersonthego;


import group5.database.DAO;

public class InsertionsOfData {
	/**
	 * This method should be called only ones during the first launch of the Activity
	 * @param dao
	 */
	public static void basicDataInsert(DAO dao){
				dao.open();
				//insert into 'types' table:
				dao.insertIntoTable2("lecture hall");
				dao.insertIntoTable2("group room");
				dao.insertIntoTable2("computer room");
				
				//insert into 'buildings' table:
				dao.insertIntoTable4("EDIT");
				
				/******************************************************************************
				*********************INSERTION OF "EDIT" BUILDING:*****************************
				******************************************************************************/
				//insert into 'entries' table:
				dao.insertIntoTable1(57.687815, 11.979233, "EDIT");
				dao.insertIntoTable1(57.688196, 11.978493,"EDIT");
				dao.insertIntoTable1(57.687775, 11.979826, "EDIT");
				dao.insertIntoTable1(57.687507, 11.978482, "EDIT");
				
				//insert into 'rooms' table:
				//lecture halls
				dao.insertIntoTable3("EL41", 57.687798, 11.978876, "lecture hall", "EDIT", "4");
				dao.insertIntoTable3("EL42", 57.687917, 11.978761, "lecture hall", "EDIT", "4");
				dao.insertIntoTable3("EL43", 57.688036, 11.978657, "lecture hall", "EDIT", "4");
				dao.insertIntoTable3("ES51", 57.687798, 11.978876, "lecture hall", "EDIT", "5");
				dao.insertIntoTable3("EL52", 57.687917, 11.978761, "lecture hall", "EDIT", "5");
				dao.insertIntoTable3("EL53", 57.688036, 11.978657, "lecture hall", "EDIT", "5");
				dao.insertIntoTable3("ES61", 57.687798, 11.978876, "lecture hall", "EDIT", "6");
				dao.insertIntoTable3("EL62", 57.687917, 11.978761, "lecture hall", "EDIT", "6");
				dao.insertIntoTable3("EL63", 57.688036, 11.978657, "lecture hall", "EDIT", "6");
				dao.insertIntoTable3("EA", 57.687758, 11.979188, "lecture hall", "EDIT", "3");
				dao.insertIntoTable3("EB", 57.687809, 11.979367, "lecture hall", "EDIT", "3");
				dao.insertIntoTable3("EC", 57.687758, 11.979188, "lecture hall", "EDIT", "4");
				dao.insertIntoTable3("ED", 57.687809, 11.979367, "lecture hall", "EDIT", "4");
				dao.insertIntoTable3("EE", 57.687758, 11.979188, "lecture hall", "EDIT", "4");
				dao.insertIntoTable3("EF", 57.687809, 11.979367, "lecture hall", "EDIT", "4");
				//computer rooms
				dao.insertIntoTable3("ED2480", 57.688125, 11.978326, "computer room", "EDIT", "2");
				dao.insertIntoTable3("ED3354", 57.687788, 11.979501, "computer room", "EDIT", "3");
				dao.insertIntoTable3("ED3358", 57.687728, 11.979255, "computer room", "EDIT", "3");
				dao.insertIntoTable3("ED5352", 57.687781, 11.979472, "computer room", "EDIT", "5");
				dao.insertIntoTable3("ED5355", 57.687732, 11.979297, "computer room", "EDIT", "5");
				dao.insertIntoTable3("ED5225", 57.687654, 11.978761, "computer room", "EDIT", "5");
				dao.insertIntoTable3("ED6360", 57.687732, 11.979297, "computer room", "EDIT", "6");
				dao.insertIntoTable3("ED6225", 57.687654, 11.978761, "computer room", "EDIT", "6");
				dao.insertIntoTable3("ED4225", 57.687654, 11.978761, "computer room", "EDIT", "4");
				dao.insertIntoTable3("ED4220", 57.687627, 11.978600, "computer room", "EDIT", "4");
				dao.insertIntoTable3("ED4358", 57.687779, 11.979464, "computer room", "EDIT", "4");
				//group rooms
				dao.insertIntoTable3("ED?3207", 57.687646, 11.978989, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED?3209", 57.687629, 11.978893, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED?3211", 57.687611, 11.978801, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED?3213", 57.687587, 11.978699, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED?3215", 57.687567, 11.978624, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED?3217", 57.687541, 11.978541, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED?4205", 57.687660, 11.979011, "group room", "EDIT", "4");
				dao.insertIntoTable3("ED?4207", 57.687634, 11.978917, "group room", "EDIT", "4");
				dao.insertIntoTable3("ED?5205", 57.687660, 11.979011, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED?5207", 57.687634, 11.978917, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED?5209", 57.687617, 11.978842, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED?5211", 57.687594, 11.978769, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED?5213", 57.687571, 11.978697, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED?5215", 57.687553, 11.978630, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED?5217", 57.687524, 11.978560, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED?6205", 57.687660, 11.979011, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED?6207", 57.687634, 11.978917, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED?6209", 57.687617, 11.978842, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED?6211", 57.687594, 11.978769, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED?6213", 57.687571, 11.978697, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED?6215", 57.687553, 11.978630, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED?6217", 57.687524, 11.978560, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED?6358", 57.687779, 11.979464, "group room", "EDIT", "6");
				/********************************************************************************
				*******************END OF EDIT BUILDING INSERTIONS*******************************
				********************************************************************************/
			
		}
}

