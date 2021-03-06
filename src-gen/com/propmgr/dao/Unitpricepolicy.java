package com.propmgr.dao;

// Generated Oct 19, 2015 11:52:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Unitpricepolicy generated by hbm2java
 */
public class Unitpricepolicy implements java.io.Serializable {

	private Long unitpricepolicyid;
	private String policyname;
	private double baserate;
	private double readyreckonerrate;
	private double stampduty;
	private double registrationcharge;
	private double servicetax;
	private double valueaddedtax;
	private double maintenancecharge1;
	private double maintenancecharge2;
	private double legalcharge;
	private Integer maintenancecharge1duration;
	private Double landrate;
	private Double interestrate;
	private Integer graceperiod;
	private Set unitmasters = new HashSet(0);
	private Set amenitypricepolicies = new HashSet(0);

	public Unitpricepolicy() {
	}

	public Unitpricepolicy(String policyname, double baserate,
			double readyreckonerrate, double stampduty,
			double registrationcharge, double servicetax, double valueaddedtax,
			double maintenancecharge1, double maintenancecharge2,
			double legalcharge) {
		this.policyname = policyname;
		this.baserate = baserate;
		this.readyreckonerrate = readyreckonerrate;
		this.stampduty = stampduty;
		this.registrationcharge = registrationcharge;
		this.servicetax = servicetax;
		this.valueaddedtax = valueaddedtax;
		this.maintenancecharge1 = maintenancecharge1;
		this.maintenancecharge2 = maintenancecharge2;
		this.legalcharge = legalcharge;
	}

	public Unitpricepolicy(String policyname, double baserate,
			double readyreckonerrate, double stampduty,
			double registrationcharge, double servicetax, double valueaddedtax,
			double maintenancecharge1, double maintenancecharge2,
			double legalcharge, Integer maintenancecharge1duration,
			Double landrate, Double interestrate, Integer graceperiod,
			Set unitmasters, Set amenitypricepolicies) {
		this.policyname = policyname;
		this.baserate = baserate;
		this.readyreckonerrate = readyreckonerrate;
		this.stampduty = stampduty;
		this.registrationcharge = registrationcharge;
		this.servicetax = servicetax;
		this.valueaddedtax = valueaddedtax;
		this.maintenancecharge1 = maintenancecharge1;
		this.maintenancecharge2 = maintenancecharge2;
		this.legalcharge = legalcharge;
		this.maintenancecharge1duration = maintenancecharge1duration;
		this.landrate = landrate;
		this.interestrate = interestrate;
		this.graceperiod = graceperiod;
		this.unitmasters = unitmasters;
		this.amenitypricepolicies = amenitypricepolicies;
	}

	public Long getUnitpricepolicyid() {
		return this.unitpricepolicyid;
	}

	public void setUnitpricepolicyid(Long unitpricepolicyid) {
		this.unitpricepolicyid = unitpricepolicyid;
	}

	public String getPolicyname() {
		return this.policyname;
	}

	public void setPolicyname(String policyname) {
		this.policyname = policyname;
	}

	public double getBaserate() {
		return this.baserate;
	}

	public void setBaserate(double baserate) {
		this.baserate = baserate;
	}

	public double getReadyreckonerrate() {
		return this.readyreckonerrate;
	}

	public void setReadyreckonerrate(double readyreckonerrate) {
		this.readyreckonerrate = readyreckonerrate;
	}

	public double getStampduty() {
		return this.stampduty;
	}

	public void setStampduty(double stampduty) {
		this.stampduty = stampduty;
	}

	public double getRegistrationcharge() {
		return this.registrationcharge;
	}

	public void setRegistrationcharge(double registrationcharge) {
		this.registrationcharge = registrationcharge;
	}

	public double getServicetax() {
		return this.servicetax;
	}

	public void setServicetax(double servicetax) {
		this.servicetax = servicetax;
	}

	public double getValueaddedtax() {
		return this.valueaddedtax;
	}

	public void setValueaddedtax(double valueaddedtax) {
		this.valueaddedtax = valueaddedtax;
	}

	public double getMaintenancecharge1() {
		return this.maintenancecharge1;
	}

	public void setMaintenancecharge1(double maintenancecharge1) {
		this.maintenancecharge1 = maintenancecharge1;
	}

	public double getMaintenancecharge2() {
		return this.maintenancecharge2;
	}

	public void setMaintenancecharge2(double maintenancecharge2) {
		this.maintenancecharge2 = maintenancecharge2;
	}

	public double getLegalcharge() {
		return this.legalcharge;
	}

	public void setLegalcharge(double legalcharge) {
		this.legalcharge = legalcharge;
	}

	public Integer getMaintenancecharge1duration() {
		return this.maintenancecharge1duration;
	}

	public void setMaintenancecharge1duration(Integer maintenancecharge1duration) {
		this.maintenancecharge1duration = maintenancecharge1duration;
	}

	public Double getLandrate() {
		return this.landrate;
	}

	public void setLandrate(Double landrate) {
		this.landrate = landrate;
	}

	public Double getInterestrate() {
		return this.interestrate;
	}

	public void setInterestrate(Double interestrate) {
		this.interestrate = interestrate;
	}

	public Integer getGraceperiod() {
		return this.graceperiod;
	}

	public void setGraceperiod(Integer graceperiod) {
		this.graceperiod = graceperiod;
	}

	public Set getUnitmasters() {
		return this.unitmasters;
	}

	public void setUnitmasters(Set unitmasters) {
		this.unitmasters = unitmasters;
	}

	public Set getAmenitypricepolicies() {
		return this.amenitypricepolicies;
	}

	public void setAmenitypricepolicies(Set amenitypricepolicies) {
		this.amenitypricepolicies = amenitypricepolicies;
	}

}
