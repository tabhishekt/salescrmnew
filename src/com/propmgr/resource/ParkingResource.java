package com.propmgr.resource;

public class ParkingResource {
	private long id;
	private long buildingId;
	private CodeTableResource parkingType;
	private int total;
	private int available;
	private int booked;
	
	public ParkingResource(long id, long buildingId,
			CodeTableResource parkingType, int total, int available, int booked) {
		super();
		this.id = id;
		this.buildingId = buildingId;
		this.parkingType = parkingType;
		this.total = total;
		this.available = available;
		this.booked = booked;
	}

	public long getId() {
		return id;
	}

	public long getBuildingId() {
		return buildingId;
	}

	public CodeTableResource getParkingType() {
		return parkingType;
	}

	public int getTotal() {
		return total;
	}

	public int getAvailable() {
		return available;
	}

	public int getBooked() {
		return booked;
	}
}
