
package hotelmanagement_beanswork;

/**
 *
 * @author LambrosTzanetos
 */
import java.io.*;
import java.net.*;
import java.util.*;
     
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.PasswordAuthentication;
import javax.activation.*;


public class MainMenu {
	static int[][] hotelsTableIDs = new int[2][10000];
	static int counterOfFindings = 0;
	static int counterOfFilteredFindings = 0;
	static int magicCheck = 0;
	static String language = "";
	static boolean filteredEnabled = false;
	static boolean seminarMode = false;
	
	static Map<Integer, Room> hotelMap = new HashMap<>(); 
	
	static Map<Integer, String> amenitiesMap = new HashMap<>();
    
    static Seminar[] seminarArray;
	
	static LocationCoordinates seminarCoordinates = new LocationCoordinates(0,0);
    
	static String reservationReceiptText;
	
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


		} catch(Exception e) {
			System.out.println("Ameinities Not Found");				
		}
	}
		
			
	public static Room readHotelSelectionsData(JSONObject jsonObj) { 
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
				
		String[] facilities = new String[100];  		
		if(jsonObj.get("has_wifi") instanceof Boolean){
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
            
			
			JSONArray shortFacilitiesJSON = jsonObj.getJSONArray("shortFacilities");   
			
			if (hotelMap.get(hotelID).getFacilitiesShort()[0] instanceof String) {
				String wifi = hotelMap.get(hotelID).getFacilitiesShort()[0];
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
						
			Node rootNode = new Node("Root");
			Node listNode = rootNode;
			
			JSONArray facilitiesExtendedListJSON = jsonObj.getJSONArray("facilities");
			for (int i=0;i<facilitiesExtendedListJSON.length();i++) { //+1 is used because the 0 position is reserved for wifi from previous request
				int amenityCode = facilitiesExtendedListJSON.getInt(i);
				listNode = listNode.next = new Node(amenitiesMap.get(amenityCode));				
			}

			(hotelMap.get(hotelID)).setFacilitiesExtendedList(rootNode);
					
			JSONObject coordinatesJSON = jsonObj.getJSONObject("location");
			double latitude = coordinatesJSON.getDouble("lat");
			double longitude = coordinatesJSON.getDouble("lon");
			LocationCoordinates coordinates = new LocationCoordinates(latitude, longitude);
			
			hotelMap.get(hotelID).setCoordinates(coordinates);
			
			JSONObject addressJSON = jsonObj.getJSONObject("address");
			String address = addressJSON.getString(language);
			
			hotelMap.get(hotelID).setAddress(address);

			magicCheck += 1;
		}	
			
	}
	
	public static void getHotelListData(BasicPersonInfo browsingUser) {
			try {
				String urlString = ("http://engine.hotellook.com/api/v2/static/hotels.json?locationId=" + browsingUser.getLocation() + "&token=6251c90d5bc52c88b60a38bd84373513");
				
				String data = readUrl(urlString);
				JSONObject object = new JSONObject(data);
				JSONArray hotels = object.getJSONArray("hotels");

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
			counter = counterOfFilteredFindings - 1;
		}
		for (int i = 0; i<=counter; i++) {
			Room tempRoom = hotelMap.get(hotelsTableIDs[row][i]);
            hotelsTableIDs[1][i] = hotelsTableIDs[row][i];
		}	
	}
	
	public static void displayAlphabeticalOrderAscending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return o1.getNameOfHotel().compareTo(o2.getNameOfHotel());		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	//sorted column
		}	
	}
	
	public static void displayAlphabeticalOrderDescending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return o2.getNameOfHotel().compareTo(o1.getNameOfHotel());		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
		
	public static void displayRatingsOrderAscending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
                
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Integer)o1.getVisitorRating()).compareTo(((Integer)o2.getVisitorRating()));		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	public static void displayRatingsOrderDescending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Integer)o2.getVisitorRating()).compareTo(((Integer)o1.getVisitorRating()));		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	public static void displayPricingOrderAscending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1; //-1 is used because it represents actual findings whereas is counteroffindings represented number of findings in indexed form
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Integer)o1.getPriceInfo().getPricePerNight()).compareTo(((Integer)o2.getPriceInfo().getPricePerNight()));		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	public static void displayPricingOrderDescending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Integer)o2.getPriceInfo().getPricePerNight()).compareTo(((Integer)o1.getPriceInfo().getPricePerNight()));		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	public static void displayDistanceFromCityOrderAscending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Double)o1.getDistanceFromCityCenter()).compareTo(((Double)o2.getDistanceFromCityCenter()));		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	public static void displayDistanceFromCityOrderDescending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return ((Double)o2.getDistanceFromCityCenter()).compareTo(((Double)o1.getDistanceFromCityCenter()));		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	public static void displayDistanceFromSeminarOrderAscending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					//hotelsTableIDs[1][i] = hotelsTableIDs[0][i];
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {				
				return (((Double)(Math.sqrt(Math.pow(o1.getCoordinates().latitude - seminarCoordinates.latitude, 2) + Math.pow(o1.getCoordinates().longitude - seminarCoordinates.longitude, 2)))).compareTo((Double)(Math.sqrt(Math.pow(o2.getCoordinates().latitude - seminarCoordinates.latitude,2) + Math.pow(o2.getCoordinates().longitude - seminarCoordinates.longitude,2)))));		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
	
	public static void displayDistanceFromSeminarOrderDescending() {
		int row = 0;
		int counter = counterOfFindings;
		if (filteredEnabled) {
			row = 1;
			counter = counterOfFilteredFindings - 1;
		}
		Room[] hotelSortedRow = new Room[counter + 1];
				for(int i=0;i<=counter;i++) {
					hotelSortedRow[i] = hotelMap.get(hotelsTableIDs[row][i]);				
				}
				
		Arrays.sort(hotelSortedRow, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {				
				return (((Double)(Math.sqrt(Math.pow(o2.getCoordinates().latitude - seminarCoordinates.latitude, 2) + Math.pow(o2.getCoordinates().longitude - seminarCoordinates.longitude, 2)))).compareTo((Double)(Math.sqrt(Math.pow(o1.getCoordinates().latitude - seminarCoordinates.latitude,2) + Math.pow(o1.getCoordinates().longitude - seminarCoordinates.longitude,2)))));		
			}
		});
			
		for(int i=0;i<=counter;i++) {
			hotelsTableIDs[1][i] = hotelSortedRow[i].getHotelID();	
		}	
	}
		
    
    //BOTH FILTER FUNCTIONS HAVE THE SOME FUNCTION, DIFFERENCE IS IN PARAMETERS (IF SEMINAR CASE EXISTS)
	public static void filterRooms(double distanceFromCityCenterMin, double distanceFromCityCenterMax, int starsMin, int starsMax, int priceMin, int priceMax, String[] facilities, int visitorRating) {
        
		counterOfFilteredFindings = 0;
        filteredEnabled = true;
		//Select from all properties of Room class, can be more than one attributes 
		//distance (less than/more than), location (less than), stars (range), visitorRating (minimum), min/max price, facilities
		
		//.getKey() is like min value, .getValue() is like max value
		AbstractMap.SimpleEntry<Double,Double> distanceFromCityCenterRange = new AbstractMap.SimpleEntry<Double, Double>(distanceFromCityCenterMin, distanceFromCityCenterMax);
		AbstractMap.SimpleEntry<Integer,Integer> starsRange = new AbstractMap.SimpleEntry<Integer, Integer>(starsMin, starsMax);
		int visitorRatingMin = visitorRating;
		AbstractMap.SimpleEntry<Integer,Integer> priceRange = new AbstractMap.SimpleEntry<Integer, Integer>(priceMin, priceMax); //price measured per night
		String[] facilitiesAvailable = facilities;
		
		boolean addToFilteredList = true;
		
		for(int i=0;i<=counterOfFindings;i++) {
			Room tempRoom = hotelMap.get(hotelsTableIDs[0][i]);
			
			addToFilteredList = true;
			
			if (!(tempRoom.getDistanceFromCityCenter() >= distanceFromCityCenterRange.getKey() && tempRoom.getDistanceFromCityCenter() <= distanceFromCityCenterRange.getValue())) {
				addToFilteredList = false;
			}
			
			if (!(tempRoom.getStars() >= starsRange.getKey() && tempRoom.getStars() <= starsRange.getValue())) {
				addToFilteredList = false;
			}
			
			if(!(tempRoom.getVisitorRating() >= visitorRatingMin)) {
				addToFilteredList = false;
			}
			
			if (!(tempRoom.getPriceInfo().getPricePerNight() >= priceRange.getKey() && tempRoom.getPriceInfo().getPricePerNight() <= priceRange.getValue())) {
				addToFilteredList = false;
			}
		
			int counterOfFacilitiesFound = 0;
			for (int j=0;j<facilitiesAvailable.length;j++) {
				for(int l=0;l<hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort().length;l++) {
					if (hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort()[l] instanceof String && hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort()[l].equals(facilitiesAvailable[j])) {
						counterOfFacilitiesFound += 1;
					}
				}	
			}

			if (counterOfFacilitiesFound < facilitiesAvailable.length) {
				addToFilteredList = false;
			}
			
			if(addToFilteredList == true) {
				hotelsTableIDs[1][counterOfFilteredFindings] = tempRoom.getHotelID();	
				counterOfFilteredFindings += 1;
			} 	
		} 
	}	

    public static void filterRooms(double distanceFromCityCenterMin, double distanceFromCityCenterMax, int starsMin, int starsMax, int priceMin, int priceMax, String[] facilities, double distanceFromSeminarMin, double distanceFromSeminarMax, int visitorRating) { 
        if(seminarMode) {
            counterOfFilteredFindings = 0;
            filteredEnabled = true;
            //Select from all properties of Room class, can be more than one attributes 
            //distance (less than/more than), location (less than), stars (range), visitorRating (minimum), min/max price, facilities

            //.getKey() is like min value, .getValue() is like max value
            AbstractMap.SimpleEntry<Double,Double> distanceFromCityCenterRange = new AbstractMap.SimpleEntry<Double, Double>(distanceFromCityCenterMin, distanceFromCityCenterMax);
            AbstractMap.SimpleEntry<Double,Double> distanceFromSeminarRange = new AbstractMap.SimpleEntry<Double, Double>(distanceFromSeminarMin, distanceFromSeminarMax); //must check if seminar mode is on
            AbstractMap.SimpleEntry<Integer,Integer> starsRange = new AbstractMap.SimpleEntry<Integer, Integer>(starsMin, starsMax);
            int visitorRatingMin = visitorRating;
            AbstractMap.SimpleEntry<Integer,Integer> priceRange = new AbstractMap.SimpleEntry<Integer, Integer>(priceMin, priceMax); //price measured per night
            String[] facilitiesAvailable = facilities;

            boolean addToFilteredList = true;

            for(int i=0;i<=counterOfFindings;i++) {
                Room tempRoom = hotelMap.get(hotelsTableIDs[0][i]);

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

                int counterOfFacilitiesFound = 0;
                for (int j=0;j<facilitiesAvailable.length;j++) {
                    for(int l=0;l<hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort().length;l++) {
                        if (hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort()[l] instanceof String && hotelMap.get(hotelsTableIDs[0][i]).getFacilitiesShort()[l].equals(facilitiesAvailable[j])) {
                            counterOfFacilitiesFound += 1;
                            break;
                        }
                    }	
                }

                if (counterOfFacilitiesFound < facilitiesAvailable.length) {
                    addToFilteredList = false;
                    continue;
                }


                if(addToFilteredList == true) {
                    hotelsTableIDs[1][counterOfFilteredFindings] = tempRoom.getHotelID();	
                    counterOfFilteredFindings += 1;
                } 


            } 
		 
        }
	}	

    public static void reservationProcess(BasicPersonInfo browsingUser, String firstName, String lastName, int age, String email, String phoneNumber, String idOfSelectedHotel, String additionalComments) {

         
         SpecializedPersonInfo reservationUser = new SpecializedPersonInfo(browsingUser.getCheckInDate(), browsingUser.getCheckOutDate(),browsingUser.getLocation(), browsingUser.getCurrency(), browsingUser.getLanguage(), firstName, lastName, age, email, phoneNumber);
         
         String reservationID = firstName.charAt(0) + lastName.charAt(0) + idOfSelectedHotel;

         ReservationProfile reservation = new ReservationProfile(reservationID, reservationUser, hotelMap.get(Integer.parseInt(idOfSelectedHotel)), additionalComments);
         
         emailSubmission(reservation);
                
    }
    
    
    public static void emailSubmission(ReservationProfile reservation) {
      
        String from = "stamatinacoins@gmail.com";
        String password = "lt13355779";
        String toClient = "stamatinacoins@gmail.com";
        String toUser = reservation.getGuestDetails().getEmail(); //"latz2000@gmail.com";
        String sub = "Reservation Confirmation";
        String msgToClient = reservation.printClientSentConfirmationInformation();
        String msgToUser = reservation.printUserSentConfirmationInformation();
        
        reservationReceiptText = msgToUser;
        

        //Get properties object    
        Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");    
        //get Session   
        Session session = Session.getDefaultInstance(props,    
            new javax.mail.Authenticator() {    
                protected PasswordAuthentication getPasswordAuthentication() {    
                return new PasswordAuthentication(from,password);                 
            }         
        }); 
            
        //compose message    
        try {    
            MimeMessage messageToClient = new MimeMessage(session);    
            messageToClient.addRecipient(Message.RecipientType.TO,new InternetAddress(toClient));    
            messageToClient.setSubject(sub);    
            messageToClient.setText(msgToClient);    

            MimeMessage messageToUser = new MimeMessage(session);    
            messageToUser.addRecipient(Message.RecipientType.TO,new InternetAddress(toUser));    
            messageToUser.setSubject(sub);    
            messageToUser.setText(msgToUser); 


            //send message 
            Transport.send(messageToClient);     
            Transport.send(messageToUser);   

            System.out.println("message sent successfully");  
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }    

    }  
   
	
    public static void readSeminars() {
        
        try {
            File file = new File("/Users/LambrosTzanetos/Dropbox/IB/CS/InternalAssessment/HotelManagement_BeansWork/src/hotelmanagement_beanswork/seminars.txt");
            if (file.exists()) {
                FileReader sourceFile = new FileReader(file); //source of file that we will read from
                BufferedReader inStream = new BufferedReader(sourceFile); //create inStream to read from the file declared above
                
                String line = inStream.readLine();
                seminarArray = new Seminar[Integer.parseInt(line)];
                
                for(int i=0;i<seminarArray.length;i++) {
                    line = inStream.readLine();
                    StringTokenizer st = new StringTokenizer(line, ",");

                    if(st.countTokens() == 3) { 
                        String name = st.nextToken();
                        double latitude = Double.parseDouble(st.nextToken());
                        double longitude = Double.parseDouble(st.nextToken()); 
                        Seminar tempSeminar = new Seminar(name, new LocationCoordinates(latitude, longitude));
                        seminarArray[i] = tempSeminar;
                    }
                }
                inStream.close();
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
	public static void main(String[] args) {
		//Must ask for: check-in, check-out, Currency, language. //Can also ask for limit  
		//Personalized limit

		//String data = readUrl("http://yasen.hotellook.com/tp/public/widget_location_dump.json?currency=eur&language=en&limit=1000&id=23721&type=available_selections.json&check_in=2018-02-02&check_out=2018-02-17&token=6251c90d5bc52c88b60a38bd84373513"); //Must change link according to info added
		
		
		//Different Display Methods -> distance from seminar (only if option is selected, otherwise do not even allow as option) -> can use different data structures to display them just for the sake of doing so
		
		//Booking Process (Can write to file info from different hotels and then read it to send info to client) -> Send email to client
		
		//System.out.println("Do you want to make a reservation?");
                
                //must ask for reservation ID //or take it through GUI
                //filewriter + e-mail to store digital receipt + send it to client
                //option to cancel reservation mid-way
                //option to return to main screen
        
       // Singleton singleton = Singleton.getInstance();
       // Singleton.getInstance().check = "working";
        
        //reservationProcess(browsingUser); 
	}
		
}