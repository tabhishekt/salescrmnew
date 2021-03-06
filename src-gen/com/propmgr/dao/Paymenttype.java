package com.propmgr.dao;

// Generated Oct 19, 2015 11:52:27 AM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

/**
 * Paymenttype generated by hbm2java
 */
public class Paymenttype implements java.io.Serializable {

	private Long paymenttypeid;
	private String paymenttypecode;
	private Clob paymenttypedescription;
	private Set paymentmasters = new HashSet(0);

	public Paymenttype() {
	}

	public Paymenttype(Clob paymenttypedescription) {
		this.paymenttypedescription = paymenttypedescription;
	}

	public Paymenttype(String paymenttypecode, Clob paymenttypedescription,
			Set paymentmasters) {
		this.paymenttypecode = paymenttypecode;
		this.paymenttypedescription = paymenttypedescription;
		this.paymentmasters = paymentmasters;
	}

	public Long getPaymenttypeid() {
		return this.paymenttypeid;
	}

	public void setPaymenttypeid(Long paymenttypeid) {
		this.paymenttypeid = paymenttypeid;
	}

	public String getPaymenttypecode() {
		return this.paymenttypecode;
	}

	public void setPaymenttypecode(String paymenttypecode) {
		this.paymenttypecode = paymenttypecode;
	}

	public Clob getPaymenttypedescription() {
		return this.paymenttypedescription;
	}

	public void setPaymenttypedescription(Clob paymenttypedescription) {
		this.paymenttypedescription = paymenttypedescription;
	}

	public Set getPaymentmasters() {
		return this.paymentmasters;
	}

	public void setPaymentmasters(Set paymentmasters) {
		this.paymentmasters = paymentmasters;
	}

}
