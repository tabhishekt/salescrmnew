package com.propmgr.dao;

// Generated Oct 19, 2015 11:52:27 AM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.Date;

/**
 * Enquirycomment generated by hbm2java
 */
public class Enquirycomment implements java.io.Serializable {

	private Long enquirycommentid;
	private Usermaster usermaster;
	private Enquiry enquiry;
	private Clob visitcomment;
	private Date visitdate;
	private Date followupdate;

	public Enquirycomment() {
	}

	public Enquirycomment(Usermaster usermaster, Enquiry enquiry) {
		this.usermaster = usermaster;
		this.enquiry = enquiry;
	}

	public Enquirycomment(Usermaster usermaster, Enquiry enquiry,
			Clob visitcomment, Date visitdate, Date followupdate) {
		this.usermaster = usermaster;
		this.enquiry = enquiry;
		this.visitcomment = visitcomment;
		this.visitdate = visitdate;
		this.followupdate = followupdate;
	}

	public Long getEnquirycommentid() {
		return this.enquirycommentid;
	}

	public void setEnquirycommentid(Long enquirycommentid) {
		this.enquirycommentid = enquirycommentid;
	}

	public Usermaster getUsermaster() {
		return this.usermaster;
	}

	public void setUsermaster(Usermaster usermaster) {
		this.usermaster = usermaster;
	}

	public Enquiry getEnquiry() {
		return this.enquiry;
	}

	public void setEnquiry(Enquiry enquiry) {
		this.enquiry = enquiry;
	}

	public Clob getVisitcomment() {
		return this.visitcomment;
	}

	public void setVisitcomment(Clob visitcomment) {
		this.visitcomment = visitcomment;
	}

	public Date getVisitdate() {
		return this.visitdate;
	}

	public void setVisitdate(Date visitdate) {
		this.visitdate = visitdate;
	}

	public Date getFollowupdate() {
		return this.followupdate;
	}

	public void setFollowupdate(Date followupdate) {
		this.followupdate = followupdate;
	}

}
