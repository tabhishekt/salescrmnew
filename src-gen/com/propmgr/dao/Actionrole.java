package com.propmgr.dao;

// Generated Jun 23, 2015 11:52:03 PM by Hibernate Tools 3.4.0.CR1

/**
 * Actionrole generated by hbm2java
 */
public class Actionrole implements java.io.Serializable {

	private Long actionroleid;
	private Actionmaster actionmaster;
	private Rolemaster rolemaster;

	public Actionrole() {
	}

	public Actionrole(Actionmaster actionmaster, Rolemaster rolemaster) {
		this.actionmaster = actionmaster;
		this.rolemaster = rolemaster;
	}

	public Long getActionroleid() {
		return this.actionroleid;
	}

	public void setActionroleid(Long actionroleid) {
		this.actionroleid = actionroleid;
	}

	public Actionmaster getActionmaster() {
		return this.actionmaster;
	}

	public void setActionmaster(Actionmaster actionmaster) {
		this.actionmaster = actionmaster;
	}

	public Rolemaster getRolemaster() {
		return this.rolemaster;
	}

	public void setRolemaster(Rolemaster rolemaster) {
		this.rolemaster = rolemaster;
	}

}