package com.propmgr.dao;

// Generated Jun 23, 2015 11:52:03 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Enquiry generated by hbm2java
 */
public class Enquiry implements java.io.Serializable {

	private Long enquiryid;
	private Customermaster customermaster;
	private Usermaster usermaster;
	private Unittype unittype;
	private Sourcemaster sourcemaster;
	private Date originalenquirydate;
	private Integer budget;
	private Integer probability;
	private Integer currenthomestatus;
	private Set enquirycomments = new HashSet(0);

	public Enquiry() {
	}

	public Enquiry(Customermaster customermaster, Usermaster usermaster,
			Unittype unittype, Sourcemaster sourcemaster) {
		this.customermaster = customermaster;
		this.usermaster = usermaster;
		this.unittype = unittype;
		this.sourcemaster = sourcemaster;
	}

	public Enquiry(Customermaster customermaster, Usermaster usermaster,
			Unittype unittype, Sourcemaster sourcemaster,
			Date originalenquirydate, Integer budget, Integer probability,
			Integer currenthomestatus, Set enquirycomments) {
		this.customermaster = customermaster;
		this.usermaster = usermaster;
		this.unittype = unittype;
		this.sourcemaster = sourcemaster;
		this.originalenquirydate = originalenquirydate;
		this.budget = budget;
		this.probability = probability;
		this.currenthomestatus = currenthomestatus;
		this.enquirycomments = enquirycomments;
	}

	public Long getEnquiryid() {
		return this.enquiryid;
	}

	public void setEnquiryid(Long enquiryid) {
		this.enquiryid = enquiryid;
	}

	public Customermaster getCustomermaster() {
		return this.customermaster;
	}

	public void setCustomermaster(Customermaster customermaster) {
		this.customermaster = customermaster;
	}

	public Usermaster getUsermaster() {
		return this.usermaster;
	}

	public void setUsermaster(Usermaster usermaster) {
		this.usermaster = usermaster;
	}

	public Unittype getUnittype() {
		return this.unittype;
	}

	public void setUnittype(Unittype unittype) {
		this.unittype = unittype;
	}

	public Sourcemaster getSourcemaster() {
		return this.sourcemaster;
	}

	public void setSourcemaster(Sourcemaster sourcemaster) {
		this.sourcemaster = sourcemaster;
	}

	public Date getOriginalenquirydate() {
		return this.originalenquirydate;
	}

	public void setOriginalenquirydate(Date originalenquirydate) {
		this.originalenquirydate = originalenquirydate;
	}

	public Integer getBudget() {
		return this.budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public Integer getProbability() {
		return this.probability;
	}

	public void setProbability(Integer probability) {
		this.probability = probability;
	}

	public Integer getCurrenthomestatus() {
		return this.currenthomestatus;
	}

	public void setCurrenthomestatus(Integer currenthomestatus) {
		this.currenthomestatus = currenthomestatus;
	}

	public Set getEnquirycomments() {
		return this.enquirycomments;
	}

	public void setEnquirycomments(Set enquirycomments) {
		this.enquirycomments = enquirycomments;
	}

}