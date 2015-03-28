package com.propmgr.resource;

import java.util.Set;



public class UnitPaymentScheduleDetailsResource {
	private long id;
	private String unitNumber;
	private String displayProjectInfo;
	private double bookingAmount;
	private double agreementvalue;
	private double totalTax;
	private double totalCost;
	private Set<UnitPaymentScheduleResource> scheduleList;
	
	public UnitPaymentScheduleDetailsResource(long id, String unitNumber,
			String displayProjectInfo, double bookingAmount,
			double agreementvalue, double totalTax, double totalCost,
			Set<UnitPaymentScheduleResource> scheduleList) {
		super();
		this.id = id;
		this.unitNumber = unitNumber;
		this.displayProjectInfo = displayProjectInfo;
		this.bookingAmount = bookingAmount;
		this.agreementvalue = agreementvalue;
		this.totalTax = totalTax;
		this.totalCost = totalCost;
		this.scheduleList = scheduleList;
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

	public double getBookingAmount() {
		return bookingAmount;
	}

	public double getAgreementvalue() {
		return agreementvalue;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public Set<UnitPaymentScheduleResource> getScheduleList() {
		return scheduleList;
	}
}
