package com.propmgr.dao;

// Generated Jun 23, 2015 11:52:03 PM by Hibernate Tools 3.4.0.CR1

/**
 * Unitamenity generated by hbm2java
 */
public class Unitamenity implements java.io.Serializable {

	private Long unitamenityid;
	private Amenity amenity;
	private Unitmaster unitmaster;

	public Unitamenity() {
	}

	public Unitamenity(Amenity amenity, Unitmaster unitmaster) {
		this.amenity = amenity;
		this.unitmaster = unitmaster;
	}

	public Long getUnitamenityid() {
		return this.unitamenityid;
	}

	public void setUnitamenityid(Long unitamenityid) {
		this.unitamenityid = unitamenityid;
	}

	public Amenity getAmenity() {
		return this.amenity;
	}

	public void setAmenity(Amenity amenity) {
		this.amenity = amenity;
	}

	public Unitmaster getUnitmaster() {
		return this.unitmaster;
	}

	public void setUnitmaster(Unitmaster unitmaster) {
		this.unitmaster = unitmaster;
	}

}