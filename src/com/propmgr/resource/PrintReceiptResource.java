package com.propmgr.resource;

public class PrintReceiptResource {
	private OrganizationResource organization;
	private ProjectResource project;
	private String buildingName;
	private CustomerResource customer;
	private UnitResource unit;
	private PaymentResource paymentInformation;
	private String amountInWords;
	
	public PrintReceiptResource(OrganizationResource organization,
			ProjectResource project, String buildingName, CustomerResource customer, 
			UnitResource unit, PaymentResource paymentInformation, String amountInWords) {
		super();
		this.organization = organization;
		this.project = project;
		this.buildingName = buildingName;
		this.customer = customer;
		this.unit = unit;
		this.paymentInformation = paymentInformation;
		this.amountInWords = amountInWords;
	}

	public OrganizationResource getOrganization() {
		return organization;
	}

	public ProjectResource getProject() {
		return project;
	}
	
	public String getBuildingName() {
		return buildingName;
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
