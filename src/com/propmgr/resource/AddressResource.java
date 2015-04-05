package com.propmgr.resource;

public class AddressResource {
	private String addressLine1;
	private String addressLine2;
	private CodeTableResource city;
	private CodeTableResource state;
	private String zipCode;
	
	public AddressResource(String addressLine1, String addressLine2,
			CodeTableResource city, CodeTableResource state, String zipCode) {
		super();
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public CodeTableResource getCity() {
		return city;
	}

	public CodeTableResource getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}
}
