package dat255.group5.database;

/**
 * This class stores all constants related to the database-data
 * 
 * @author Fredrik
 * 
 */
public class DatabaseConstants {

	// A String[] with all the floors
	public static final String floor_minus1 = "-1 Floor";
	public static final String floor_ground = "Ground floor";
	public static final String floor_1 = "1th Floor";
	public static final String floor_2 = "2th Floor";
	public static final String floor_3 = "3th Floor";
	public static final String floor_4 = "4th Floor";
	public static final String floor_5 = "5th Floor";
	public static final String floor_6 = "6th Floor";
	public static final String[] floorOptions = { floor_minus1, floor_ground,
			floor_1, floor_2, floor_3, floor_4, floor_5, floor_6 };

	// A String[] with all the different types of rooms, available to be
	// selected in the show all menu
	public static final String type_computerRoom = "Computer Room";
	public static final String type_lectureHall = "Lecture Hall";
	public static final String type_groupRoom = "Group Room";
	public static final String type_pub = "Pub";
	public static final String[] layerOptions = { type_computerRoom,
			type_lectureHall, type_groupRoom, type_pub };

	// Buildings
	public static final String building_edit = "EDIT";
	public static final String building_architecture = "Architecture";
	public static final String building_vasa = "Vasa";
	public static final String building_studentUnion = "Student Union";

}
