package com.propmgr.dao;

// Generated Jul 30, 2015 6:25:17 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Projectbuilding generated by hbm2java
 */
public class Projectbuilding implements java.io.Serializable {

	private Long projectbuildingid;
	private Projectphase projectphase;
	private String buildingname;
	private String wingname;
	private long floorcount;
	private String buildingtype;
	private Integer currentstatus;
	private Date expectedcompletiondate;
	private boolean hasmultiplepaymentschedules;
	private Clob remarks;
	private Integer parkingfloorcount;
	private Set unitpaymentschedules = new HashSet(0);
	private Set parkingmasters = new HashSet(0);
	private Set unitmasters = new HashSet(0);

	public Projectbuilding() {
	}

	public Projectbuilding(Projectphase projectphase, String buildingname,
			long floorcount, boolean hasmultiplepaymentschedules) {
		this.projectphase = projectphase;
		this.buildingname = buildingname;
		this.floorcount = floorcount;
		this.hasmultiplepaymentschedules = hasmultiplepaymentschedules;
	}

	public Projectbuilding(Projectphase projectphase, String buildingname,
			String wingname, long floorcount, String buildingtype,
			Integer currentstatus, Date expectedcompletiondate,
			boolean hasmultiplepaymentschedules, Clob remarks,
			Integer parkingfloorcount, Set unitpaymentschedules,
			Set parkingmasters, Set unitmasters) {
		this.projectphase = projectphase;
		this.buildingname = buildingname;
		this.wingname = wingname;
		this.floorcount = floorcount;
		this.buildingtype = buildingtype;
		this.currentstatus = currentstatus;
		this.expectedcompletiondate = expectedcompletiondate;
		this.hasmultiplepaymentschedules = hasmultiplepaymentschedules;
		this.remarks = remarks;
		this.parkingfloorcount = parkingfloorcount;
		this.unitpaymentschedules = unitpaymentschedules;
		this.parkingmasters = parkingmasters;
		this.unitmasters = unitmasters;
	}

	public Long getProjectbuildingid() {
		return this.projectbuildingid;
	}

	public void setProjectbuildingid(Long projectbuildingid) {
		this.projectbuildingid = projectbuildingid;
	}

	public Projectphase getProjectphase() {
		return this.projectphase;
	}

	public void setProjectphase(Projectphase projectphase) {
		this.projectphase = projectphase;
	}

	public String getBuildingname() {
		return this.buildingname;
	}

	public void setBuildingname(String buildingname) {
		this.buildingname = buildingname;
	}

	public String getWingname() {
		return this.wingname;
	}

	public void setWingname(String wingname) {
		this.wingname = wingname;
	}

	public long getFloorcount() {
		return this.floorcount;
	}

	public void setFloorcount(long floorcount) {
		this.floorcount = floorcount;
	}

	public String getBuildingtype() {
		return this.buildingtype;
	}

	public void setBuildingtype(String buildingtype) {
		this.buildingtype = buildingtype;
	}

	public Integer getCurrentstatus() {
		return this.currentstatus;
	}

	public void setCurrentstatus(Integer currentstatus) {
		this.currentstatus = currentstatus;
	}

	public Date getExpectedcompletiondate() {
		return this.expectedcompletiondate;
	}

	public void setExpectedcompletiondate(Date expectedcompletiondate) {
		this.expectedcompletiondate = expectedcompletiondate;
	}

	public boolean isHasmultiplepaymentschedules() {
		return this.hasmultiplepaymentschedules;
	}

	public void setHasmultiplepaymentschedules(
			boolean hasmultiplepaymentschedules) {
		this.hasmultiplepaymentschedules = hasmultiplepaymentschedules;
	}

	public Clob getRemarks() {
		return this.remarks;
	}

	public void setRemarks(Clob remarks) {
		this.remarks = remarks;
	}

	public Integer getParkingfloorcount() {
		return this.parkingfloorcount;
	}

	public void setParkingfloorcount(Integer parkingfloorcount) {
		this.parkingfloorcount = parkingfloorcount;
	}

	public Set getUnitpaymentschedules() {
		return this.unitpaymentschedules;
	}

	public void setUnitpaymentschedules(Set unitpaymentschedules) {
		this.unitpaymentschedules = unitpaymentschedules;
	}

	public Set getParkingmasters() {
		return this.parkingmasters;
	}

	public void setParkingmasters(Set parkingmasters) {
		this.parkingmasters = parkingmasters;
	}

	public Set getUnitmasters() {
		return this.unitmasters;
	}

	public void setUnitmasters(Set unitmasters) {
		this.unitmasters = unitmasters;
	}

}
