package com.propmgr.resource;


public class CustomerResource {
	private long id;
	private long userId;
	private String userDisplayName;
	private AddressResource address;
	private PersonResource person;
	private String displayName;
	private String displayAddress;
	private String phoneNumber;
	private String mobileNumber;
	private String emailID;
	
	public CustomerResource(long id, long userId, String userDisplayName, AddressResource address, PersonResource person, String displayName, 
			String displayAddress, String phoneNumber, String mobileNumber, String emailID) {
		super();
		this.id = id;
		this.userId = userId;
		this.userDisplayName = userDisplayName;
		this.address = address;
		this.person = person;
		this.displayName = displayName;
		this.displayAddress = displayAddress;
		this.phoneNumber = phoneNumber;
		this.mobileNumber = mobileNumber;
		this.emailID = emailID;
	}

	public long getId() {
		return id;
	}

	public long getUserId() {
		return userId;
	}
	
	public String getUserDisplayName() {
		return userDisplayName;
	}

	public AddressResource getAddress() {
		return address;
	}

	public PersonResource getPerson() {
		return person;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDisplayAddress() {
		return displayAddress;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	public String getEmailID() {
		return emailID;
	}
}
