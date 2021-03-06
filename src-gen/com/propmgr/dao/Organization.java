package com.propmgr.dao;

// Generated Oct 19, 2015 11:52:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Organization generated by hbm2java
 */
public class Organization implements java.io.Serializable {

	private Long orgid;
	private Contactinfo contactinfo;
	private Person person;
	private Address address;
	private String orgname;
	private String logofilename;
	private Set projectmasters = new HashSet(0);

	public Organization() {
	}

	public Organization(Contactinfo contactinfo, Person person,
			Address address, String orgname) {
		this.contactinfo = contactinfo;
		this.person = person;
		this.address = address;
		this.orgname = orgname;
	}

	public Organization(Contactinfo contactinfo, Person person,
			Address address, String orgname, String logofilename,
			Set projectmasters) {
		this.contactinfo = contactinfo;
		this.person = person;
		this.address = address;
		this.orgname = orgname;
		this.logofilename = logofilename;
		this.projectmasters = projectmasters;
	}

	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Contactinfo getContactinfo() {
		return this.contactinfo;
	}

	public void setContactinfo(Contactinfo contactinfo) {
		this.contactinfo = contactinfo;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getLogofilename() {
		return this.logofilename;
	}

	public void setLogofilename(String logofilename) {
		this.logofilename = logofilename;
	}

	public Set getProjectmasters() {
		return this.projectmasters;
	}

	public void setProjectmasters(Set projectmasters) {
		this.projectmasters = projectmasters;
	}

}
