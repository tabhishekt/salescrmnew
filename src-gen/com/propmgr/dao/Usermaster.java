package com.propmgr.dao;

// Generated Jul 30, 2015 6:25:17 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Usermaster generated by hbm2java
 */
public class Usermaster implements java.io.Serializable {

	private Long usermasterid;
	private Person person;
	private Address address;
	private String loginname;
	private String password;
	private boolean isactive;
	private Set enquirycomments = new HashSet(0);
	private Set customermasters = new HashSet(0);
	private Set unitbookingsForCancelledby = new HashSet(0);
	private Set enquiries = new HashSet(0);
	private Set unitbookingsForBookedby = new HashSet(0);
	private Set paymentstatuses = new HashSet(0);
	private Set unitmodificationstatuses = new HashSet(0);
	private Set paymentmasters = new HashSet(0);
	private Set userroles = new HashSet(0);

	public Usermaster() {
	}

	public Usermaster(Person person, Address address, String loginname,
			boolean isactive) {
		this.person = person;
		this.address = address;
		this.loginname = loginname;
		this.isactive = isactive;
	}

	public Usermaster(Person person, Address address, String loginname,
			String password, boolean isactive, Set enquirycomments,
			Set customermasters, Set unitbookingsForCancelledby, Set enquiries,
			Set unitbookingsForBookedby, Set paymentstatuses,
			Set unitmodificationstatuses, Set paymentmasters, Set userroles) {
		this.person = person;
		this.address = address;
		this.loginname = loginname;
		this.password = password;
		this.isactive = isactive;
		this.enquirycomments = enquirycomments;
		this.customermasters = customermasters;
		this.unitbookingsForCancelledby = unitbookingsForCancelledby;
		this.enquiries = enquiries;
		this.unitbookingsForBookedby = unitbookingsForBookedby;
		this.paymentstatuses = paymentstatuses;
		this.unitmodificationstatuses = unitmodificationstatuses;
		this.paymentmasters = paymentmasters;
		this.userroles = userroles;
	}

	public Long getUsermasterid() {
		return this.usermasterid;
	}

	public void setUsermasterid(Long usermasterid) {
		this.usermasterid = usermasterid;
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

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public Set getEnquirycomments() {
		return this.enquirycomments;
	}

	public void setEnquirycomments(Set enquirycomments) {
		this.enquirycomments = enquirycomments;
	}

	public Set getCustomermasters() {
		return this.customermasters;
	}

	public void setCustomermasters(Set customermasters) {
		this.customermasters = customermasters;
	}

	public Set getUnitbookingsForCancelledby() {
		return this.unitbookingsForCancelledby;
	}

	public void setUnitbookingsForCancelledby(Set unitbookingsForCancelledby) {
		this.unitbookingsForCancelledby = unitbookingsForCancelledby;
	}

	public Set getEnquiries() {
		return this.enquiries;
	}

	public void setEnquiries(Set enquiries) {
		this.enquiries = enquiries;
	}

	public Set getUnitbookingsForBookedby() {
		return this.unitbookingsForBookedby;
	}

	public void setUnitbookingsForBookedby(Set unitbookingsForBookedby) {
		this.unitbookingsForBookedby = unitbookingsForBookedby;
	}

	public Set getPaymentstatuses() {
		return this.paymentstatuses;
	}

	public void setPaymentstatuses(Set paymentstatuses) {
		this.paymentstatuses = paymentstatuses;
	}

	public Set getUnitmodificationstatuses() {
		return this.unitmodificationstatuses;
	}

	public void setUnitmodificationstatuses(Set unitmodificationstatuses) {
		this.unitmodificationstatuses = unitmodificationstatuses;
	}

	public Set getPaymentmasters() {
		return this.paymentmasters;
	}

	public void setPaymentmasters(Set paymentmasters) {
		this.paymentmasters = paymentmasters;
	}

	public Set getUserroles() {
		return this.userroles;
	}

	public void setUserroles(Set userroles) {
		this.userroles = userroles;
	}

}
