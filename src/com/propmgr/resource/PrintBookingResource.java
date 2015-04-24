package com.propmgr.resource;

import java.util.List;
import java.util.Set;


public class PrintBookingResource {
	private long id;
	private long bookingFormNumber;
	private String userDisplayName;
	private String bookingDate;
	private CodeTableResource buildingCurrentStatus;
	private double balancePaymentForCurrentStatus;
	private OrganizationResource organization;
	private CustomerResource customer;
	private UnitResource unit;
	private double discount;
	private double deductionOnOtherCharges;
	private String comment;
	private UnitPriceDetailResource priceDetails;
	private List<PaymentResource> paymentList;
	private List<ProjectBankAccountResource> projectBankAccounts;
	private double totalPaymentReceived;
	private String termsAndConditions;
	private Set<UnitPaymentScheduleResource> scheduleList;
	
	public PrintBookingResource(long id, long bookingFormNumber,
			String userDisplayName, String bookingDate, CodeTableResource buildingCurrentStatus,
			double balancePaymentForCurrentStatus, OrganizationResource organization,
			CustomerResource customer, UnitResource unit, double discount, double deductionOnOtherCharges,
			String comment, UnitPriceDetailResource priceDetails,
			List<PaymentResource> paymentList, double totalPaymentReceived,
			List<ProjectBankAccountResource> projectBankAccounts, String termsAndConditions,
			Set<UnitPaymentScheduleResource> scheduleList) {
		super();
		this.id = id;
		this.bookingFormNumber = bookingFormNumber;
		this.userDisplayName = userDisplayName;
		this.bookingDate = bookingDate;
		this.buildingCurrentStatus = buildingCurrentStatus;
		this.balancePaymentForCurrentStatus = balancePaymentForCurrentStatus;
		this.organization = organization;
		this.customer = customer;
		this.unit = unit;
		this.discount = discount;
		this.deductionOnOtherCharges = deductionOnOtherCharges;
		this.comment = comment;
		this.priceDetails = priceDetails;
		this.paymentList = paymentList;
		this.totalPaymentReceived = totalPaymentReceived;
		this.projectBankAccounts = projectBankAccounts;
		this.termsAndConditions = termsAndConditions;
		this.scheduleList = scheduleList;
	}

	public long getId() {
		return id;
	}

	public long getBookingFormNumber() {
		return bookingFormNumber;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public CodeTableResource getBuildingCurrentStatus() {
		return buildingCurrentStatus;
	}

	public double getBalancePaymentForCurrentStatus() {
		return balancePaymentForCurrentStatus;
	}

	public OrganizationResource getOrganization() {
		return organization;
	}

	public CustomerResource getCustomer() {
		return customer;
	}

	public UnitResource getUnit() {
		return unit;
	}

	public double getDiscount() {
		return discount;
	}

	public double getDeductionOnOtherCharges() {
		return deductionOnOtherCharges;
	}

	public String getComment() {
		return comment;
	}

	public UnitPriceDetailResource getPriceDetails() {
		return priceDetails;
	}

	public List<PaymentResource> getPaymentList() {
		return paymentList;
	}

	public double getTotalPaymentReceived() {
		return totalPaymentReceived;
	}

	public List<ProjectBankAccountResource> getProjectBankAccounts() {
		return projectBankAccounts;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public Set<UnitPaymentScheduleResource> getScheduleList() {
		return scheduleList;
	}
}
