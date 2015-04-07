package com.propmgr.resource;

import java.util.Map;

public class ProjectBuildingResource {
	private long id;
	private long projectId;
	private ProjectPhaseResource projectPhase;
	private String projectPhaseName;
	private String name;
	private String wingName;
	private long floorCount;
	private String type;
	private CodeTableResource currentStatus;
	private String expectedCompletionDate;
	private String remarks;
	private boolean hasMultiplePaymentSchedules;
	private Map<String, UnitPaymentScheduleResource> paymentSchedule;
	private Map<Integer, Double> floorRise;
	private Map<Integer, Map<String, Boolean>> availability;
	private Map<String, Map<String, Double>> unitCharges;
	
	public ProjectBuildingResource(long id, long projectId, ProjectPhaseResource projectPhase,
			String projectPhaseName, String name, String wingName,
			long floorCount, String type, CodeTableResource currentStatus, 
			String expectedCompletionDate, String remarks, boolean hasMultiplePaymentSchedules, 
			Map<String, UnitPaymentScheduleResource> paymentSchedule, 
			Map<Integer, Double> floorRise, Map<Integer, Map<String, Boolean>> availability,
			Map<String, Map<String, Double>> unitCharges) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.projectPhase = projectPhase;
		this.projectPhaseName = projectPhaseName;
		this.name = name;
		this.wingName = wingName;
		this.floorCount = floorCount;
		this.type = type;
		this.currentStatus = currentStatus;
		this.expectedCompletionDate = expectedCompletionDate;
		this.remarks = remarks;
		this.paymentSchedule = paymentSchedule;
		this.floorRise = floorRise;
		this.availability = availability;
		this.hasMultiplePaymentSchedules = hasMultiplePaymentSchedules;
		this.unitCharges = unitCharges;
	}

	public long getId() {
		return id;
	}

	public long getProjectId() {
		return projectId;
	}

	public ProjectPhaseResource getProjectPhase() {
		return projectPhase;
	}

	public String getProjectPhaseName() {
		return projectPhaseName;
	}

	public String getName() {
		return name;
	}

	public String getWingName() {
		return wingName;
	}

	public long getFloorCount() {
		return floorCount;
	}

	public String getType() {
		return type;
	}

	public CodeTableResource getCurrentStatus() {
		return currentStatus;
	}

	public String getExpectedCompletionDate() {
		return expectedCompletionDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public Map<String, UnitPaymentScheduleResource> getPaymentSchedule() {
		return paymentSchedule;
	}

	public Map<Integer, Double> getFloorRise() {
		return floorRise;
	}
	
	public Map<Integer, Map<String, Boolean>> getAvailability() {
		return availability;
	}

	public boolean isHasMultiplePaymentSchedules() {
		return hasMultiplePaymentSchedules;
	}

	public void setHasMultiplePaymentSchedules(boolean hasMultiplePaymentSchedules) {
		this.hasMultiplePaymentSchedules = hasMultiplePaymentSchedules;
	}

	public Map<String, Map<String, Double>> getUnitCharges() {
		return unitCharges;
	}
}
