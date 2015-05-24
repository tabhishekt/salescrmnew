package com.propmgr.resource;


public class UnitAvailabilityResource {
	private String unitNumber;
	private String unitType;
	private String bookingType;
	private String parkingName;
	private String customerName;
	private String userName;
	private boolean available;
	
	public UnitAvailabilityResource(String unitNumber, String unitType, String bookingType,
			String parkingName, String customerName, String userName,
			boolean available) {
		super();
		this.unitNumber = unitNumber;
		this.unitType = unitType;
		this.bookingType = bookingType;
		this.parkingName = parkingName;
		this.customerName = customerName;
		this.userName = userName;
		this.available = available;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public String getUnitType() {
		return unitType;
	}
	
	public String getBookingType() {
		return bookingType;
	}

	public String getParkingName() {
		return parkingName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isAvailable() {
		return available;
	}
}
