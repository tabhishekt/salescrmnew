package com.propmgr.resource;

import java.util.List;

public class UnitResource {
	private long id;
	private long buildingId;
	private UnitPricePolicyResource unitPricePolicy;
	private String displayProjectInfo;
	private CodeTableResource unitType;
	private String unitNumber;
	private int floorNumber;
	private CodeTableResource floorType;
	private long carpetArea;
	private long saleableArea;
	private double floorRise;
	private boolean otherOptions;
	private List<CodeTableResource> amenities;
	private double bookingAmount;
	private double otherCharges;
	private double agreementvalue;
	private double totalTax;
	private double totalCostWithTax;
	private double totalCost;
	private boolean available;
	private boolean registered;
	
	public UnitResource(long id, long buildingId,
			UnitPricePolicyResource unitPricePolicy, String displayProjectInfo,
			CodeTableResource unitType, String unitNumber, int floorNumber,
			CodeTableResource floorType, long carpetArea, long saleableArea,
			double floorRise, boolean otherOptions, List<CodeTableResource> amenities,
			double bookingAmount, double otherCharges, double agreementvalue, double totalTax, 
			double totalCostWithTax, double totalCost, boolean available, boolean registered) {
		super();
		this.id = id;
		this.buildingId = buildingId;
		this.unitPricePolicy = unitPricePolicy;
		this.displayProjectInfo = displayProjectInfo;
		this.unitType = unitType;
		this.unitNumber = unitNumber;
		this.floorNumber = floorNumber;
		this.floorType = floorType;
		this.carpetArea = carpetArea;
		this.saleableArea = saleableArea;
		this.floorRise = floorRise;
		this.otherOptions = otherOptions;
		this.amenities = amenities;
		this.bookingAmount = bookingAmount;
		this.otherCharges = otherCharges;
		this.agreementvalue = agreementvalue;
		this.totalTax = totalTax;
		this.totalCostWithTax = totalCostWithTax;
		this.totalCost = totalCost;
		this.available = available;
		this.registered = registered;
	}

	public long getId() {
		return id;
	}

	public long getBuildingId() {
		return buildingId;
	}

	public UnitPricePolicyResource getUnitPricePolicy() {
		return unitPricePolicy;
	}

	public String getDisplayProjectInfo() {
		return displayProjectInfo;
	}

	public CodeTableResource getUnitType() {
		return unitType;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public CodeTableResource getFloorType() {
		return floorType;
	}

	public long getCarpetArea() {
		return carpetArea;
	}

	public long getSaleableArea() {
		return saleableArea;
	}

	public double getFloorRise() {
		return floorRise;
	}

	public boolean isOtherOptions() {
		return otherOptions;
	}

	public List<CodeTableResource> getAmenities() {
		return amenities;
	}
	
	public double getBookingAmount() {
		return bookingAmount;
	}
	
	public double getOtherCharges() {
		return otherCharges;
	}

	public double getAgreementvalue() {
		return agreementvalue;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public double getTotalCostWithTax() {
		return totalCostWithTax;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public boolean isAvailable() {
		return available;
	}

	public boolean isRegistered() {
		return registered;
	}
}
