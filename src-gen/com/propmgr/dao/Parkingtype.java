package com.propmgr.dao;

// Generated Oct 19, 2015 11:52:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Parkingtype generated by hbm2java
 */
public class Parkingtype implements java.io.Serializable {

	private Long parkingtypeid;
	private String parkingcode;
	private String parkingname;
	private Set parkingmasters = new HashSet(0);

	public Parkingtype() {
	}

	public Parkingtype(String parkingcode) {
		this.parkingcode = parkingcode;
	}

	public Parkingtype(String parkingcode, String parkingname,
			Set parkingmasters) {
		this.parkingcode = parkingcode;
		this.parkingname = parkingname;
		this.parkingmasters = parkingmasters;
	}

	public Long getParkingtypeid() {
		return this.parkingtypeid;
	}

	public void setParkingtypeid(Long parkingtypeid) {
		this.parkingtypeid = parkingtypeid;
	}

	public String getParkingcode() {
		return this.parkingcode;
	}

	public void setParkingcode(String parkingcode) {
		this.parkingcode = parkingcode;
	}

	public String getParkingname() {
		return this.parkingname;
	}

	public void setParkingname(String parkingname) {
		this.parkingname = parkingname;
	}

	public Set getParkingmasters() {
		return this.parkingmasters;
	}

	public void setParkingmasters(Set parkingmasters) {
		this.parkingmasters = parkingmasters;
	}

}
