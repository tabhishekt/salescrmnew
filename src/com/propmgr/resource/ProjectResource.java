package com.propmgr.resource;

public class ProjectResource {
	private long id;
	private OrganizationResource organization;
	private String orgName;
	private String name;
	private String description;
	private AddressResource address;
	private long totalPhases;
	private String displayAddress;
	private String bankName;
	private String accountNumber;
	private String accountHolderName;
	private String ifscCode;
	private String micrCode;
	private AddressResource bankAddress;
	private String displayBankAddress;
	private String termsAndConditions;
	
	public ProjectResource(long id, OrganizationResource organization,
			String orgName, String name, String description,
			AddressResource address, long totalPhases, String displayAddress,
			String bankName, String accountNumber, String accountHolderName,
			String ifscCode, String micrCode, AddressResource bankAddress,
			String displayBankAddress, String termsAndConditions) {
		super();
		this.id = id;
		this.organization = organization;
		this.orgName = orgName;
		this.name = name;
		this.description = description;
		this.address = address;
		this.totalPhases = totalPhases;
		this.displayAddress = displayAddress;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.ifscCode = ifscCode;
		this.micrCode = micrCode;
		this.bankAddress = bankAddress;
		this.displayBankAddress = displayBankAddress;
		this.termsAndConditions = termsAndConditions;
	}

	public long getId() {
		return id;
	}

	public OrganizationResource getOrganization() {
		return organization;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public AddressResource getAddress() {
		return address;
	}

	public long getTotalPhases() {
		return totalPhases;
	}

	public String getDisplayAddress() {
		return displayAddress;
	}

	public String getBankName() {
		return bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public String getMicrCode() {
		return micrCode;
	}

	public AddressResource getBankAddress() {
		return bankAddress;
	}

	public String getDisplayBankAddress() {
		return displayBankAddress;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}
}
