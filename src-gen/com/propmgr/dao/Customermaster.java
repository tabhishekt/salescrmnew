package com.propmgr.dao;

// Generated Jun 23, 2015 11:52:03 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Customermaster generated by hbm2java
 */
public class Customermaster implements java.io.Serializable {

	private Long customerid;
	private Address address;
	private Person personByCoownerdetail;
	private Person personByPersondetail;
	private Usermaster usermaster;
	private Set enquiries = new HashSet(0);
	private Set unitbookings = new HashSet(0);

	public Customermaster() {
	}

	public Customermaster(Address address, Person personByPersondetail,
			Usermaster usermaster) {
		this.address = address;
		this.personByPersondetail = personByPersondetail;
		this.usermaster = usermaster;
	}

	public Customermaster(Address address, Person personByCoownerdetail,
			Person personByPersondetail, Usermaster usermaster, Set enquiries,
			Set unitbookings) {
		this.address = address;
		this.personByCoownerdetail = personByCoownerdetail;
		this.personByPersondetail = personByPersondetail;
		this.usermaster = usermaster;
		this.enquiries = enquiries;
		this.unitbookings = unitbookings;
	}

	public Long getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Person getPersonByCoownerdetail() {
		return this.personByCoownerdetail;
	}

	public void setPersonByCoownerdetail(Person personByCoownerdetail) {
		this.personByCoownerdetail = personByCoownerdetail;
	}

	public Person getPersonByPersondetail() {
		return this.personByPersondetail;
	}

	public void setPersonByPersondetail(Person personByPersondetail) {
		this.personByPersondetail = personByPersondetail;
	}

	public Usermaster getUsermaster() {
		return this.usermaster;
	}

	public void setUsermaster(Usermaster usermaster) {
		this.usermaster = usermaster;
	}

	public Set getEnquiries() {
		return this.enquiries;
	}

	public void setEnquiries(Set enquiries) {
		this.enquiries = enquiries;
	}

	public Set getUnitbookings() {
		return this.unitbookings;
	}

	public void setUnitbookings(Set unitbookings) {
		this.unitbookings = unitbookings;
	}

}