package com.propmgr.dao;

// Generated Jul 1, 2015 11:15:39 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

/**
 * Rolemaster generated by hbm2java
 */
public class Rolemaster implements java.io.Serializable {

	private Long rolemasterid;
	private String rolename;
	private Clob roledescription;
	private boolean isadmin;
	private Set actionroles = new HashSet(0);
	private Set userroles = new HashSet(0);

	public Rolemaster() {
	}

	public Rolemaster(String rolename, boolean isadmin) {
		this.rolename = rolename;
		this.isadmin = isadmin;
	}

	public Rolemaster(String rolename, Clob roledescription, boolean isadmin,
			Set actionroles, Set userroles) {
		this.rolename = rolename;
		this.roledescription = roledescription;
		this.isadmin = isadmin;
		this.actionroles = actionroles;
		this.userroles = userroles;
	}

	public Long getRolemasterid() {
		return this.rolemasterid;
	}

	public void setRolemasterid(Long rolemasterid) {
		this.rolemasterid = rolemasterid;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Clob getRoledescription() {
		return this.roledescription;
	}

	public void setRoledescription(Clob roledescription) {
		this.roledescription = roledescription;
	}

	public boolean isIsadmin() {
		return this.isadmin;
	}

	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}

	public Set getActionroles() {
		return this.actionroles;
	}

	public void setActionroles(Set actionroles) {
		this.actionroles = actionroles;
	}

	public Set getUserroles() {
		return this.userroles;
	}

	public void setUserroles(Set userroles) {
		this.userroles = userroles;
	}

}
