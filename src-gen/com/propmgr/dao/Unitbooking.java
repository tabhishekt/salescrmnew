package com.propmgr.dao;

// Generated Oct 19, 2015 11:52:27 AM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Unitbooking generated by hbm2java
 */
public class Unitbooking implements java.io.Serializable {

	private Long unitbookingid;
	private Usermaster usermasterByBookedby;
	private Usermaster usermasterByCancelledby;
	private Parkingmaster parkingmaster;
	private Customermaster customermaster;
	private Unitmaster unitmaster;
	private Date bookingdate;
	private long bookingformnumber;
	private Double bookingdiscount;
	private Double deductiononothercharges;
	private Clob bookingcomment;
	private Boolean iscancelled;
	private Double canceldeduction;
	private Date cancellationdate;
	private Clob cancellationcomment;
	private Clob unitmodificationdetails;
	private Integer parkingtype;
	private Date demandlettergenerationdate;
	private Set refundmasters = new HashSet(0);
	private Set unitmodificationstatuses = new HashSet(0);
	private Set paymentmasters = new HashSet(0);

	public Unitbooking() {
	}

	public Unitbooking(Usermaster usermasterByBookedby,
			Customermaster customermaster, Unitmaster unitmaster,
			Date bookingdate, long bookingformnumber) {
		this.usermasterByBookedby = usermasterByBookedby;
		this.customermaster = customermaster;
		this.unitmaster = unitmaster;
		this.bookingdate = bookingdate;
		this.bookingformnumber = bookingformnumber;
	}

	public Unitbooking(Usermaster usermasterByBookedby,
			Usermaster usermasterByCancelledby, Parkingmaster parkingmaster,
			Customermaster customermaster, Unitmaster unitmaster,
			Date bookingdate, long bookingformnumber, Double bookingdiscount,
			Double deductiononothercharges, Clob bookingcomment,
			Boolean iscancelled, Double canceldeduction, Date cancellationdate,
			Clob cancellationcomment, Clob unitmodificationdetails,
			Integer parkingtype, Date demandlettergenerationdate,
			Set refundmasters, Set unitmodificationstatuses, Set paymentmasters) {
		this.usermasterByBookedby = usermasterByBookedby;
		this.usermasterByCancelledby = usermasterByCancelledby;
		this.parkingmaster = parkingmaster;
		this.customermaster = customermaster;
		this.unitmaster = unitmaster;
		this.bookingdate = bookingdate;
		this.bookingformnumber = bookingformnumber;
		this.bookingdiscount = bookingdiscount;
		this.deductiononothercharges = deductiononothercharges;
		this.bookingcomment = bookingcomment;
		this.iscancelled = iscancelled;
		this.canceldeduction = canceldeduction;
		this.cancellationdate = cancellationdate;
		this.cancellationcomment = cancellationcomment;
		this.unitmodificationdetails = unitmodificationdetails;
		this.parkingtype = parkingtype;
		this.demandlettergenerationdate = demandlettergenerationdate;
		this.refundmasters = refundmasters;
		this.unitmodificationstatuses = unitmodificationstatuses;
		this.paymentmasters = paymentmasters;
	}

	public Long getUnitbookingid() {
		return this.unitbookingid;
	}

	public void setUnitbookingid(Long unitbookingid) {
		this.unitbookingid = unitbookingid;
	}

	public Usermaster getUsermasterByBookedby() {
		return this.usermasterByBookedby;
	}

	public void setUsermasterByBookedby(Usermaster usermasterByBookedby) {
		this.usermasterByBookedby = usermasterByBookedby;
	}

	public Usermaster getUsermasterByCancelledby() {
		return this.usermasterByCancelledby;
	}

	public void setUsermasterByCancelledby(Usermaster usermasterByCancelledby) {
		this.usermasterByCancelledby = usermasterByCancelledby;
	}

	public Parkingmaster getParkingmaster() {
		return this.parkingmaster;
	}

	public void setParkingmaster(Parkingmaster parkingmaster) {
		this.parkingmaster = parkingmaster;
	}

	public Customermaster getCustomermaster() {
		return this.customermaster;
	}

	public void setCustomermaster(Customermaster customermaster) {
		this.customermaster = customermaster;
	}

	public Unitmaster getUnitmaster() {
		return this.unitmaster;
	}

	public void setUnitmaster(Unitmaster unitmaster) {
		this.unitmaster = unitmaster;
	}

	public Date getBookingdate() {
		return this.bookingdate;
	}

	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}

	public long getBookingformnumber() {
		return this.bookingformnumber;
	}

	public void setBookingformnumber(long bookingformnumber) {
		this.bookingformnumber = bookingformnumber;
	}

	public Double getBookingdiscount() {
		return this.bookingdiscount;
	}

	public void setBookingdiscount(Double bookingdiscount) {
		this.bookingdiscount = bookingdiscount;
	}

	public Double getDeductiononothercharges() {
		return this.deductiononothercharges;
	}

	public void setDeductiononothercharges(Double deductiononothercharges) {
		this.deductiononothercharges = deductiononothercharges;
	}

	public Clob getBookingcomment() {
		return this.bookingcomment;
	}

	public void setBookingcomment(Clob bookingcomment) {
		this.bookingcomment = bookingcomment;
	}

	public Boolean getIscancelled() {
		return this.iscancelled;
	}

	public void setIscancelled(Boolean iscancelled) {
		this.iscancelled = iscancelled;
	}

	public Double getCanceldeduction() {
		return this.canceldeduction;
	}

	public void setCanceldeduction(Double canceldeduction) {
		this.canceldeduction = canceldeduction;
	}

	public Date getCancellationdate() {
		return this.cancellationdate;
	}

	public void setCancellationdate(Date cancellationdate) {
		this.cancellationdate = cancellationdate;
	}

	public Clob getCancellationcomment() {
		return this.cancellationcomment;
	}

	public void setCancellationcomment(Clob cancellationcomment) {
		this.cancellationcomment = cancellationcomment;
	}

	public Clob getUnitmodificationdetails() {
		return this.unitmodificationdetails;
	}

	public void setUnitmodificationdetails(Clob unitmodificationdetails) {
		this.unitmodificationdetails = unitmodificationdetails;
	}

	public Integer getParkingtype() {
		return this.parkingtype;
	}

	public void setParkingtype(Integer parkingtype) {
		this.parkingtype = parkingtype;
	}

	public Date getDemandlettergenerationdate() {
		return this.demandlettergenerationdate;
	}

	public void setDemandlettergenerationdate(Date demandlettergenerationdate) {
		this.demandlettergenerationdate = demandlettergenerationdate;
	}

	public Set getRefundmasters() {
		return this.refundmasters;
	}

	public void setRefundmasters(Set refundmasters) {
		this.refundmasters = refundmasters;
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

}
