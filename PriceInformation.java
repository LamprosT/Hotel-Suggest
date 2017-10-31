public class PriceInformation {
	private int totalPrice;
	private int numberOfNights;
	private int pricePerNight; 
	public PriceInformation() {
		
	}
	
	public  PriceInformation(int totalPrice, int numberOfNights, int pricePerNight) {
		this.totalPrice = totalPrice;
		this.numberOfNights = numberOfNights;
		this.pricePerNight = pricePerNight;
	}
	
	/**************************/
		
		//Accessors
		public int getTotalPrice() { return totalPrice; }
		public int getNumberOfNights() { return numberOfNights; }
		public int getPricePerNight() { return pricePerNight; }
		
		//Mutators
		public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
		public void setNumberOfNights(int numberOfNights) { this.numberOfNights = numberOfNights; }
		public void setPricePerNight(int pricePerNight) { this.pricePerNight = pricePerNight; }
		
		
		public String printPriceInformation() {
			return("Total Price: " + Integer.toString(totalPrice) + ", Number Of Nights: " + Integer.toString(numberOfNights) + ", Price Per Night: " + Integer.toString(pricePerNight));
		}
	/**************************/
		
}
