package com.propmgr.dao;

// Generated Jul 18, 2015 11:02:19 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Contactinfo generated by hbm2java
 */
public class Contactinfo implements java.io.Serializable {

	private Long contactinfoid;
	private String phonenumber;
	private String alternatenumber;
	private String mobilenumber;
	private String emailid;
	private Set persons = new HashSet(0);
	private Set organizations = new HashSet(0);

	public Contactinfo() {
	}

	public Contactinfo(String phonenumber, String alternatenumber,
			String mobilenumber, String emailid, Set persons, Set organizations) {
		this.phonenumber = phonenumber;
		this.alternatenumber = alternatenumber;
		this.mobilenumber = mobilenumber;
		this.emailid = emailid;
		this.persons = persons;
		this.organizations = organizations;
	}

	public Long getContactinfoid() {
		return this.contactinfoid;
	}

	public void setContactinfoid(Long contactinfoid) {
		this.contactinfoid = contactinfoid;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAlternatenumber() {
		return this.alternatenumber;
	}

	public void setAlternatenumber(String alternatenumber) {
		this.alternatenumber = alternatenumber;
	}

	public String getMobilenumber() {
		return this.mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getEmailid() {
		return this.emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public Set getPersons() {
		return this.persons;
	}

	public void setPersons(Set persons) {
		this.persons = persons;
	}

	public Set getOrganizations() {
		return this.organizations;
	}

	public void setOrganizations(Set organizations) {
		this.organizations = organizations;
	}

}
