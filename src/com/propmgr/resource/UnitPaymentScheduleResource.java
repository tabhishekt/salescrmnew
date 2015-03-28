package com.propmgr.resource;




public class UnitPaymentScheduleResource {
	private Long id;
	private long buildingId;
	private String buildingName;
	private int position;
	private String type;
	private String description;
	private String scheduledate;
	private double amount;
	private double percentamount;
	
	public UnitPaymentScheduleResource(Long id, long buildingId, String buildingName, int position,
			String type, String description, String scheduledate, double amount, double percentamount) {
		super();
		this.id = id;
		this.buildingId = buildingId;
		this.buildingName = buildingName;
		this.position = position;
		this.type = type;
		this.description = description;
		this.scheduledate = scheduledate;
		this.amount = amount;
		this.percentamount = percentamount;
	}
	
	public Long getId() {
		return id;
	}
	
	public long getBuildingId() {
		return buildingId;
	}
	
	public String getBuildingName() {
		return buildingName;
	}
	
	public int getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getScheduledate() {
		return scheduledate;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public double getPercentamount() {
		return percentamount;
	}
}
