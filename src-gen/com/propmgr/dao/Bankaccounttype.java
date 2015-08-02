package com.propmgr.dao;

// Generated Aug 2, 2015 5:17:33 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Bankaccounttype generated by hbm2java
 */
public class Bankaccounttype implements java.io.Serializable {

	private Long bankaccounttypeid;
	private String accounttypecode;
	private String accounttypename;
	private Set projectbankaccounts = new HashSet(0);

	public Bankaccounttype() {
	}

	public Bankaccounttype(String accounttypecode, String accounttypename) {
		this.accounttypecode = accounttypecode;
		this.accounttypename = accounttypename;
	}

	public Bankaccounttype(String accounttypecode, String accounttypename,
			Set projectbankaccounts) {
		this.accounttypecode = accounttypecode;
		this.accounttypename = accounttypename;
		this.projectbankaccounts = projectbankaccounts;
	}

	public Long getBankaccounttypeid() {
		return this.bankaccounttypeid;
	}

	public void setBankaccounttypeid(Long bankaccounttypeid) {
		this.bankaccounttypeid = bankaccounttypeid;
	}

	public String getAccounttypecode() {
		return this.accounttypecode;
	}

	public void setAccounttypecode(String accounttypecode) {
		this.accounttypecode = accounttypecode;
	}

	public String getAccounttypename() {
		return this.accounttypename;
	}

	public void setAccounttypename(String accounttypename) {
		this.accounttypename = accounttypename;
	}

	public Set getProjectbankaccounts() {
		return this.projectbankaccounts;
	}

	public void setProjectbankaccounts(Set projectbankaccounts) {
		this.projectbankaccounts = projectbankaccounts;
	}

}
