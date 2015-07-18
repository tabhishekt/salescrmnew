package com.propmgr.dao;

// Generated Jul 18, 2015 11:02:19 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Paymentstate generated by hbm2java
 */
public class Paymentstate implements java.io.Serializable {

	private Long paymentstateid;
	private String paymentstatename;
	private boolean allowstatechange;
	private Set paymentstatuses = new HashSet(0);

	public Paymentstate() {
	}

	public Paymentstate(String paymentstatename, boolean allowstatechange) {
		this.paymentstatename = paymentstatename;
		this.allowstatechange = allowstatechange;
	}

	public Paymentstate(String paymentstatename, boolean allowstatechange,
			Set paymentstatuses) {
		this.paymentstatename = paymentstatename;
		this.allowstatechange = allowstatechange;
		this.paymentstatuses = paymentstatuses;
	}

	public Long getPaymentstateid() {
		return this.paymentstateid;
	}

	public void setPaymentstateid(Long paymentstateid) {
		this.paymentstateid = paymentstateid;
	}

	public String getPaymentstatename() {
		return this.paymentstatename;
	}

	public void setPaymentstatename(String paymentstatename) {
		this.paymentstatename = paymentstatename;
	}

	public boolean isAllowstatechange() {
		return this.allowstatechange;
	}

	public void setAllowstatechange(boolean allowstatechange) {
		this.allowstatechange = allowstatechange;
	}

	public Set getPaymentstatuses() {
		return this.paymentstatuses;
	}

	public void setPaymentstatuses(Set paymentstatuses) {
		this.paymentstatuses = paymentstatuses;
	}

}
