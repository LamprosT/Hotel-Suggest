/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement_beanswork;

public class BasicPersonInfo { //use specialized and un-specialized class from which one inherits or have the unspecialized class as part of the attributes of the specialized
	private String checkInDate;
	private String checkOutDate;
	private String location;
	private String currency;
	private String language;
	
	public BasicPersonInfo() {
		
	}
	
	public BasicPersonInfo(String checkInDate, String checkOutDate, String location, String currency, String language) {
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.location = location;
		this.currency = currency;
		this.language = language;
		
	}
	
	/**************************/
		
	//Accessors
	public String getCheckInDate() { return checkInDate; }
	public String getCheckOutDate() { return checkOutDate; }
	public String getLocation() { return location; }
	public String getCurrency() { return currency; }
	public String getLanguage() { return language; }
		
	//Mutators
	public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }
	public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }
	public void setLocation(String location) { this.location = location; }
	public void setCurrency(String currency) { this.currency = currency; }
	public void setLanguage(String language) { this.language = language; }
	
		
	/**************************/
}