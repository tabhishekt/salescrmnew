package com.propmgr.resource;

public class UnitModificationStateResource {
	private long id;
	private String name;
	
	public UnitModificationStateResource(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
