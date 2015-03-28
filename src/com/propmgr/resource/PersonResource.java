package com.propmgr.resource;

import java.util.Date;

public class PersonResource {
	private String firstName;
	private String middleName;
	private String lastName;
	private ContactInfoResource contactInfo;
	private String dateOfBirth;
	private String profession;
	private String otherDetail;

	public PersonResource(String firstName, String middleName, String lastName,
			ContactInfoResource contactInfo, String dateOfBirth,
			String profession, String otherDetail) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.contactInfo = contactInfo;
		this.dateOfBirth = dateOfBirth;
		this.profession = profession;
		this.otherDetail = otherDetail;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public ContactInfoResource getContactInfo() {
		return contactInfo;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getProfession() {
		return profession;
	}

	public String getOtherDetail() {
		return otherDetail;
	}
}
