public class SpecializedPersonInfo extends BasicPersonInfo { //what is *implements*, 
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String phoneNumber;
	
	public  SpecializedPersonInfo() {
	
	}
	
	public SpecializedPersonInfo(String checkInDate, String checkOutDate, String location, String currency, String language, String firstName, String lastName, int age, String email, String phoneNumber) {	
		super(checkInDate, checkOutDate, location, currency, language);
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.phoneNumber = email;
	}
	
	/**************************/
			
	//Accessors
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public int getAge() { return age; }
	public String getEmail() { return email; }
	public String getPhoneNumber() { return phoneNumber; }
	
	//Mutators
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public void setAge(int age) { this.age = age; }
	public void setEmail(String email) { this.email = email; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
		
	/**************************/
	
		
}