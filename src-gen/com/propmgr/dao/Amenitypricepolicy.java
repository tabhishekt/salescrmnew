package com.propmgr.dao;

// Generated Jun 23, 2015 11:52:03 PM by Hibernate Tools 3.4.0.CR1

/**
 * Amenitypricepolicy generated by hbm2java
 */
public class Amenitypricepolicy implements java.io.Serializable {

	private Long amenitypricepolicyid;
	private Unitpricepolicy unitpricepolicy;
	private Amenity amenity;
	private double amenitycharge;

	public Amenitypricepolicy() {
	}

	public Amenitypricepolicy(Unitpricepolicy unitpricepolicy, Amenity amenity,
			double amenitycharge) {
		this.unitpricepolicy = unitpricepolicy;
		this.amenity = amenity;
		this.amenitycharge = amenitycharge;
	}

	public Long getAmenitypricepolicyid() {
		return this.amenitypricepolicyid;
	}

	public void setAmenitypricepolicyid(Long amenitypricepolicyid) {
		this.amenitypricepolicyid = amenitypricepolicyid;
	}

	public Unitpricepolicy getUnitpricepolicy() {
		return this.unitpricepolicy;
	}

	public void setUnitpricepolicy(Unitpricepolicy unitpricepolicy) {
		this.unitpricepolicy = unitpricepolicy;
	}

	public Amenity getAmenity() {
		return this.amenity;
	}

	public void setAmenity(Amenity amenity) {
		this.amenity = amenity;
	}

	public double getAmenitycharge() {
		return this.amenitycharge;
	}

	public void setAmenitycharge(double amenitycharge) {
		this.amenitycharge = amenitycharge;
	}

}