package com.propmgr.resource;


public class UnitPriceDetailResource {
	private long id;
	private String unitNumber;
	private String displayProjectInfo;
	private long carpetArea;
	private long saleableArea;
	private double baserate;
	private double discount;
	private double deductionOnOtherCharges;
	private double floorrise;
	private double totalrate;
	private double stampduty;
	private double registrationcharge;
	private double servicetax;
	private double valueaddedtax;
	private double maintenancecharge1;
	private double maintenancecharge2;
	private double legalcharge;
	private double othercharges;
	private double totalcharges;
	private double basicCost;
	private double agreementvalue;
	private double totalTax;
	private double totalCostWithTax;
	private double totalCost;
	
	public UnitPriceDetailResource(long id, String unitNumber, String displayProjectInfo, 
			long carpetArea, long saleableArea, double baserate, double discount, double deductionOnOtherCharges, 
			double floorrise, double totalrate, double stampduty, double registrationcharge, double servicetax,
			double valueaddedtax, double maintenancecharge1, double maintenancecharge2, double legalcharge, 
			double othercharges, double totalcharges, double basicCost, double agreementvalue, double totalTax, 
			double totalCostWithTax, double totalCost) {
		super();
		this.id = id;
		this.unitNumber = unitNumber;
		this.displayProjectInfo = displayProjectInfo;
		this.saleableArea = saleableArea;
		this.carpetArea = carpetArea;
		this.baserate = baserate;
		this.discount = discount;
		this.deductionOnOtherCharges = deductionOnOtherCharges;
		this.floorrise = floorrise;
		this.totalrate = totalrate;
		this.stampduty = stampduty;
		this.registrationcharge = registrationcharge;
		this.servicetax = servicetax;
		this.valueaddedtax = valueaddedtax;
		this.maintenancecharge1 = maintenancecharge1;
		this.maintenancecharge2 = maintenancecharge2;
		this.legalcharge = legalcharge;
		this.othercharges = othercharges;
		this.totalcharges = totalcharges;
		this.basicCost = basicCost;
		this.agreementvalue = agreementvalue;
		this.totalTax = totalTax;
		this.totalCostWithTax = totalCostWithTax;
		this.totalCost = totalCost;
	}

	public long getId() {
		return id;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public String getDisplayProjectInfo() {
		return displayProjectInfo;
	}

	public long getSaleableArea() {
		return saleableArea;
	}

	public long getCarpetArea() {
		return carpetArea;
	}

	public double getBaserate() {
		return baserate;
	}

	public double getDiscount() {
		return discount;
	}
	
	public double getDeductionOnOtherCharges() {
		return deductionOnOtherCharges;
	}

	public double getFloorrise() {
		return floorrise;
	}
	
	public double getTotalrate() {
		return totalrate;
	}

	public double getStampduty() {
		return stampduty;
	}

	public double getRegistrationcharge() {
		return registrationcharge;
	}

	public double getServicetax() {
		return servicetax;
	}

	public double getValueaddedtax() {
		return valueaddedtax;
	}

	public double getMaintenancecharge1() {
		return maintenancecharge1;
	}

	public double getMaintenancecharge2() {
		return maintenancecharge2;
	}

	public double getLegalcharge() {
		return legalcharge;
	}

	public double getOthercharges() {
		return othercharges;
	}

	public double getTotalcharges() {
		return totalcharges;
	}

	public double getBasicCost() {
		return basicCost;
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
}
