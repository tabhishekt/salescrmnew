package com.propmgr.dao;

// Generated Jul 28, 2015 11:20:32 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Parkingmaster generated by hbm2java
 */
public class Parkingmaster implements java.io.Serializable {

	private Long parkingmasterid;
	private Parkingtype parkingtype;
	private Projectbuilding projectbuilding;
	private int total;
	private int available;
	private int booked;
	private Set unitbookings = new HashSet(0);

	public Parkingmaster() {
	}

	public Parkingmaster(Parkingtype parkingtype,
			Projectbuilding projectbuilding, int total, int available,
			int booked) {
		this.parkingtype = parkingtype;
		this.projectbuilding = projectbuilding;
		this.total = total;
		this.available = available;
		this.booked = booked;
	}

	public Parkingmaster(Parkingtype parkingtype,
			Projectbuilding projectbuilding, int total, int available,
			int booked, Set unitbookings) {
		this.parkingtype = parkingtype;
		this.projectbuilding = projectbuilding;
		this.total = total;
		this.available = available;
		this.booked = booked;
		this.unitbookings = unitbookings;
	}

	public Long getParkingmasterid() {
		return this.parkingmasterid;
	}

	public void setParkingmasterid(Long parkingmasterid) {
		this.parkingmasterid = parkingmasterid;
	}

	public Parkingtype getParkingtype() {
		return this.parkingtype;
	}

	public void setParkingtype(Parkingtype parkingtype) {
		this.parkingtype = parkingtype;
	}

	public Projectbuilding getProjectbuilding() {
		return this.projectbuilding;
	}

	public void setProjectbuilding(Projectbuilding projectbuilding) {
		this.projectbuilding = projectbuilding;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getAvailable() {
		return this.available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getBooked() {
		return this.booked;
	}

	public void setBooked(int booked) {
		this.booked = booked;
	}

	public Set getUnitbookings() {
		return this.unitbookings;
	}

	public void setUnitbookings(Set unitbookings) {
		this.unitbookings = unitbookings;
	}

}
