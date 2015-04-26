package com.propmgr.resource;

public class PrintReceiptResource {
	private OrganizationResource organization;
	private String projectName;
	private String buildingName;
	private String projectAddress;
	private CustomerResource customer;
	private UnitResource unit;
	private PaymentResource paymentInformation;
	private String amountInWords;
	
	public PrintReceiptResource(OrganizationResource organization,
			String projectName, String buildingName, String projectAddress, 
			CustomerResource customer, UnitResource unit, PaymentResource paymentInformation,
			String amountInWords) {
		super();
		this.organization = organization;
		this.projectName = projectName;
		this.buildingName = buildingName;
		this.projectAddress = projectAddress;
		this.customer = customer;
		this.unit = unit;
		this.paymentInformation = paymentInformation;
		this.amountInWords = amountInWords;
	}

	public OrganizationResource getOrganization() {
		return organization;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public CustomerResource getCustomer() {
		return customer;
	}

	public UnitResource getUnit() {
		return unit;
	}

	public PaymentResource getPaymentInformation() {
		return paymentInformation;
	}
	
	public String getAmountInWords() {
		return amountInWords;
	}
}
