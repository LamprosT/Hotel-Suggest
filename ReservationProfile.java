/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement_beanswork;

public class ReservationProfile { //create the specialized person info
    private String reservationID; 
   
    private SpecializedPersonInfo guestDetails;
    //firstName, lastName, age, email, phoneNumber, checkInDate, checkOutDate, location, currency, language, firstName, lastName, age, email    , phoneNumber, checkOutDate, location, currency, language
    
    private Room roomBookingDetails;
    //hotelID, moneyAmountDue //if not available make it -1, hotelAddress; //so the client can keep track of attendees
    
    private String additionalComments;

    
    public ReservationProfile() {
    }

    public ReservationProfile(String reservationID, SpecializedPersonInfo guestDetails, Room roomBookingDetails, String additionalComments) {
        this.reservationID = reservationID;
        this.guestDetails = guestDetails;
        this.roomBookingDetails = roomBookingDetails;
        this.additionalComments = additionalComments;
    }

    public String getReservationID() {
        return reservationID;
    }
   
    public SpecializedPersonInfo getGuestDetails() {
        return guestDetails;
    }
   
    public Room getRoomBookingDetails() {
        return roomBookingDetails;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

                    ///////////////
    
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public void setGuestDetails(SpecializedPersonInfo guestDetails) {
        this.guestDetails = guestDetails;
    }

    public void setRoomBookingDetails(Room roomBookingDetails) {
        this.roomBookingDetails = roomBookingDetails;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }
    
    
    
    public String printReservationProfileInformation() {
        String returnedString = "";
        
        
        
        returnedString = "-> ReservationID = " + reservationID + "\n" +
        "\n-> Guest Details:" + 
        "\n-> First Name = " + guestDetails.getFirstName() +
        "\n-> Last Name = " + guestDetails.getLastName() +
        "\n-> Age = " + guestDetails.getAge() +
        "\n-> E-Mail = " + guestDetails.getEmail() +
        "\n-> Phone Number = " + guestDetails.getPhoneNumber() +
        "\n-> Check-In Date = " + guestDetails.getCheckInDate() + 
        "\n-> Check-OutDate = "  + guestDetails.getCheckOutDate() +
        "\n" +
        "\n-> Booked Room Details:" + 
        "\n-> Hotel Name = " + roomBookingDetails.getNameOfHotel() +
        "\n-> Hotel ID = " + roomBookingDetails.getHotelID() + 
        "\n-> Money Amount Due = " + roomBookingDetails.getPriceInfo().getTotalPrice() + 
        "\n-> Hotel Address = " + roomBookingDetails.getAddress() + 
        "\n" +
        "\n-> Additional Comments: " + additionalComments;
        
        return returnedString;         
    }

    
    public String printUserSentConfirmationInformation() {
        String returnedString = "";
        
        returnedString = "Dear " + guestDetails.getFirstName() + " " + guestDetails.getLastName() + ",\nYour reservation has been confirmed. Please find the relative information regarding your reservation, digital receipt, below. Do not hesitate to contact us for any further concerns of yours.\n" + printReservationProfileInformation();
        
        return returnedString;
    }
    
    public String printClientSentConfirmationInformation() {
        String returnedString = "";
        
        returnedString = "The reservation for " + guestDetails.getFirstName() + " " + guestDetails.getLastName() + " has bas been succesfully submitted.,\n The  relative information regarding the reservation, digital receipt, can be found below.\n" + printReservationProfileInformation();
        
        return returnedString;
    }
    
    public void saveTextVersionOfReservation() {
        //TODO$$: Fix
    }
    
	
	
}