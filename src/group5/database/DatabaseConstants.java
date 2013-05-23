package group5.database;


/**
 * This class stores all constants related to the database-data
 * @author Fredrik
 * 
 */
public class DatabaseConstants {
	
	// A CharSequence[] with all the floors
	public static final CharSequence floor_minus1 = "-1 Floor";
	public static final CharSequence floor_ground = "Ground floor";
	public static final CharSequence floor_1 = "1th Floor";
	public static final CharSequence floor_2 = "2th Floor";
	public static final CharSequence floor_3 = "3th Floor";
	public static final CharSequence floor_4 = "4th Floor";
	public static final CharSequence floor_5 = "5th Floor";
	public static final CharSequence[] floorOptions =
		{floor_minus1,floor_ground,floor_1,floor_2,floor_3,floor_4,floor_5};
	
	// A CharSequence[] with all the different types of rooms, available to be
	// selected in the show all menu
	public static final CharSequence type_computerRoom = "Computer Rooms";
	public static final CharSequence type_lectureHall = "Lecture Halls";
	public static final CharSequence type_groupRoom = "Group Rooms";
	public static final CharSequence type_pub = "Pubs";
	public static final CharSequence[] layerOptions =
		{type_computerRoom,type_lectureHall,type_groupRoom,type_pub};
	
}
