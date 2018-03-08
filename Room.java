
package hotelmanagement_beanswork;


public class Room { //TODO: Potentially add more constructors + fix prints so no commas are in the bgginning
	private int hotelID;
	private double distanceFromCityCenter;
	private String nameOfHotel;
	private int stars;
	private int visitorRating;
	private String summary;
	private String propertyType;
	private String[] hotelType;
	private PriceInformation priceInfo;
	
	private String[] facilitiesShort; 
	private Node facilitiesExtendedList; 
	
	private LocationCoordinates coordinates;
	private String address; 
		
	public Room() {

	}
	
	public Room(int hotelID, double distanceFromCityCenter, String nameOfHotel, int stars, int visitorRating, String summary, String propertyType, String[] hotelType, PriceInformation priceInfo, String[] facilitiesShort, Node facilitiesExtendedList, LocationCoordinates coordinates, String address) { 
		
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
		this.coordinates = coordinates;
		this.address = address;
		
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
	public LocationCoordinates getCoordinates() { return coordinates; }
	public String getAddress() { return address; }
	
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
	public void setCoordinates(LocationCoordinates coordinates) { this.coordinates = coordinates; }
	public void setAddress(String address) { this.address = address; }
	
	/**************************/
	public String printAll() {
		return("Hotel ID: " + this.hotelID  + 
		", Hotel Name: " + this.nameOfHotel + 
		", Distance from City Center: " + Double.toString(this.distanceFromCityCenter) + 
		", Stars: " + Integer.toString(this.stars) +
		", Visitor Rating: " + Integer.toString(this.visitorRating) + 
		", Summary: " + this.summary +
		", Property Type: " + this.propertyType + 
		", Hotel Type: " + this.printHotelTypeArray() + 
		", Price Info: " + this.priceInfo.printPriceInformation() +
		", Short Facilities: " + this.printFacilitiesShort() +
		", All Amenities: " + this.printFacilitiesExtendedList() +
		", Address: " + this.address +
		", Coordinates; Longitude: " + Double.toString(this.coordinates.longitude) + ", Latidue: " + Double.toString(this.coordinates.latitude)
		);
		
		
	}
    //return("Hotel Name: " + this.nameOfHotel + String.format("%1$"+45+ "s", this.nameOfHotel) + 
    //String.format("%-" + length + "." + length + "s", text);
    //String.format("%-" + 45 + "." + 45 + "s", this.nameOfHotel)
    //String.format("%-" + Integer.toString(this.nameOfHotel.length() - 30) + "." + Integer.toString(this.nameOfHotel.length() - 30), this.nameOfHotel)
  //  "%1$-" + "45" + "s", this.nameOfHotel

    public String printShortListForm() {
        //TODO: Implement this and the one below accordingly
        //System.out.print(String.format("%1$-" + 60 + "s", ("Hotel Name: " + this.nameOfHotel).trim()));
        //System.out.println("Check");
        

       /* StringBuilder builder = new StringBuilder();
        
        builder.append("<html><pre>");
        builder.append(String.format("%1$-" + 60 + "s", ("Hotel Name: " + this.nameOfHotel).trim()));
        builder.append("</pre></html>");
        
        return(builder.toString().trim() + 
		". Distance from City Center: " + Double.toString(this.distanceFromCityCenter) + 
		". Stars: " + Integer.toString(this.stars) +
		". Visitor Rating: " + Integer.toString(this.visitorRating) + 
		". Price Per Night: " + Integer.toString(this.priceInfo.getPricePerNight()) +
		". Short Facilities: " + this.printFacilitiesShort()
		);*/
       
        StringBuilder builder = new StringBuilder();
        
        builder.append("<html><pre>");
        builder.append(
        String.format("%1$-" + 60 + "s", ("Hotel Name: " + this.nameOfHotel + ".")) +
		String.format("%1$-" + 38 + "s", ("Distance from City Center: " + Double.toString(this.distanceFromCityCenter) + ".")) +
		String.format("%1$-" + 14 + "s", ("Stars: " + Integer.toString(this.stars) + ".")) +
		String.format("%1$-" + 25 + "s", ("Visitor Rating: " + Integer.toString(this.visitorRating) + ".")) + 
		String.format("%1$-" + 28 + "s", ("Price Per Night: " + Integer.toString(this.priceInfo.getPricePerNight()) + ".")) +
		String.format("%1$-" + 100 + "s", ("Short Facilities: " + this.printFacilitiesShort() + "."))
        );
        builder.append("</pre></html>");
        
        return(builder.toString());
		
	}
    
    public String printHotelDescriptionForm() {
        return("-> Hotel ID: " + this.hotelID  + "\n" +
		"-> Hotel Name: " + this.nameOfHotel + "\n" +
		"-> Distance from City Center: " + Double.toString(this.distanceFromCityCenter) + "\n" +
		"-> Stars: " + Integer.toString(this.stars) + "\n" +
		"-> Visitor Rating: " + Integer.toString(this.visitorRating) + "\n" +
		"-> Summary: " + this.summary + "\n" +
		"-> Property Type: " + this.propertyType + "\n" +
		"-> Hotel Type: " + this.printHotelTypeArray() + "\n" +
		"-> Price Info: " + this.priceInfo.printPriceInformation() + "\n" +
		"-> Short Facilities: " + this.printFacilitiesShort() + "\n" +
		"-> All Amenities: " + this.printFacilitiesExtendedList() + "\n" +
		"-> Address: " + this.address + "\n"
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
                    if(i==0) {
                        returnedString = this.facilitiesShort[i];
                    } else {
                        returnedString = returnedString + ", " + this.facilitiesShort[i];
                    }
				}
			}
			return returnedString;
	}
	
	public String printFacilitiesExtendedList() {
		String returnedString = "";
		Node rootNode = this.facilitiesExtendedList;
        boolean firstHasBeenAdded = false;
        
		while(rootNode != null) {
            if(firstHasBeenAdded) {
                returnedString = returnedString + ", " + rootNode.data;
            } else {
                returnedString = rootNode.data;
                firstHasBeenAdded = true;
            }
			
			rootNode = rootNode.next;		
		}	
		return returnedString;
	}	
	

	
	

	
}
