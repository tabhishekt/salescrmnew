package com.propmgr.resource;

import java.util.Map;


public class UnitFloorRiseResource {
	private long id;
	private long floorCount;
	private Map<Integer, Long> floorRise;
	
	public UnitFloorRiseResource(long id, long floorCount,
			Map<Integer, Long> floorRise) {
		super();
		this.id = id;
		this.floorCount = floorCount;
		this.floorRise = floorRise;
	}

	public long getId() {
		return id;
	}

	public long getFloorCount() {
		return floorCount;
	}

	public Map<Integer, Long> getFloorRise() {
		return floorRise;
	}
}
