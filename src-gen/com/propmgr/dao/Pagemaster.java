package com.propmgr.dao;

// Generated Jul 18, 2015 11:02:19 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

/**
 * Pagemaster generated by hbm2java
 */
public class Pagemaster implements java.io.Serializable {

	private Long pagemasterid;
	private String pagename;
	private Clob pagedescription;
	private Set actionmasters = new HashSet(0);

	public Pagemaster() {
	}

	public Pagemaster(String pagename) {
		this.pagename = pagename;
	}

	public Pagemaster(String pagename, Clob pagedescription, Set actionmasters) {
		this.pagename = pagename;
		this.pagedescription = pagedescription;
		this.actionmasters = actionmasters;
	}

	public Long getPagemasterid() {
		return this.pagemasterid;
	}

	public void setPagemasterid(Long pagemasterid) {
		this.pagemasterid = pagemasterid;
	}

	public String getPagename() {
		return this.pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	public Clob getPagedescription() {
		return this.pagedescription;
	}

	public void setPagedescription(Clob pagedescription) {
		this.pagedescription = pagedescription;
	}

	public Set getActionmasters() {
		return this.actionmasters;
	}

	public void setActionmasters(Set actionmasters) {
		this.actionmasters = actionmasters;
	}

}
