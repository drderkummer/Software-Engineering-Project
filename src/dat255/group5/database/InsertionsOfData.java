package dat255.group5.database;

import com.example.chalmersonthego.R;

/**
 * This class is used to insert all data
 * 
 * @author Fredrik
 * 
 */
public class InsertionsOfData {
	/**
	 * This method should be called only ones during the first launch of the
	 * Activity
	 * 
	 * @param mydao
	 */
	public static void basicDataInsert(DAO dao) {
		final DAO mydao = dao;
		new Thread() {
			public void run() {
				// insert into 'types' table:
				mydao.insertIntoTable2(DatabaseConstants.type_lectureHall);
				mydao.insertIntoTable2(DatabaseConstants.type_groupRoom);
				mydao.insertIntoTable2(DatabaseConstants.type_computerRoom);
				mydao.insertIntoTable2(DatabaseConstants.type_pub);
				mydao.insertIntoTable2(DatabaseConstants.type_cinema);
				mydao.insertIntoTable2(DatabaseConstants.type_gym);
				mydao.insertIntoTable2(DatabaseConstants.type_billiard);
				mydao.insertIntoTable2(DatabaseConstants.type_sauna);
				mydao.insertIntoTable2(DatabaseConstants.type_restaurant);
				mydao.insertIntoTable2(DatabaseConstants.type_atm);
				mydao.insertIntoTable2(DatabaseConstants.type_microwave);

				/***************************************************************
				 ********************* INSERTION OF
				 * DatabaseConstants.building_studentUnion:*********
				 * ********************
				 **************************************************************/
				mydao.insertIntoTable3("Pripps", 57.688873, 11.974357,
						DatabaseConstants.type_pub,
						DatabaseConstants.building_studentUnion,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable5("Pripps", R.drawable.pripps);
				mydao.insertIntoTable3("Kyrkan", 57.68911, 11.974352,
						DatabaseConstants.type_billiard,
						DatabaseConstants.building_studentUnion,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("Restaurant Student Union", 57.68918, 11.974059,
						DatabaseConstants.type_restaurant,
						DatabaseConstants.building_studentUnion,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("RunAn Salen/Cinema", 57.689125, 11.973869,
						DatabaseConstants.type_cinema,
						DatabaseConstants.building_studentUnion,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("Palmstedt Salen", 57.689355, 11.973992,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_studentUnion,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Motionshall/Gym", 57.688925, 11.974099,
						DatabaseConstants.type_gym,
						DatabaseConstants.building_studentUnion,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("ATM Student Union", 57.688968, 11.974121,
						DatabaseConstants.type_atm,
						DatabaseConstants.building_studentUnion,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("ATM Library", 57.690046, 11.978702,
						DatabaseConstants.type_atm,
						DatabaseConstants.building_library,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Microwave Student Union", 57.689064, 11.974244, 
						DatabaseConstants.type_microwave,
						DatabaseConstants.building_studentUnion,
						DatabaseConstants.floor_minus1);

				// insert into 'buildings' table:
				mydao.insertIntoTable4(DatabaseConstants.building_edit);
				mydao.insertIntoTable4(DatabaseConstants.building_architecture);
				mydao.insertIntoTable4(DatabaseConstants.building_vasa);
				mydao.insertIntoTable4(DatabaseConstants.building_studentUnion);
				mydao.insertIntoTable4(DatabaseConstants.building_library);

				/***************************************************************
				 ****************** INSERTION OF DatabaseConstants.building_edit
				 * BUILDING:*****************************
				 **************************************************************/
				// insert into 'entries' table:
				//edit:
				mydao.insertIntoTable1(57.687815, 11.979233,
						DatabaseConstants.building_edit);
				mydao.insertIntoTable1(57.688196, 11.978493,
						DatabaseConstants.building_edit);
				mydao.insertIntoTable1(57.687775, 11.979826,
						DatabaseConstants.building_edit);
				mydao.insertIntoTable1(57.687507, 11.978482,
						DatabaseConstants.building_edit);
				//student union:
				mydao.insertIntoTable1(57.689363, 11.973791,
						DatabaseConstants.building_studentUnion);
				mydao.insertIntoTable1(57.689176, 11.974408,
						DatabaseConstants.building_studentUnion);
				mydao.insertIntoTable1(57.688705, 11.975167,
						DatabaseConstants.building_studentUnion);
				//architecture:
				mydao.insertIntoTable1(57.6875, 11.976328,
						DatabaseConstants.building_architecture);
				mydao.insertIntoTable1(57.6875, 11.976328,
						DatabaseConstants.building_architecture);
				//vasa:
				mydao.insertIntoTable1(57.693036,11.975301,
						DatabaseConstants.building_vasa);
				mydao.insertIntoTable1(57.693099,11.975744,
						DatabaseConstants.building_vasa);
				mydao.insertIntoTable1(57.692942,11.975191,
						DatabaseConstants.building_vasa);
				mydao.insertIntoTable1(57.693448,11.975422,
						DatabaseConstants.building_vasa);
				//library:
				mydao.insertIntoTable1(57.690304, 11.978686, 
						DatabaseConstants.building_library);

				// insert into 'rooms' table:
				// lecture halls
				mydao.insertIntoTable3("EL41", 57.687798, 11.978876,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				mydao.insertIntoTable3("EL42", 57.687917, 11.978761,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				mydao.insertIntoTable3("EL43", 57.688036, 11.978657,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				mydao.insertIntoTable3("ES51", 57.687798, 11.978876,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("EL52", 57.687917, 11.978761,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("EL53", 57.688036, 11.978657,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ES61", 57.687798, 11.978876,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("EL62", 57.687917, 11.978761,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("EL63", 57.688036, 11.978657,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("EA", 57.687758, 11.979188,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				mydao.insertIntoTable3("EB", 57.687809, 11.979367,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				mydao.insertIntoTable3("EC", 57.687758, 11.979188,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED", 57.687809, 11.979367,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("EE", 57.687758, 11.979188,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("EF", 57.687809, 11.979367,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				// computer rooms
				mydao.insertIntoTable3("ED2480", 57.688125, 11.978326,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_2);
				mydao.insertIntoTable3("ED3354", 57.687788, 11.979501,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("ED3358", 57.687728, 11.979255,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("ED5352", 57.687781, 11.979472,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED5355", 57.687732, 11.979297,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED5225", 57.687654, 11.978761,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED6360", 57.687732, 11.979297,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("ED6225", 57.687654, 11.978761,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("ED4225", 57.687654, 11.978761,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				mydao.insertIntoTable3("ED4220", 57.687627, 11.978600,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				mydao.insertIntoTable3("ED4358", 57.687779, 11.979464,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				// group rooms
				mydao.insertIntoTable3("ED3207", 57.687646, 11.978989,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("ED3209", 57.687629, 11.978893,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("ED3211", 57.687611, 11.978801,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("ED3213", 57.687587, 11.978699,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("ED3215", 57.687567, 11.978624,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("ED3217", 57.687541, 11.978541,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("ED4205", 57.687660, 11.979011,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				mydao.insertIntoTable3("ED4207", 57.687634, 11.978917,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_4);
				mydao.insertIntoTable3("ED5205", 57.687660, 11.979011,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED5207", 57.687634, 11.978917,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED5209", 57.687617, 11.978842,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED5211", 57.687594, 11.978769,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED5213", 57.687571, 11.978697,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED5215", 57.687553, 11.978630,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED5217", 57.687524, 11.978560,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_5);
				mydao.insertIntoTable3("ED6205", 57.687660, 11.979011,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("ED6207", 57.687634, 11.978917,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("ED6209", 57.687617, 11.978842,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("ED6211", 57.687594, 11.978769,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("ED6213", 57.687571, 11.978697,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("ED6215", 57.687553, 11.978630,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("ED6217", 57.687524, 11.978560,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				mydao.insertIntoTable3("ED6358", 57.687779, 11.979464,
						DatabaseConstants.type_groupRoom,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_6);
				// Pubs. Coordinates currently unknown.
				mydao.insertIntoTable3("Datasektionen - Basen", 57.687543, 11.978621,
						DatabaseConstants.type_pub,
						DatabaseConstants.building_edit,
						DatabaseConstants.floor_minus1);
				mydao.insertIntoTable5("Datasektionen - Basen", R.drawable.d);
				mydao.insertIntoTable3("Kajsabaren", 57.688178, 11.978659,
						DatabaseConstants.type_pub,
						DatabaseConstants.building_edit, 
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable5("Kajsabaren", R.drawable.e);
				/***************************************************************
				 ******************* END OF EDIT BUILDING
				 * INSERTIONS*******************************
				 **************************************************************/

				/***************************************************************
				 ******************* INSERTIONS OF ARCHITECTURE" BUILDING*******
				 **************************************************************/
				// insert into 'entries' table:

				// insert into "rooms" table:
				// lecture halls
				// VM missing because of strange name conflict: Check that!!!
				mydao.insertIntoTable3("VR", 57.687381, 11.976328,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("VK", 57.687219, 11.976505,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("VG", 57.687243, 11.977039,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("VF", 57.687359, 11.977302,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("VB", 57.687523, 11.977270,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("VA", 57.687630, 11.977184,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("VÖ1", 57.687404, 11.977256,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("VÖ2", 57.687404, 11.977256,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_2);
				mydao.insertIntoTable3("VÖ3", 57.687404, 11.977256,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("VÖ12", 57.687353, 11.977152,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("VÖ22", 57.687353, 11.977152,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_2);
				mydao.insertIntoTable3("VÖ32", 57.687353, 11.977152,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("VÖ11", 57.687302, 11.977197,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("VÖ21", 57.687302, 11.977197,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_2);
				mydao.insertIntoTable3("VÖ31", 57.687302, 11.977197,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("VV11", 57.687187, 11.976731,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("VV21", 57.687187, 11.976731,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_2);
				mydao.insertIntoTable3("VV31", 57.687187, 11.976731,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("VV12", 57.687242, 11.976698,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("VV22", 57.687242, 11.976698,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_2);
				mydao.insertIntoTable3("VV13", 57.687157, 11.976575,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("VV23", 57.687157, 11.976575,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_2);
				mydao.insertIntoTable3("VV33", 57.687157, 11.976575,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("VV43", 57.687157, 11.976575,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_4);
				// computer rooms
				mydao.insertIntoTable3("VÖ13", 57.687338, 11.977310,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_1);
				mydao.insertIntoTable3("VÖ23", 57.687338, 11.977310,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_2);
				mydao.insertIntoTable3("VÖ33", 57.687338, 11.977310,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("VV32", 57.687242, 11.976698,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_3);
				mydao.insertIntoTable3("VV42", 57.687242, 11.976698,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_architecture,
						DatabaseConstants.floor_4);
				/***************************************************************
				 ***************** END OF ARCHITECTURE BUILDING
				 * INSERTIONS*************************
				 **************************************************************/

				/***************************************************************
				 ************************* INSERTIONS OF VASA BUILDING *********
				 **************************************************************/
				// insert into 'entries' table:

				// insert into "rooms" table:
				// lecture halls
				mydao.insertIntoTable3("Vasa A", 57.693233, 11.975172,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Vasa B", 57.693138, 11.974909,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Vasa C", 57.693382, 11.975411,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Vasa 1", 57.693079, 11.974974,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Vasa 2", 57.692986, 11.975060,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Vasa 3", 57.693293, 11.975510,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Vasa 4", 57.693215, 11.975567,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Vasa 5", 57.693141, 11.975639,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Vasa 6", 57.693038, 11.975642,
						DatabaseConstants.type_lectureHall,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_ground);
				mydao.insertIntoTable3("Datasal A", 57.693038, 11.975642,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_minus1);
				mydao.insertIntoTable3("Datasal B", 57.693115, 11.975601,
						DatabaseConstants.type_computerRoom,
						DatabaseConstants.building_vasa,
						DatabaseConstants.floor_minus1);
				/***************************************************************
				 ************************* END OF VASA BUILDING INSERTIONS******
				 **************************************************************/
			}
		}.start();

	}
}
