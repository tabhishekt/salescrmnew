package com.propmgr.resource;

public class ContactInfoResource {
	private String phoneNumber;
	private String alternateNumber;
	private String mobileNumber;
	private String emailID;
	
	public ContactInfoResource(String phoneNumber, String alternateNumber,
			String mobileNumber, String emailID) {
		super();
		this.phoneNumber = phoneNumber;
		this.alternateNumber = alternateNumber;
		this.mobileNumber = mobileNumber;
		this.emailID = emailID;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getAlternateNumber() {
		return alternateNumber;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	public String getEmailID() {
		return emailID;
	}
}
