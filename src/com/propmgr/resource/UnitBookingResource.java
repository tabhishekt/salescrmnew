package com.propmgr.resource;

public class UnitBookingResource {
	private long id;
	private long userId;
	private long buildingId;
	private long projectId;
	private long bookingFormNumber;
	private String customerDisplayName;
	private String unitDisplayName;
	private String userDisplayName;
	private String bookingDate;
	private double bookingAmount;
	private double discount;
	private double deductionOnOtherCharges;
	private String comment;
	private double totalUnitCost;
	private double totalUnitCostWithDiscount;
	private double totalPaymentReceived;
	private double balancePayment;
	private boolean cancelled;
	private String cancelUserDisplayName;
	private double cancelDeduction;
	private double cancelDeductionPercent;
	private String cancellationDate;
	private String cancellationComment;
	private RefundResource refundDetails;
	private String unitModificationDetails;
	private UnitModificationStateResource unitModificationStatus;
	private String unitModificationStatusDate;
	private String unitModificationStatusComment;
	
	public UnitBookingResource(long id, long userId, long buildingId, long projectId, 
			long bookingFormNumber, String customerDisplayName, String unitDisplayName,
			String userDisplayName, String bookingDate, double bookingAmount, double discount,
			double deductionOnOtherCharges, String comment, double totalUnitCost,
			double totalUnitCostWithDiscount, double totalPaymentReceived,
			double balancePayment, boolean cancelled, String cancelUserDisplayName, 
			double cancelDeduction, double cancelDeductionPercent, String cancellationDate, String cancellationComment,
			RefundResource refundDetails, String unitModificationDetails, 
			UnitModificationStateResource unitModificationStatus, String unitModificationStatusDate, 
			String unitModificationStatusComment) {
		super();
		this.id = id;
		this.userId = userId;
		this.buildingId = buildingId;
		this.projectId = projectId;
		this.bookingFormNumber = bookingFormNumber;
		this.customerDisplayName = customerDisplayName;
		this.unitDisplayName = unitDisplayName;
		this.userDisplayName = userDisplayName;
		this.bookingDate = bookingDate;
		this.bookingAmount = bookingAmount;
		this.deductionOnOtherCharges = deductionOnOtherCharges;
		this.discount = discount;
		this.comment = comment;
		this.totalUnitCost = totalUnitCost;
		this.totalUnitCostWithDiscount = totalUnitCostWithDiscount;
		this.totalPaymentReceived = totalPaymentReceived;
		this.balancePayment = balancePayment;
		this.cancelled = cancelled;
		this.cancelUserDisplayName = cancelUserDisplayName;
		this.cancelDeduction = cancelDeduction;
		this.cancelDeductionPercent = cancelDeductionPercent;
		this.cancellationDate = cancellationDate;
		this.cancellationComment = cancellationComment;
		this.refundDetails = refundDetails;
		this.unitModificationDetails = unitModificationDetails;
		this.unitModificationStatus = unitModificationStatus;
		this.unitModificationStatusDate = unitModificationStatusDate;
		this.unitModificationStatusComment = unitModificationStatusComment;
	}

	public long getId() {
		return id;
	}

	public long getUserId() {
		return userId;
	}

	public long getBuildingId() {
		return buildingId;
	}

	public long getProjectId() {
		return projectId;
	}
	
	public long getBookingFormNumber() {
		return bookingFormNumber;
	}

	public String getCustomerDisplayName() {
		return customerDisplayName;
	}

	public String getUnitDisplayName() {
		return unitDisplayName;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public double getBookingAmount() {
		return bookingAmount;
	}
	
	public double getDiscount() {
		return discount;
	}

	public double getDeductionOnOtherCharges() {
		return deductionOnOtherCharges;
	}

	public String getComment() {
		return comment;
	}

	public double getTotalUnitCost() {
		return totalUnitCost;
	}

	public double getTotalUnitCostWithDiscount() {
		return totalUnitCostWithDiscount;
	}

	public double getTotalPaymentReceived() {
		return totalPaymentReceived;
	}

	public double getBalancePayment() {
		return balancePayment;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public String getCancelUserDisplayName() {
		return cancelUserDisplayName;
	}

	public double getCancelDeduction() {
		return cancelDeduction;
	}

	public double getCancelDeductionPercent() {
		return cancelDeductionPercent;
	}

	public void setCancelDeduction(double cancelDeduction) {
		this.cancelDeduction = cancelDeduction;
	}

	public String getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(String cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public String getCancellationComment() {
		return cancellationComment;
	}

	public void setCancellationComment(String cancellationComment) {
		this.cancellationComment = cancellationComment;
	}

	public RefundResource getRefundDetails() {
		return refundDetails;
	}

	public String getUnitModificationDetails() {
		return unitModificationDetails;
	}

	public UnitModificationStateResource getUnitModificationStatus() {
		return unitModificationStatus;
	}

	public String getUnitModificationStatusDate() {
		return unitModificationStatusDate;
	}

	public String getUnitModificationStatusComment() {
		return unitModificationStatusComment;
	}
}
