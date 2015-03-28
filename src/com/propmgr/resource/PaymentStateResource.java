package com.propmgr.resource;

public class PaymentStateResource {
	private long id;
	private String name;
	private boolean allowStateChange;
	
	public PaymentStateResource(long id, String name, boolean allowStateChange) {
		super();
		this.id = id;
		this.name = name;
		this.allowStateChange = allowStateChange;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isAllowStateChange() {
		return allowStateChange;
	}
}
