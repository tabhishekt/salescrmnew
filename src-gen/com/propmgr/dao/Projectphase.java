package com.propmgr.dao;

// Generated Jul 30, 2015 6:25:17 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

/**
 * Projectphase generated by hbm2java
 */
public class Projectphase implements java.io.Serializable {

	private Long projectphaseid;
	private Projectmaster projectmaster;
	private String projectphasename;
	private Clob projectphasedescription;
	private Set projectbuildings = new HashSet(0);

	public Projectphase() {
	}

	public Projectphase(Projectmaster projectmaster, String projectphasename) {
		this.projectmaster = projectmaster;
		this.projectphasename = projectphasename;
	}

	public Projectphase(Projectmaster projectmaster, String projectphasename,
			Clob projectphasedescription, Set projectbuildings) {
		this.projectmaster = projectmaster;
		this.projectphasename = projectphasename;
		this.projectphasedescription = projectphasedescription;
		this.projectbuildings = projectbuildings;
	}

	public Long getProjectphaseid() {
		return this.projectphaseid;
	}

	public void setProjectphaseid(Long projectphaseid) {
		this.projectphaseid = projectphaseid;
	}

	public Projectmaster getProjectmaster() {
		return this.projectmaster;
	}

	public void setProjectmaster(Projectmaster projectmaster) {
		this.projectmaster = projectmaster;
	}

	public String getProjectphasename() {
		return this.projectphasename;
	}

	public void setProjectphasename(String projectphasename) {
		this.projectphasename = projectphasename;
	}

	public Clob getProjectphasedescription() {
		return this.projectphasedescription;
	}

	public void setProjectphasedescription(Clob projectphasedescription) {
		this.projectphasedescription = projectphasedescription;
	}

	public Set getProjectbuildings() {
		return this.projectbuildings;
	}

	public void setProjectbuildings(Set projectbuildings) {
		this.projectbuildings = projectbuildings;
	}

}
