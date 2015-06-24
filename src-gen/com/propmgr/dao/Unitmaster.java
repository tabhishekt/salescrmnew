package com.propmgr.dao;

// Generated Jun 23, 2015 11:52:03 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Unitmaster generated by hbm2java
 */
public class Unitmaster implements java.io.Serializable {

	private Long unitid;
	private Projectbuilding projectbuilding;
	private Unitpricepolicy unitpricepolicy;
	private Unitclassificationmaster unitclassificationmaster;
	private Unittype unittype;
	private String unitnumber;
	private int floornumber;
	private int floortype;
	private long carpetarea;
	private long saleablearea;
	private long carpetareaforterrace;
	private Double floorrise;
	private boolean otheroptions;
	private Double bookingamount;
	private Double othercharges;
	private Boolean registrationdone;
	private Double agreementvalue;
	private Double agreementvalurereadyreckoner;
	private Double totaltax;
	private Double totalcostwithtax;
	private Double totalcostwithtaxreadyreckoner;
	private Double totalcost;
	private Double totalcostreadyreckoner;
	private Set unitbookings = new HashSet(0);
	private Set unitamenities = new HashSet(0);

	public Unitmaster() {
	}

	public Unitmaster(Projectbuilding projectbuilding,
			Unitclassificationmaster unitclassificationmaster,
			Unittype unittype, String unitnumber, int floornumber,
			int floortype, long carpetarea, long saleablearea,
			long carpetareaforterrace, boolean otheroptions) {
		this.projectbuilding = projectbuilding;
		this.unitclassificationmaster = unitclassificationmaster;
		this.unittype = unittype;
		this.unitnumber = unitnumber;
		this.floornumber = floornumber;
		this.floortype = floortype;
		this.carpetarea = carpetarea;
		this.saleablearea = saleablearea;
		this.carpetareaforterrace = carpetareaforterrace;
		this.otheroptions = otheroptions;
	}

	public Unitmaster(Projectbuilding projectbuilding,
			Unitpricepolicy unitpricepolicy,
			Unitclassificationmaster unitclassificationmaster,
			Unittype unittype, String unitnumber, int floornumber,
			int floortype, long carpetarea, long saleablearea,
			long carpetareaforterrace, Double floorrise, boolean otheroptions,
			Double bookingamount, Double othercharges,
			Boolean registrationdone, Double agreementvalue,
			Double agreementvalurereadyreckoner, Double totaltax,
			Double totalcostwithtax, Double totalcostwithtaxreadyreckoner,
			Double totalcost, Double totalcostreadyreckoner, Set unitbookings,
			Set unitamenities) {
		this.projectbuilding = projectbuilding;
		this.unitpricepolicy = unitpricepolicy;
		this.unitclassificationmaster = unitclassificationmaster;
		this.unittype = unittype;
		this.unitnumber = unitnumber;
		this.floornumber = floornumber;
		this.floortype = floortype;
		this.carpetarea = carpetarea;
		this.saleablearea = saleablearea;
		this.carpetareaforterrace = carpetareaforterrace;
		this.floorrise = floorrise;
		this.otheroptions = otheroptions;
		this.bookingamount = bookingamount;
		this.othercharges = othercharges;
		this.registrationdone = registrationdone;
		this.agreementvalue = agreementvalue;
		this.agreementvalurereadyreckoner = agreementvalurereadyreckoner;
		this.totaltax = totaltax;
		this.totalcostwithtax = totalcostwithtax;
		this.totalcostwithtaxreadyreckoner = totalcostwithtaxreadyreckoner;
		this.totalcost = totalcost;
		this.totalcostreadyreckoner = totalcostreadyreckoner;
		this.unitbookings = unitbookings;
		this.unitamenities = unitamenities;
	}

	public Long getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Long unitid) {
		this.unitid = unitid;
	}

	public Projectbuilding getProjectbuilding() {
		return this.projectbuilding;
	}

	public void setProjectbuilding(Projectbuilding projectbuilding) {
		this.projectbuilding = projectbuilding;
	}

	public Unitpricepolicy getUnitpricepolicy() {
		return this.unitpricepolicy;
	}

	public void setUnitpricepolicy(Unitpricepolicy unitpricepolicy) {
		this.unitpricepolicy = unitpricepolicy;
	}

	public Unitclassificationmaster getUnitclassificationmaster() {
		return this.unitclassificationmaster;
	}

	public void setUnitclassificationmaster(
			Unitclassificationmaster unitclassificationmaster) {
		this.unitclassificationmaster = unitclassificationmaster;
	}

	public Unittype getUnittype() {
		return this.unittype;
	}

	public void setUnittype(Unittype unittype) {
		this.unittype = unittype;
	}

	public String getUnitnumber() {
		return this.unitnumber;
	}

	public void setUnitnumber(String unitnumber) {
		this.unitnumber = unitnumber;
	}

	public int getFloornumber() {
		return this.floornumber;
	}

	public void setFloornumber(int floornumber) {
		this.floornumber = floornumber;
	}

	public int getFloortype() {
		return this.floortype;
	}

	public void setFloortype(int floortype) {
		this.floortype = floortype;
	}

	public long getCarpetarea() {
		return this.carpetarea;
	}

	public void setCarpetarea(long carpetarea) {
		this.carpetarea = carpetarea;
	}

	public long getSaleablearea() {
		return this.saleablearea;
	}

	public void setSaleablearea(long saleablearea) {
		this.saleablearea = saleablearea;
	}

	public long getCarpetareaforterrace() {
		return this.carpetareaforterrace;
	}

	public void setCarpetareaforterrace(long carpetareaforterrace) {
		this.carpetareaforterrace = carpetareaforterrace;
	}

	public Double getFloorrise() {
		return this.floorrise;
	}

	public void setFloorrise(Double floorrise) {
		this.floorrise = floorrise;
	}

	public boolean isOtheroptions() {
		return this.otheroptions;
	}

	public void setOtheroptions(boolean otheroptions) {
		this.otheroptions = otheroptions;
	}

	public Double getBookingamount() {
		return this.bookingamount;
	}

	public void setBookingamount(Double bookingamount) {
		this.bookingamount = bookingamount;
	}

	public Double getOthercharges() {
		return this.othercharges;
	}

	public void setOthercharges(Double othercharges) {
		this.othercharges = othercharges;
	}

	public Boolean getRegistrationdone() {
		return this.registrationdone;
	}

	public void setRegistrationdone(Boolean registrationdone) {
		this.registrationdone = registrationdone;
	}

	public Double getAgreementvalue() {
		return this.agreementvalue;
	}

	public void setAgreementvalue(Double agreementvalue) {
		this.agreementvalue = agreementvalue;
	}

	public Double getAgreementvalurereadyreckoner() {
		return this.agreementvalurereadyreckoner;
	}

	public void setAgreementvalurereadyreckoner(
			Double agreementvalurereadyreckoner) {
		this.agreementvalurereadyreckoner = agreementvalurereadyreckoner;
	}

	public Double getTotaltax() {
		return this.totaltax;
	}

	public void setTotaltax(Double totaltax) {
		this.totaltax = totaltax;
	}

	public Double getTotalcostwithtax() {
		return this.totalcostwithtax;
	}

	public void setTotalcostwithtax(Double totalcostwithtax) {
		this.totalcostwithtax = totalcostwithtax;
	}

	public Double getTotalcostwithtaxreadyreckoner() {
		return this.totalcostwithtaxreadyreckoner;
	}

	public void setTotalcostwithtaxreadyreckoner(
			Double totalcostwithtaxreadyreckoner) {
		this.totalcostwithtaxreadyreckoner = totalcostwithtaxreadyreckoner;
	}

	public Double getTotalcost() {
		return this.totalcost;
	}

	public void setTotalcost(Double totalcost) {
		this.totalcost = totalcost;
	}

	public Double getTotalcostreadyreckoner() {
		return this.totalcostreadyreckoner;
	}

	public void setTotalcostreadyreckoner(Double totalcostreadyreckoner) {
		this.totalcostreadyreckoner = totalcostreadyreckoner;
	}

	public Set getUnitbookings() {
		return this.unitbookings;
	}

	public void setUnitbookings(Set unitbookings) {
		this.unitbookings = unitbookings;
	}

	public Set getUnitamenities() {
		return this.unitamenities;
	}

	public void setUnitamenities(Set unitamenities) {
		this.unitamenities = unitamenities;
	}

}