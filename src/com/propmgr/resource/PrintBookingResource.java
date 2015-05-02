package com.propmgr.resource;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class PrintBookingResource {
	private long id;
	private long bookingFormNumber;
	private String userDisplayName;
	private String currentDate;
	private String bookingDate;
	private CodeTableResource buildingCurrentStatus;
	private double totalDueForCurrentStatus;
	private double balancePaymentForCurrentStatus;
	private OrganizationResource organization;
	private String projectName;
	private String projectPhaseName;
	private String projectBuildingName;
	private String displayProjectInfo;
	private CustomerResource customer;
	private UnitResource unit;
	private double discount;
	private double deductionOnOtherCharges;
	private String comment;
	private UnitPriceDetailResource priceDetails;
	private List<PaymentResource> paymentList;
	private Map<String, String> projectBankAccounts;
	private double totalPaymentReceived;
	private String termsAndConditions;
	private Set<UnitPaymentScheduleResource> scheduleList;
	
	public PrintBookingResource(long id, long bookingFormNumber, String userDisplayName, String currentDate, 
			String bookingDate, CodeTableResource buildingCurrentStatus, double totalDueForCurrentStatus,
			double balancePaymentForCurrentStatus, OrganizationResource organization, String displayProjectInfo,
			String projectName, String projectPhaseName, String projectBuildingName, CustomerResource customer, UnitResource unit, 
			double discount, double deductionOnOtherCharges, String comment, UnitPriceDetailResource priceDetails,
			List<PaymentResource> paymentList, double totalPaymentReceived,
			Map<String, String> projectBankAccounts, String termsAndConditions,
			Set<UnitPaymentScheduleResource> scheduleList) {
		super();
		this.id = id;
		this.bookingFormNumber = bookingFormNumber;
		this.userDisplayName = userDisplayName;
		this.currentDate = currentDate;
		this.bookingDate = bookingDate;
		this.buildingCurrentStatus = buildingCurrentStatus;
		this.totalDueForCurrentStatus = totalDueForCurrentStatus;
		this.balancePaymentForCurrentStatus = balancePaymentForCurrentStatus;
		this.organization = organization;
		this.displayProjectInfo = displayProjectInfo;
		this.projectName = projectName;
		this.projectPhaseName = projectPhaseName;
		this.projectBuildingName = projectBuildingName;
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

	public String getCurrentDate() {
		return currentDate;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public CodeTableResource getBuildingCurrentStatus() {
		return buildingCurrentStatus;
	}

	public double getTotalDueForCurrentStatus() {
		return totalDueForCurrentStatus;
	}

	public double getBalancePaymentForCurrentStatus() {
		return balancePaymentForCurrentStatus;
	}

	public OrganizationResource getOrganization() {
		return organization;
	}
	
	public String getDisplayProjectInfo() {
		return displayProjectInfo;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public String getProjectPhaseName() {
		return projectPhaseName;
	}

	public String getProjectBuildingName() {
		return projectBuildingName;
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

	public Map<String, String> getProjectBankAccounts() {
		return projectBankAccounts;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public Set<UnitPaymentScheduleResource> getScheduleList() {
		return scheduleList;
	}
}
