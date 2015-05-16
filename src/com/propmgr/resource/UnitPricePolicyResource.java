package com.propmgr.resource;


public class UnitPricePolicyResource {
	private long id;
	private String policyName;
	private double baserate;
	private double readyreckonerrate;
	private double stampduty;
	private double registrationcharge;
	private double servicetax;
	private double valueaddedtax;
	private double totaltax;
	private double maintenancecharge1;
	private double maintenancecharge2;
	private double legalcharge;
	private String assignedToProjects;
	
	public UnitPricePolicyResource(Long id, String policyName, double baserate, double readyreckonerrate, double stampduty, 
			double registrationcharge, double servicetax, double valueaddedtax, double totaltax, 
			double maintenancecharge1,double maintenancecharge2, double legalcharge, String assignedToProjects) {
		super();
		this.id = id;
		this.policyName = policyName;
		this.baserate = baserate;
		this.readyreckonerrate = readyreckonerrate;
		this.stampduty = stampduty;
		this.registrationcharge = registrationcharge;
		this.servicetax = servicetax;
		this.valueaddedtax = valueaddedtax;
		this.totaltax = totaltax;
		this.maintenancecharge1 = maintenancecharge1;
		this.maintenancecharge2 = maintenancecharge2;
		this.legalcharge = legalcharge;
		this.assignedToProjects = assignedToProjects;
	}

	public Long getId() {
		return id;
	}

	public String getPolicyName() {
		return policyName;
	}

	public double getBaserate() {
		return baserate;
	}
	
	public double getReadyreckonerrate() {
		return readyreckonerrate;
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

	public double getTotaltax() {
		return totaltax;
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

	public String getAssignedToProjects() {
		return assignedToProjects;
	}
}
