import java.util.Iterator;
import java.io.*;
import java.net.*;
import java.util.*;

//Mention use of JSON Library
//Mention E-mail libtrary / api

//filewriter + e-mail to store digital receipt + send it to client
//add both seminar case and city case 

//maybe not display cases without price info

public class MainMenu {
	//static Room[][] hotelsTable = new Room[100000][2];
	static int[][] hotelsTableIDs = new int[2][100000];
	static int counterOfFindings = 0;
	static int counterOfFilteredFindings = 0;
	static int magicCheck = 0;
	static String language = "";
	static boolean filteredEnabled = false;
	static boolean seminarMode = false;
	
	static Map<Integer, Room> hotelMap = new HashMap<>(); 
	
	static Map<Integer, String> amenitiesMap = new HashMap<>();
	
	static LocationCoordinates seminarCoordinates = new LocationCoordinates(0,0);
	
	
	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read); 

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		} 
	}
	
	public static void createAmenitiesMap() {
		try {
			String urlString = "http://engine.hotellook.com/api/v2/static/amenities/en.json?token=6251c90d5bc52c88b60a38bd84373513";
			String data = readUrl(urlString);	
			JSONArray amenities = new JSONArray(data);
			
			for(int i=0;i<amenities.length();i++) {
				JSONObject tempObject = amenities.getJSONObject(i);
				int id = Integer.parseInt(tempObject.getString("id"));
				String name = tempObject.getString("name");
				
				amenitiesMap.put(id, name);
			}
			
			/*System.out.println("AMENITIES:"); //debug
			System.out.print(amenitiesMap); */

		} catch(Exception e) {
			System.out.println("Ameinities Not Found");				
		}
	}
		
			
	public static Room readHotelSelectionsData(JSONObject jsonObj) { //instead of creating object with blank attributes for null values, could better use inheritance
		int hotelID = jsonObj.getInt("hotel_id");
		Double distanceFromCityCenter = jsonObj.getDouble("distance");
		String nameOfHotel = jsonObj.getString("name");
		int stars = jsonObj.getInt("stars");
		int visitorRating = jsonObj.getInt("rating");
		String summary = "";
		if(!jsonObj.isNull("ty_summary")) { //mention how this was an issue
			summary = jsonObj.getString("ty_summary");
		}
		String propertyType = jsonObj.getString("property_type"); 
		JSONArray hotelTypes = jsonObj.getJSONArray("hotel_type");
		String[] hotelTypesArray = new String[hotelTypes.length()];
		for(int i=0; i<hotelTypes.length(); i++) {	
			hotelTypesArray[i] = hotelTypes.getString(i);
		}
		
		int totalPrice = -1;
		int numberOfNights = -1;
		int pricePerNight = -1;	
		
		if(!jsonObj.isNull("last_price_info")) {
			//System.out.println("GOOD");
			//System.out.println(pricePerNight);
			JSONObject pricingInfo = jsonObj.getJSONObject("last_price_info");
			totalPrice = pricingInfo.getInt("price");
			numberOfNights = pricingInfo.getInt("nights");
			pricePerNight = pricingInfo.getInt("price_pn");	
		}
				
		String[] facilities = new String[100]; //change later, use dynamic list 		
		if(jsonObj.get("has_wifi") instanceof Boolean){ //as it will be list, we do not care about other case + mention hardship of boolean/int
			if (jsonObj.getBoolean("has_wifi")) {
				facilities[0] = "Wifi";
			}
		}
		LocationCoordinates tempCoordinates = new LocationCoordinates(0.0,0.0);
		return (new Room(hotelID, distanceFromCityCenter, nameOfHotel, stars, visitorRating, summary, propertyType, hotelTypesArray, new PriceInformation(totalPrice, numberOfNights, pricePerNight), facilities, null, tempCoordinates, ""));
			
	}
	
	public static void getHotelSelectionsData(BasicPersonInfo browsingUser) {
		try {
			String urlString = ("http://yasen.hotellook.com/tp/public/widget_location_dump.json?currency=" + browsingUser.getCurrency() + "&language=" + browsingUser.getLanguage() + "&limit=" + 10000 + "&id=" + browsingUser.getLocation() + "&type=available_selections.json&check_in=" + browsingUser.getCheckInDate() + "&check_out=" + browsingUser.getCheckOutDate() + "&token=6251c90d5bc52c88b60a38bd84373513");
			
			String data = readUrl(urlString);
			
			JSONObject object = new JSONObject(data);
			JSONArray hotels = object.getJSONArray("available_selections.json");
			
			//Read All Data and Add Rooms to First Column of Table
			//int counterOfFindings = 0; //used for debugging + to keep track of existing indexes in array
			counterOfFindings = 0;
			for(int i=0; i<hotels.length(); i++) {	
				JSONObject jsonObj = hotels.getJSONObject(i);
				//hotelsTable[i][0] = readHotelSelectionsData(jsonObj);
				Room tempRoom = readHotelSelectionsData(jsonObj);
				hotelsTableIDs[0][i] = tempRoom.getHotelID();
				hotelMap.put(tempRoom.getHotelID(), tempRoom);
				counterOfFindings = i;
			}	
			
		} catch(Exception e) {
				System.out.println("Unfortunately we were not able to meet your request. Please try again later.");
		}
	}
	
	
	public static void readHotelListData(JSONObject jsonObj) { 
		int hotelID = jsonObj.getInt("id");
		
		if(hotelMap.containsKey(hotelID)) {
			/*
			JSONArray shortFacilitiesJSON = jsonObj.getJSONArray("shortFacilities"); 
			String[] shortFacilitiesArray = (hotelMap.get(hotelID)).getFacilitiesShort();
			
			/*for (int i=0;i<shortFacilitiesJSON.length();i++) { //+1 is used because the 0 position is reserved for wifi from previous request
				System.out.println(shortFacilitiesJSON.getString(i));				
			}*/
			//Remake the facilities array to get rid of the null cases 
			/*
			if (shortFacilitiesArray[0] instanceof  String) {
				String wifi = shortFacilitiesArray[0];
				shortFacilitiesArray = new String[shortFacilitiesJSON.length() + 1];
				shortFacilitiesArray[0] = wifi;
				for (int i=0;i<shortFacilitiesJSON.length();i++) { //+1 is used because the 0 position is reserved for wifi from previous request
					shortFacilitiesArray[i+1] = shortFacilitiesJSON.getString(i);				
				}
			} else {
				shortFacilitiesArray = new String[shortFacilitiesJSON.length()];
				for (int i=0;i<shortFacilitiesJSON.length();i++) { //+1 is used because the 0 position is reserved for wifi from previous request
					shortFacilitiesArray[i] = shortFacilitiesJSON.getString(i);				
				}
			} 
			
			//MUST FIXXXX GAMO TO KEFALI TOU
			hotelMap.get(hotelID).setFacilitiesShort(shortFacilitiesArray);
			System.out.println(shortFacilitiesArray[0]);
			System.out.println(shortFacilitiesJSON.getString(0));
			System.out.println(hotelMap.get(hotelID).getFacilitiesShort()[0] + "\n"); */
			
			JSONArray shortFacilitiesJSON = jsonObj.getJSONArray("shortFacilities"); 
			//String[] shortFacilitiesArray = (hotelMap.get(hotelID)).getFacilitiesShort();
			
			/*for (int i=0;i<shortFacilitiesJSON.length();i++) { //+1 is used because the 0 position is reserved for wifi from previous request
				System.out.println(shortFacilitiesJSON.getString(i));				
			}*/
			//Remake the facilities array to get rid of the null cases
			if (hotelMap.get(hotelID).getFacilitiesShort()[0] instanceof  String) {
				String wifi = hotelMap.get(hotelID).getFacilitiesShort()[0];
				//System.out.println("MAH BOYL " + shortFacilitiesJSON.length());
				hotelMap.get(hotelID).setFacilitiesShort(new String[shortFacilitiesJSON.length() + 1]);
				hotelMap.get(hotelID).getFacilitiesShort()[0] = wifi;
				for (int i=0;i<shortFacilitiesJSON.length();i++) { //+1 is used because the 0 position is reserved for wifi from previous request
					hotelMap.get(hotelID).getFacilitiesShort()[i+1] = shortFacilitiesJSON.getString(i);				
				}
			} else {
				hotelMap.get(hotelID).setFacilitiesShort(new String[shortFacilitiesJSON.length()]);
				for (int i=0;i<shortFacilitiesJSON.length();i++) { //+1 is used because the 0 position is reserved for wifi from previous request
					hotelMap.get(hotelID).getFacilitiesShort()[i] = shortFacilitiesJSON.getString(i);				
				}
			} 
			
			//MUST FIXXXX GAMO TO KEFALI TOU	
			//hotelMap.get(hotelID).setFacilitiesShort(shortFacilitiesArray);
			/*for(int i=0;i<hotelMap.get(hotelID).getFacilitiesShort().length;i++) {
				System.out.println(hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort()[i]);
			}
			System.out.println();*/
			//System.out.println(hotelMap.get(hotelID).getFacilitiesShort()[0]);
			//System.out.println(shortFacilitiesJSON.getString(0) + "\n");

						
			Node rootNode = new Node("Root");
			Node listNode = rootNode;
			//TODO: Create hasMap to turn AmenitiesID to String, use additional request
			
			JSONArray facilitiesExtendedListJSON = jsonObj.getJSONArray("facilities");
			for (int i=0;i<facilitiesExtendedListJSON.length();i++) { //+1 is used because the 0 position is reserved for wifi from previous request
				int amenityCode = facilitiesExtendedListJSON.getInt(i);
				listNode = listNode.next = new Node(amenitiesMap.get(amenityCode));				
			}
			//rootNode = rootNode.next;
			
			(hotelMap.get(hotelID)).setFacilitiesExtendedList(rootNode);
					
			JSONObject coordinatesJSON = jsonObj.getJSONObject("location");
			double latitude = coordinatesJSON.getDouble("lat");
			double longitude = coordinatesJSON.getDouble("lon");
			LocationCoordinates coordinates = new LocationCoordinates(latitude, longitude);
			
			hotelMap.get(hotelID).setCoordinates(coordinates);
			
			JSONObject addressJSON = jsonObj.getJSONObject("address");
			String address = addressJSON.getString(language);
			
			hotelMap.get(hotelID).setAddress(address); //maybe change the set method of map

		
			magicCheck += 1;
		}	
			
	}
	
	public static void getHotelListData(BasicPersonInfo browsingUser) {
			try {
				String urlString = ("http://engine.hotellook.com/api/v2/static/hotels.json?locationId=" + browsingUser.getLocation() + "&token=6251c90d5bc52c88b60a38bd84373513");
				
				String data = readUrl(urlString);
				
				JSONObject object = new JSONObject(data);
				JSONArray hotels = object.getJSONArray("hotels");
				
				//location, address, photos -> add to class

				for(int i=0; i<hotels.length(); i++) {	
					JSONObject jsonObj = hotels.getJSONObject(i);
					readHotelListData(jsonObj);					
				}
				
			
			} catch(Exception e) {
					System.out.println("Unfortunately we were not able to meet your request. Please try again later.");
			}
		}
		
	public static void displayStockOrder() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings;
		}
		for (int i = 0; i<=counter; i++) {
			////System.out.println(hotelsTable[i][0].printAll() + "\n");
			Room tempRoom = hotelMap.get(hotelsTableIDs[row][i]);
			System.out.println(tempRoom.printAll() + "\n");
		}	
	}
	
	public static void displayAlphabeticalOrderAscending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return o1.getNameOfHotel().compareTo(o2.getNameOfHotel());		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	public static void displayAlphabeticalOrderDescending() {
		Room[] hotelSortedRow = new Room[counterOfFindings + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return o2.getNameOfHotel().compareTo(o1.getNameOfHotel());		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
		
	public static void displayRatingsOrderAsscending() {
		Room[] hotelSortedRow = new Room[counterOfFindings + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Integer)o1.getVisitorRating()).compareTo(((Integer)o2.getVisitorRating()));		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	public static void displayRatingsOrderDescending() {
		Room[] hotelSortedRow = new Room[counterOfFindings + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Integer)o2.getVisitorRating()).compareTo(((Integer)o1.getVisitorRating()));		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	
	public static void displayPricingOrderAsscending() {
		Room[] hotelSortedRow = new Room[counterOfFindings + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Integer)o1.getPriceInfo().getPricePerNight()).compareTo(((Integer)o2.getPriceInfo().getPricePerNight()));		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	
	public static void displayPricingOrderDescending() {
		Room[] hotelSortedRow = new Room[counterOfFindings + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Integer)o2.getPriceInfo().getPricePerNight()).compareTo(((Integer)o1.getPriceInfo().getPricePerNight()));		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	
	public static void displayDistanceFromCityOrderAscending() {
		Room[] hotelSortedRow = new Room[counterOfFindings + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Double)o1.getDistanceFromCityCenter()).compareTo(((Double)o2.getDistanceFromCityCenter()));		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	
	public static void displayDistanceFromCityOrderDescending() {
		Room[] hotelSortedRow = new Room[counterOfFindings + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Double)o2.getDistanceFromCityCenter()).compareTo(((Double)o1.getDistanceFromCityCenter()));		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	
	public static void displayDistanceFromSeminarOrderAscending() {
		Room[] hotelSortedRow = new Room[counterOfFindings + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {				
				return (((Double)(Math.sqrt(Math.pow(o1.getCoordinates().latitude - seminarCoordinates.latitude, 2) + Math.pow(o1.getCoordinates().longitude - seminarCoordinates.longitude, 2)))).compareTo((Double)(Math.sqrt(Math.pow(o2.getCoordinates().latitude - seminarCoordinates.latitude,2) + Math.pow(o2.getCoordinates().longitude - seminarCoordinates.longitude,2)))));		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	
	public static void displayDistanceFromSeminarOrderDescending() {
		Room[] hotelSortedRow = new Room[counterOfFindings + 1];
				for(int i=0;i<=counterOfFindings;i++) {
					hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[1][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {				
				return (((Double)(Math.sqrt(Math.pow(o2.getCoordinates().latitude - seminarCoordinates.latitude, 2) + Math.pow(o2.getCoordinates().longitude - seminarCoordinates.longitude, 2)))).compareTo((Double)(Math.sqrt(Math.pow(o1.getCoordinates().latitude - seminarCoordinates.latitude,2) + Math.pow(o1.getCoordinates().longitude - seminarCoordinates.longitude,2)))));		
			}
		});
			
		for(int i=0;i<=counterOfFindings;i++) {
			System.out.println(hotelSortedRow[i].printAll() + "\n");
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
		
	
	public static void filterRooms() {
		counterOfFilteredFindings = 0;
		System.out.println("According to what criteria do you want to filter the results?");
		//Select from all properties of Room class, can be more than one attributes 
		//distance (less than/more than), location (less than), stars (range), visitorRating (minimum), min/max price, facilities
		
		//.getKey() is like min value, .getValue() is like max value
		//TODO: deal with case when only one value is inserted
		AbstractMap.SimpleEntry<Double,Double> distanceFromCityCenterRange = new AbstractMap.SimpleEntry<Double, Double>(0.0, 35.0);
		AbstractMap.SimpleEntry<Double,Double> distanceFromSeminarRange = new AbstractMap.SimpleEntry<Double, Double>(0.0, 35.0); //must check if seminar mode is on
		AbstractMap.SimpleEntry<Integer,Integer> starsRange = new AbstractMap.SimpleEntry<Integer, Integer>(0, 5);
		int visitorRatingMin = 0;
		AbstractMap.SimpleEntry<Integer,Integer> priceRange = new AbstractMap.SimpleEntry<Integer, Integer>(0, 1100); //price measured per night
		String[] facilitiesAvailable = new String[100]; //remake array when 
		
		facilitiesAvailable = new String[] {"parking"}; //only temp array to make sure it works
		
		boolean addToFilteredList = true;
		
		for(int i=0;i<=counterOfFindings;i++) {
			Room tempRoom = hotelMap.get(hotelsTableIDs[0][i]);
			//Could add all if statements in the same if, as one big truth statement but dealing with it separetely increases code comprehension
			
			addToFilteredList = true;
			
			if (!(tempRoom.getDistanceFromCityCenter() >= distanceFromCityCenterRange.getKey() && tempRoom.getDistanceFromCityCenter() <= distanceFromCityCenterRange.getValue())) {
				addToFilteredList = false;
				continue;
			}
			 
			if (seminarMode == true) {
				double distanceFromSeminar = (Math.sqrt(Math.pow(tempRoom.getCoordinates().latitude - seminarCoordinates.latitude, 2) + Math.pow(tempRoom.getCoordinates().longitude - seminarCoordinates.longitude, 2)));
				 if (!(distanceFromSeminar >= distanceFromSeminarRange.getKey() && distanceFromSeminar <= distanceFromSeminarRange.getValue())) {
					addToFilteredList = false;
					continue;
				}
			}
			
			if (!(tempRoom.getStars() >= starsRange.getKey() && tempRoom.getStars() <= starsRange.getValue())) {
				addToFilteredList = false;
				/*System.out.println(tempRoom.getStars());
				System.out.println(starsRange.getKey());
				System.out.println(starsRange.getValue());
				System.out.println(); */
				continue;
			}
			
			if(!(tempRoom.getVisitorRating() >= visitorRatingMin)) {
				addToFilteredList = false;
				continue;
			}
			
			if (!(tempRoom.getPriceInfo().getPricePerNight() >= priceRange.getKey() && tempRoom.getPriceInfo().getPricePerNight() <= priceRange.getValue())) {
				addToFilteredList = false;
				continue;
			}
			
			
			//CheckFacilities
			//String[] tempRoomFacilities = tempRoom.getFacilitiesShort();
			//Arrays.sort(facilitiesAvailable); //no need to sort
			/*for (int j=0;j<facilitiesAvailable.length;j++) {
				if (tempRoom.getFacilitiesShort()[j] instanceof String) {
			
				System.out.print(tempRoom.getFacilitiesShort()[j]); }
			} */
			
			/*for(int j=0;j<hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort().length;j++) {
				System.out.println(hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort()[j]);
			}
			Arrays.sort(hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort()); //Arrays.sort(tempRoom.getFacilitiesShort())
			for (int j=0;j<facilitiesAvailable.length;j++) {
				int check = Arrays.binarySearch(tempRoom.getFacilitiesShort(), facilitiesAvailable[j]);
				if (check < 0) {
					addToFilteredList = false;
					//break;
				}
			} */
			int counterOfFacilitiesFound = 0;
			for (int j=0;j<facilitiesAvailable.length;j++) {
				for(int l=0;l<hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort().length;l++) {
					if (hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort()[j] instanceof String && hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort()[j].equals(facilitiesAvailable[j])) {
						counterOfFacilitiesFound += 1;
						break;
						//continue;
					}
				}	
			}
			//System.out.println(counterOfFacilitiesFound);

			if (counterOfFacilitiesFound < facilitiesAvailable.length) {
				addToFilteredList = false;
				continue;
			}
			
			
			if(addToFilteredList == true) {
				hotelsTableIDs[1][i] = tempRoom.getHotelID();	
				//System.out.println(tempRoom.printAll());
				//System.out.println();
				counterOfFilteredFindings += 1;
			} 
			
			
		} 
		 

	}	
	
		
	public static void main(String[] args) {
		System.out.println("Please select mode:\n1. City-Based Mode\n2. Seminar-Based Mode");
		//Set seminar coordinates + create seminar boolean variable
		
		System.out.println("Please Select Location: \n1. Athens \n2. Thessaloniki \n3. Berlin"); //maybe can allow to insert name, or display broader list
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please enter your Check-In-Date (yyyy-mm-dd) : ");
		String checkInDate = scanner.next();
		System.out.println("Please enter your Check-Out-Date (yyyy-mm-dd) : ");
		String checkOutDate = scanner.next();
		
		language = "en";
		String currency = "eur";
		String limit = "1000";
		String locationID = "23721";
		
		BasicPersonInfo browsingUser = new BasicPersonInfo(checkInDate, checkOutDate, locationID, currency, language);

		
		//Must ask for: check-in, check-out, Currency, language. //Can also ask for limit  
		//Personalized limit
		
		//String data = readUrl("http://yasen.hotellook.com/tp/public/widget_location_dump.json?currency=eur&language=en&limit=1000&id=23721&type=available_selections.json&check_in=2018-02-02&check_out=2018-02-17&token=6251c90d5bc52c88b60a38bd84373513"); //Must change link according to info added
		
		createAmenitiesMap();
		getHotelSelectionsData(browsingUser);
		getHotelListData(browsingUser);

		System.out.println(counterOfFindings);
		System.out.println(magicCheck);
		
		//displayStockOrder();
		//displayAlphabeticalOrderAscending();
		//displayAlphabeticalOrderDescending();
		//displayRatingsOrderAsscending();
		//displayRatingsOrderDescending();
		//displayPricingOrderAsscending();
		//displayPricingOrderDescending();
		//displayDistanceFromCityOrderAscending();
		//displayDistanceFromCityOrderDescending();
		//displayDistanceFromSeminarOrderAscending();
		//displayDistanceFromSeminarOrderDescending();
		
		System.out.println("Do you want to filter the hotels displayed?");
		filterRooms(); //add option to remove filter
		filteredEnabled = true;
		System.out.println(counterOfFilteredFindings);
		displayStockOrder();
		
		//if yes create 2nd row of table with filtered results and then organize to display + adjust filter boolean
			
		//Different Display Methods -> distance from seminar (only if option is selected, otherwise do not even allow as option) -> can use different data structures to display them just for the sake of doing so
		
		//Filtered Mode
		
		//Booking Process (Can write to file info from different hotels and then read it to send info to client) -> Send email to client
		
		System.out.println("Do you want to make a reservation?");
				
	}	
		
}