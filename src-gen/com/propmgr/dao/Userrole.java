package com.propmgr.dao;

// Generated Oct 19, 2015 11:52:27 AM by Hibernate Tools 3.4.0.CR1

/**
 * Userrole generated by hbm2java
 */
public class Userrole implements java.io.Serializable {

	private Long userroleid;
	private Usermaster usermaster;
	private Rolemaster rolemaster;

	public Userrole() {
	}

	public Userrole(Usermaster usermaster, Rolemaster rolemaster) {
		this.usermaster = usermaster;
		this.rolemaster = rolemaster;
	}

	public Long getUserroleid() {
		return this.userroleid;
	}

	public void setUserroleid(Long userroleid) {
		this.userroleid = userroleid;
	}

	public Usermaster getUsermaster() {
		return this.usermaster;
	}

	public void setUsermaster(Usermaster usermaster) {
		this.usermaster = usermaster;
	}

	public Rolemaster getRolemaster() {
		return this.rolemaster;
	}

	public void setRolemaster(Rolemaster rolemaster) {
		this.rolemaster = rolemaster;
	}

}
