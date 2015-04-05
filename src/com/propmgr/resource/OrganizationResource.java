package com.propmgr.resource;

public class OrganizationResource {
	private long id;
	private String name;
	private ContactInfoResource contactInfo;
	private AddressResource address;
	private PersonResource contactPerson;
	private String displayAddress;
	
	
	
	public OrganizationResource(long id, String name, ContactInfoResource contactInfo,
			AddressResource address, PersonResource contactPerson,
			String displayAddress) {
		super();
		this.id = id;
		this.name = name;
		this.contactInfo = contactInfo;
		this.address = address;
		this.contactPerson = contactPerson;
		this.displayAddress = displayAddress;
	}
	
	public long getId() {
		return id;
	}
	public String getDisplayAddress() {
		return displayAddress;
	}
	public String getName() {
		return name;
	}

	public ContactInfoResource getContactInfo() {
		return contactInfo;
	}

	public AddressResource getAddress() {
		return address;
	}

	public PersonResource getContactPerson() {
		return contactPerson;
	}
}
