package com.propmgr.dao;

// Generated Aug 2, 2015 5:17:33 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.Date;

/**
 * Unitmodificationstatus generated by hbm2java
 */
public class Unitmodificationstatus implements java.io.Serializable {

	private Long unitmodificationstatusid;
	private Unitbooking unitbooking;
	private Unitmodificationstate unitmodificationstate;
	private Usermaster usermaster;
	private Date statusdate;
	private Clob statuscomment;

	public Unitmodificationstatus() {
	}

	public Unitmodificationstatus(Unitbooking unitbooking,
			Unitmodificationstate unitmodificationstate, Usermaster usermaster,
			Date statusdate) {
		this.unitbooking = unitbooking;
		this.unitmodificationstate = unitmodificationstate;
		this.usermaster = usermaster;
		this.statusdate = statusdate;
	}

	public Unitmodificationstatus(Unitbooking unitbooking,
			Unitmodificationstate unitmodificationstate, Usermaster usermaster,
			Date statusdate, Clob statuscomment) {
		this.unitbooking = unitbooking;
		this.unitmodificationstate = unitmodificationstate;
		this.usermaster = usermaster;
		this.statusdate = statusdate;
		this.statuscomment = statuscomment;
	}

	public Long getUnitmodificationstatusid() {
		return this.unitmodificationstatusid;
	}

	public void setUnitmodificationstatusid(Long unitmodificationstatusid) {
		this.unitmodificationstatusid = unitmodificationstatusid;
	}

	public Unitbooking getUnitbooking() {
		return this.unitbooking;
	}

	public void setUnitbooking(Unitbooking unitbooking) {
		this.unitbooking = unitbooking;
	}

	public Unitmodificationstate getUnitmodificationstate() {
		return this.unitmodificationstate;
	}

	public void setUnitmodificationstate(
			Unitmodificationstate unitmodificationstate) {
		this.unitmodificationstate = unitmodificationstate;
	}

	public Usermaster getUsermaster() {
		return this.usermaster;
	}

	public void setUsermaster(Usermaster usermaster) {
		this.usermaster = usermaster;
	}

	public Date getStatusdate() {
		return this.statusdate;
	}

	public void setStatusdate(Date statusdate) {
		this.statusdate = statusdate;
	}

	public Clob getStatuscomment() {
		return this.statuscomment;
	}

	public void setStatuscomment(Clob statuscomment) {
		this.statuscomment = statuscomment;
	}

}
