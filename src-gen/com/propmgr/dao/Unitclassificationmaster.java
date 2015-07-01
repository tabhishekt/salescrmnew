package com.propmgr.dao;

// Generated Jul 1, 2015 11:15:39 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Unitclassificationmaster generated by hbm2java
 */
public class Unitclassificationmaster implements java.io.Serializable {

	private Long unitclassid;
	private String unitclasscode;
	private String unitclassdesc;
	private Set unitmasters = new HashSet(0);

	public Unitclassificationmaster() {
	}

	public Unitclassificationmaster(String unitclasscode) {
		this.unitclasscode = unitclasscode;
	}

	public Unitclassificationmaster(String unitclasscode, String unitclassdesc,
			Set unitmasters) {
		this.unitclasscode = unitclasscode;
		this.unitclassdesc = unitclassdesc;
		this.unitmasters = unitmasters;
	}

	public Long getUnitclassid() {
		return this.unitclassid;
	}

	public void setUnitclassid(Long unitclassid) {
		this.unitclassid = unitclassid;
	}

	public String getUnitclasscode() {
		return this.unitclasscode;
	}

	public void setUnitclasscode(String unitclasscode) {
		this.unitclasscode = unitclasscode;
	}

	public String getUnitclassdesc() {
		return this.unitclassdesc;
	}

	public void setUnitclassdesc(String unitclassdesc) {
		this.unitclassdesc = unitclassdesc;
	}

	public Set getUnitmasters() {
		return this.unitmasters;
	}

	public void setUnitmasters(Set unitmasters) {
		this.unitmasters = unitmasters;
	}

}
