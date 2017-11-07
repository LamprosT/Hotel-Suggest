import java.util.Iterator;
import java.io.*;
import java.net.*;
import java.util.*;

//Mention use of JSON Library
//Mention E-mail libtrary / api

//filewriter + e-mail to store digital receipt + send it to client
//add both seminar case and city case 

public class MainMenu {
	//static Room[][] hotelsTable = new Room[100000][2];
	static int[][] hotelsTableIDs = new int[100000][2];
	static int counterOfFindings = 0;
	static int magicCheck = 0;
	
	static Map<Integer, Room> hotelMap = new HashMap<>(); 
	
	static Map<Integer, String> amenitiesMap = new HashMap<>();
	
	
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

		return (new Room(hotelID, distanceFromCityCenter, nameOfHotel, stars, visitorRating, summary, propertyType, hotelTypesArray, new PriceInformation(totalPrice, numberOfNights, pricePerNight), facilities, null));
			
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
				hotelsTableIDs[i][0] = tempRoom.getHotelID();
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
			
			JSONArray shortFacilitiesJSON = jsonObj.getJSONArray("shortFacilities"); 
			String[] shortFacilitiesArray = (hotelMap.get(hotelID)).getFacilitiesShort();
			for (int i=0;i<shortFacilitiesJSON.length();i++) { //+1 is used because the 0 position is reserved for wifi from previous request
				shortFacilitiesArray[i+1] = shortFacilitiesJSON.getString(i);				
			}
			(hotelMap.get(hotelID)).setFacilitiesShort(shortFacilitiesArray);
						
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
		
	
	
	
	public static void main(String[] args) {
		System.out.println("Please select mode: 1. City-Based Mode\n2. Seminar-Based Mode");
		
		System.out.println("Please Select Location: \n1. Athens \n2. Thessaloniki \n3. Berlin"); //maybe can allow to insert name, or display broader list
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please enter your Check-In-Date (yyyy-mm-dd) : ");
		String checkInDate = scanner.next();
		System.out.println("Please enter your Check-Out-Date (yyyy-mm-dd) : ");
		String checkOutDate = scanner.next();
		
		String language = "en";
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
		for (int i = 0; i<=counterOfFindings; i++) {
			////System.out.println(hotelsTable[i][0].printAll() + "\n");
			Room tempRoom = hotelMap.get(hotelsTableIDs[i][0]);
			System.out.println(tempRoom.printAll() + "\n");
		}
			
		//Different Display Methods -> stock , alphabetical, populatiry, ratings, price  -> all of them ascending/descending (can possible use opposite order of for)
		
		//Filtered Mode
		
		//Booking Process (Can write to file info from different hotels and then read it to send info to client) -> Send email to client

		
		
			
						
	}	
		
}