
public class Room { //could have class that inherits if lets say the summary of priceInfo do not exist
	private int hotelID;
	private double distanceFromCityCenter;
	private String nameOfHotel;
	private int stars;
	private int visitorRating;
	private String summary;
	private String propertyType;
	private String[] hotelType;
	private PriceInformation priceInfo;
	
	//'wifi', 'restaurant', 'parking', 'non-smoking', 'pets', 'tv', 'laundry', 'conditioning', 'internet', 'pool', 'fitness'
	private String[] facilitiesShort; //include with other aminities and facilities
	private Node facilitiesExtendedList; 
	
	
	//facilities from different request + include location? + calculate distance from seminar location using longitude/latitude? 
	
	public Room() {

	}
	
	public Room(int hotelID, double distanceFromCityCenter, String nameOfHotel, int stars, int visitorRating, String summary, String propertyType, String[] hotelType, PriceInformation priceInfo, String[] facilitiesShort, Node facilitiesExtendedList) { 
		
		this.hotelID = hotelID;
		this.distanceFromCityCenter = distanceFromCityCenter;
		this.nameOfHotel = nameOfHotel;
		this.stars = stars;
		this.visitorRating = visitorRating;
		this.summary = summary;
		this.propertyType = propertyType;
		this.hotelType = hotelType;
		this.priceInfo = priceInfo;
		this.facilitiesShort = facilitiesShort;
		this.facilitiesExtendedList = facilitiesExtendedList;
		
		}
		
		
	/**************************/
	
	//Accessors
	public int getHotelID() {	return hotelID; }
	public double getDistanceFromCityCenter() {	return distanceFromCityCenter; }
	public String getNameOfHotel() { return nameOfHotel; }
	public int getStars() { return stars; }
	public int getVisitorRating() { return visitorRating; }
	public String getSumary() { return summary; }
	public String getPropertyType() { return propertyType; }
	public String[] getHotelType() { return hotelType; }
	public PriceInformation getPriceInfo() { return priceInfo; }
	public String[] getFacilitiesShort() { return facilitiesShort; }
	public Node getFacilitiesExtendedList() { return facilitiesExtendedList; }
	
	//Mutators
	public void setHotelID(int hotelID) { this.hotelID = hotelID; }
	public void setDistanceFromCityCenter(double distanceFromCityCenter) { this.distanceFromCityCenter = distanceFromCityCenter; }
	public void setNameOfHotel(String nameOfHotel) { this.nameOfHotel = nameOfHotel; }
	public void setStars(int stars) { this.stars = stars; }
	public void setVisitorRating(int visitorRating) { this.visitorRating = visitorRating; }
	public void setSummary(String summary) { this.summary = summary; }
	public void setPropertyType(String propertyType) { this.propertyType = propertyType; }
	public void setHotelType(String[] hotelType) { this.hotelType = hotelType; }
	public void setPriceInfo(PriceInformation priceInfo) { this.priceInfo = priceInfo;}
	public void setFacilitiesShort(String[] facilities) { this.facilitiesShort = facilitiesShort; }
	public void setFacilitiesExtendedList(Node facilitiesExtendedList) { this.facilitiesExtendedList = facilitiesExtendedList; }
	
	/**************************/
	public String printAll() {
		return("Hotel ID: " + this.hotelID + 
		", Hotel Name: " + this.nameOfHotel + 
		", Distance from City Center: " + Double.toString(this.distanceFromCityCenter) + 
		", Stars: " + Integer.toString(this.stars) +
		", Visitor Rating: " + Integer.toString(this.visitorRating) + 
		", Summary: " + this.summary +
		", Property Type: " + this.propertyType + 
		", Hotel Type: " + this.printHotelTypeArray() + 
		", Price Info: " + this.priceInfo.printPriceInformation() +
		", Short Facilities: " + this.printFacilitiesShort() +
		", All Amenities: " + this.printFacilitiesExtendedList()	
		);
		
		
	}
	
	public String printHotelTypeArray() {
		String returnedString = "";
		for (int i = 0; i < this.hotelType.length; i++) {
			returnedString = returnedString + ", " + this.hotelType[i];
		}
		return returnedString;
	}
	
	public String printFacilitiesShort() {
			String returnedString = "";
			for (int i = 0; i < this.facilitiesShort.length; i++) {
				if(this.facilitiesShort[i] instanceof  String ) {
				returnedString = returnedString + ", " + this.facilitiesShort[i];
				}
			}
			return returnedString;
	}
	
	public String printFacilitiesExtendedList() {
		String returnedString = "";
		Node rootNode = this.facilitiesExtendedList;
		
		while(rootNode.next != null) {
			rootNode = rootNode.next;	
			returnedString = returnedString + ", " + rootNode.data;	
		}
		
		
		return returnedString;
	}	
	
	

	
}
