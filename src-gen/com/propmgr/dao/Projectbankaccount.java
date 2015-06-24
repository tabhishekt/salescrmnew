package com.propmgr.dao;

// Generated Jun 23, 2015 11:52:03 PM by Hibernate Tools 3.4.0.CR1

/**
 * Projectbankaccount generated by hbm2java
 */
public class Projectbankaccount implements java.io.Serializable {

	private Long projectbankaccountid;
	private Address address;
	private Bankaccounttype bankaccounttype;
	private Projectmaster projectmaster;
	private String bankname;
	private String accountnumber;
	private String accountholdername;
	private String ifsccode;
	private String micrcode;

	public Projectbankaccount() {
	}

	public Projectbankaccount(Address address, Bankaccounttype bankaccounttype,
			Projectmaster projectmaster) {
		this.address = address;
		this.bankaccounttype = bankaccounttype;
		this.projectmaster = projectmaster;
	}

	public Projectbankaccount(Address address, Bankaccounttype bankaccounttype,
			Projectmaster projectmaster, String bankname, String accountnumber,
			String accountholdername, String ifsccode, String micrcode) {
		this.address = address;
		this.bankaccounttype = bankaccounttype;
		this.projectmaster = projectmaster;
		this.bankname = bankname;
		this.accountnumber = accountnumber;
		this.accountholdername = accountholdername;
		this.ifsccode = ifsccode;
		this.micrcode = micrcode;
	}

	public Long getProjectbankaccountid() {
		return this.projectbankaccountid;
	}

	public void setProjectbankaccountid(Long projectbankaccountid) {
		this.projectbankaccountid = projectbankaccountid;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Bankaccounttype getBankaccounttype() {
		return this.bankaccounttype;
	}

	public void setBankaccounttype(Bankaccounttype bankaccounttype) {
		this.bankaccounttype = bankaccounttype;
	}

	public Projectmaster getProjectmaster() {
		return this.projectmaster;
	}

	public void setProjectmaster(Projectmaster projectmaster) {
		this.projectmaster = projectmaster;
	}

	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAccountnumber() {
		return this.accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getAccountholdername() {
		return this.accountholdername;
	}

	public void setAccountholdername(String accountholdername) {
		this.accountholdername = accountholdername;
	}

	public String getIfsccode() {
		return this.ifsccode;
	}

	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}

	public String getMicrcode() {
		return this.micrcode;
	}

	public void setMicrcode(String micrcode) {
		this.micrcode = micrcode;
	}

}