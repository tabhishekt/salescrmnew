package com.propmgr.resource;

public class ProjectResource {
	private long id;
	private OrganizationResource organization;
	private String orgName;
	private String name;
	private String logoFileName;
	private String description;
	private AddressResource address;
	private long totalPhases;
	private String displayAddress;
	private String termsAndConditions;
	
	public ProjectResource(long id, OrganizationResource organization,
			String orgName, String name, String logoFileName, String description,
			AddressResource address, long totalPhases, String displayAddress, 
			String termsAndConditions) {
		super();
		this.id = id;
		this.organization = organization;
		this.orgName = orgName;
		this.name = name;
		this.logoFileName = logoFileName;
		this.description = description;
		this.address = address;
		this.totalPhases = totalPhases;
		this.displayAddress = displayAddress;
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
	
	public String getLogoFileName() {
		return logoFileName;
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

	public String getTermsAndConditions() {
		return termsAndConditions;
	}
}
