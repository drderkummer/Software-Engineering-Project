package com.example.chalmersonthego;


import group5.database.DAO;

public class InsertionsOfData {
	/**
	 * This method should be called only ones during the first launch of the Activity
	 * @param dao
	 */
	public static void basicDataInsert(DAO dao){
				//insert into 'types' table:
				dao.insertIntoTable2("lecture hall");
				dao.insertIntoTable2("group room");
				dao.insertIntoTable2("computer room");
				dao.insertIntoTable4("pub");
				
				//insert into 'buildings' table:
				dao.insertIntoTable4("EDIT");
				dao.insertIntoTable4("Architecture");
				
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
				dao.insertIntoTable3("EA", 57.687758, 11.979188, "lecture hall", "EDIT", "4");
				dao.insertIntoTable3("EB", 57.687809, 11.979367, "lecture hall", "EDIT", "4");
				dao.insertIntoTable3("EC", 57.687758, 11.979188, "lecture hall", "EDIT", "5");
				dao.insertIntoTable3("ED", 57.687809, 11.979367, "lecture hall", "EDIT", "5");
				dao.insertIntoTable3("EE", 57.687758, 11.979188, "lecture hall", "EDIT", "6");
				dao.insertIntoTable3("EF", 57.687809, 11.979367, "lecture hall", "EDIT", "6");
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
				dao.insertIntoTable3("ED3207", 57.687646, 11.978989, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED3209", 57.687629, 11.978893, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED3211", 57.687611, 11.978801, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED3213", 57.687587, 11.978699, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED3215", 57.687567, 11.978624, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED3217", 57.687541, 11.978541, "group room", "EDIT", "3");
				dao.insertIntoTable3("ED4205", 57.687660, 11.979011, "group room", "EDIT", "4");
				dao.insertIntoTable3("ED4207", 57.687634, 11.978917, "group room", "EDIT", "4");
				dao.insertIntoTable3("ED5205", 57.687660, 11.979011, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED5207", 57.687634, 11.978917, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED5209", 57.687617, 11.978842, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED5211", 57.687594, 11.978769, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED5213", 57.687571, 11.978697, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED5215", 57.687553, 11.978630, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED5217", 57.687524, 11.978560, "group room", "EDIT", "5");
				dao.insertIntoTable3("ED6205", 57.687660, 11.979011, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED6207", 57.687634, 11.978917, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED6209", 57.687617, 11.978842, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED6211", 57.687594, 11.978769, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED6213", 57.687571, 11.978697, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED6215", 57.687553, 11.978630, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED6217", 57.687524, 11.978560, "group room", "EDIT", "6");
				dao.insertIntoTable3("ED6358", 57.687779, 11.979464, "group room", "EDIT", "6");
				//Pubs. Coordinates currently unknown.
				dao.insertIntoTable3("Datasektionen - Basen", 0.0, 0.0, "pub", "EDIT", "0");
				dao.insertIntoTable5("Datasektionen - Basen", R.drawable.d);
				/********************************************************************************
				*******************END OF EDIT BUILDING INSERTIONS*******************************
				********************************************************************************/

				/********************************************************************************
				*******************INSERTIONS OF ARCHITECTURE" BUILDING*************************
				********************************************************************************/
				//insert into 'entries' table:

				//insert into "rooms" table:
				//lecture halls
				//VM missing because of strange name conflict: Check that!!!
				dao.insertIntoTable3("VR", 57.687381, 11.976328, "lecture hall", "Architecture", "ground");
				dao.insertIntoTable3("VK", 57.687219, 11.976505, "lecture hall", "Architecture", "ground");
				dao.insertIntoTable3("VG", 57.687243, 11.977039, "lecture hall", "Architecture", "ground");
				dao.insertIntoTable3("VF", 57.687359, 11.977302, "lecture hall", "Architecture", "ground");
				dao.insertIntoTable3("VB", 57.687523, 11.977270, "lecture hall", "Architecture", "ground");
				dao.insertIntoTable3("VA", 57.687630, 11.977184, "lecture hall", "Architecture", "ground");
				dao.insertIntoTable3("VÖ1",57.687404,11.977256, "lecture hall", "Architecture" , "1");
				dao.insertIntoTable3("VÖ2",57.687404,11.977256, "lecture hall", "Architecture" , "2");
				dao.insertIntoTable3("VÖ3",57.687404,11.977256, "lecture hall", "Architecture" , "3");
				dao.insertIntoTable3("VÖ12", 57.687353, 11.977152, "lecture hall", "Architecture", "1");
				dao.insertIntoTable3("VÖ22", 57.687353, 11.977152, "lecture hall", "Architecture", "2");
				dao.insertIntoTable3("VÖ32", 57.687353, 11.977152, "lecture hall", "Architecture", "3");
				dao.insertIntoTable3("VÖ11", 57.687302, 11.977197, "lecture hall", "Architecture", "1");
				dao.insertIntoTable3("VÖ21", 57.687302, 11.977197, "lecture hall", "Architecture", "2");
				dao.insertIntoTable3("VÖ31", 57.687302, 11.977197, "lecture hall", "Architecture", "3");
				dao.insertIntoTable3("VV11", 57.687187, 11.976731, "lecture hall", "Architecture", "1");
				dao.insertIntoTable3("VV21", 57.687187, 11.976731, "lecture hall", "Architecture", "2");
				dao.insertIntoTable3("VV31", 57.687187, 11.976731, "lecture hall", "Architecture", "3");
				dao.insertIntoTable3("VV12", 57.687242, 11.976698, "lecture hall", "Architecture", "1");
				dao.insertIntoTable3("VV22", 57.687242, 11.976698, "lecture hall", "Architecture", "2");
				dao.insertIntoTable3("VV13", 57.687157, 11.976575, "lecture hall", "Architecture", "1");
				dao.insertIntoTable3("VV23", 57.687157, 11.976575, "lecture hall", "Architecture", "2");
				dao.insertIntoTable3("VV33", 57.687157, 11.976575, "lecture hall", "Architecture", "3");
				dao.insertIntoTable3("VV43", 57.687157, 11.976575, "lecture hall", "Architecture", "4");
				//computer rooms
				dao.insertIntoTable3("VÖ13", 57.687338, 11.977310, "computer room", "Architecture", "1");
				dao.insertIntoTable3("VÖ23", 57.687338, 11.977310, "computer room", "Architecture", "2");
				dao.insertIntoTable3("VÖ33", 57.687338, 11.977310, "computer room", "Architecture", "3");
				dao.insertIntoTable3("VV32", 57.687242, 11.976698, "computer room", "Architecture", "3");
				dao.insertIntoTable3("VV42", 57.687242, 11.976698, "computer room", "Architecture", "4");
				/********************************************************************************
				*****************END OF ARCHITECTURE BUILDING INSERTIONS*************************
				********************************************************************************/
					
		}
}

