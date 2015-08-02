package com.propmgr.resource;

import java.util.List;
import java.util.Map;

public class ProjectBuildingResource {
	private long id;
	private long projectId;
	private ProjectPhaseResource projectPhase;
	private String projectPhaseName;
	private String name;
	private String wingName;
	private long floorCount;
	private int parkingFloorCount;
	private String type;
	private CodeTableResource currentStatus;
	private String expectedCompletionDate;
	private String remarks;
	private boolean hasMultiplePaymentSchedules;
	private String planApprovalDate;
	private Map<String, UnitPaymentScheduleResource> paymentSchedule;
	private Map<Integer, Double> floorRise;
	private Map<Integer, List<UnitAvailabilityResource>> availability;
	private Map<String, Map<String, Double>> unitCharges;
	private Map<String, ParkingResource> parking;
	
	public ProjectBuildingResource(long id, long projectId, ProjectPhaseResource projectPhase,
			String projectPhaseName, String name, String wingName, long floorCount, 
			int parkingFloorCount, String type, CodeTableResource currentStatus, 
			String expectedCompletionDate, String remarks, boolean hasMultiplePaymentSchedules, 
			String planApprovalDate, Map<String, UnitPaymentScheduleResource> paymentSchedule, 
			Map<Integer, Double> floorRise, Map<Integer, List<UnitAvailabilityResource>> availability,
			Map<String, Map<String, Double>> unitCharges, Map<String, ParkingResource> parking) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.projectPhase = projectPhase;
		this.projectPhaseName = projectPhaseName;
		this.name = name;
		this.wingName = wingName;
		this.floorCount = floorCount;
		this.parkingFloorCount = parkingFloorCount;
		this.type = type;
		this.currentStatus = currentStatus;
		this.expectedCompletionDate = expectedCompletionDate;
		this.remarks = remarks;
		this.paymentSchedule = paymentSchedule;
		this.floorRise = floorRise;
		this.availability = availability;
		this.hasMultiplePaymentSchedules = hasMultiplePaymentSchedules;
		this.planApprovalDate = planApprovalDate;
		this.unitCharges = unitCharges;
		this.parking = parking;
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

	public int getParkingFloorCount() {
		return parkingFloorCount;
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
	
	public Map<Integer, List<UnitAvailabilityResource>> getAvailability() {
		return availability;
	}

	public boolean isHasMultiplePaymentSchedules() {
		return hasMultiplePaymentSchedules;
	}

	public String getPlanApprovalDate() {
		return planApprovalDate;
	}

	public void setHasMultiplePaymentSchedules(boolean hasMultiplePaymentSchedules) {
		this.hasMultiplePaymentSchedules = hasMultiplePaymentSchedules;
	}

	public Map<String, Map<String, Double>> getUnitCharges() {
		return unitCharges;
	}

	public Map<String, ParkingResource> getParking() {
		return parking;
	}
}
