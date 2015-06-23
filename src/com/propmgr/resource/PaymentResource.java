package com.propmgr.resource;

import java.util.List;



public class PaymentResource {
	private long id;
	private long userId;
	private String userDisplayName;
	private long unitbookingId;
	private long bookingFormNumber;
	private CodeTableResource paymentType;
	private String paymentTypeName;
	private long receiptNumber;
	private long altReceiptNumber;
	private double receiptAmount;
	private String description;
	private String receiptDate;
	private String bankName;
	private String bankBranch;
	private String chequeNumber;
	private String chequeDate;
	private String utrNumber;
	private String cardNumber;
	private String cardExpiryDate;
	private String cardHolderName;
	private String cardType;
	private String displayPaymentStatus;
	private PaymentStateResource paymentStatus;
	private String statusUpdatedBy;
	private String statusDate;
	private String statusComment;
	private List<CodeTableResource> paymentStages;
	
	public PaymentResource(long id, long userId, String userDisplayName,
			long unitbookingId, long bookingFormNumber,
			CodeTableResource paymentType, String paymentTypeName,
			long receiptNumber, long altReceiptNumber, double receiptAmount, String description,
			String receiptDate, String bankName, String bankBranch,
			String chequeNumber, String chequeDate, String utrNumber, String cardNumber,
			String cardExpiryDate, String cardHolderName, String cardType, String displayPaymentStatus,
			PaymentStateResource paymentStatus, String statusUpdatedBy, String statusDate,
			String statusComment, List<CodeTableResource> paymentStages) {
		super();
		this.id = id;
		this.userId = userId;
		this.userDisplayName = userDisplayName;
		this.unitbookingId = unitbookingId;
		this.bookingFormNumber = bookingFormNumber;
		this.paymentType = paymentType;
		this.paymentTypeName = paymentTypeName;
		this.receiptNumber = receiptNumber;
		this.altReceiptNumber = altReceiptNumber;
		this.receiptAmount = receiptAmount;
		this.description = description;
		this.receiptDate = receiptDate;
		this.bankName = bankName;
		this.bankBranch = bankBranch;
		this.chequeNumber = chequeNumber;
		this.chequeDate = chequeDate;
		this.utrNumber = utrNumber;
		this.cardNumber = cardNumber;
		this.cardExpiryDate = cardExpiryDate;
		this.cardHolderName = cardHolderName;
		this.cardType = cardType;
		this.displayPaymentStatus = displayPaymentStatus;
		this.paymentStatus = paymentStatus;
		this.statusUpdatedBy = statusUpdatedBy;
		this.statusDate = statusDate;
		this.statusComment = statusComment;
		this.paymentStages = paymentStages;
	}

	public long getId() {
		return id;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public long getUnitbookingId() {
		return unitbookingId;
	}

	public long getBookingFormNumber() {
		return bookingFormNumber;
	}

	public CodeTableResource getPaymentType() {
		return paymentType;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public long getReceiptNumber() {
		return receiptNumber;
	}
	
	public long getAltReceiptNumber() {
		return altReceiptNumber;
	}

	public double getReceiptAmount() {
		return receiptAmount;
	}

	public String getDescription() {
		return description;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public String getBankName() {
		return bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public String getChequeDate() {
		return chequeDate;
	}

	public String getUtrNumber() {
		return utrNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public String getCardType() {
		return cardType;
	}

	public String getDisplayPaymentStatus() {
		return displayPaymentStatus;
	}

	public PaymentStateResource getPaymentStatus() {
		return paymentStatus;
	}

	public String getStatusUpdatedBy() {
		return statusUpdatedBy;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public String getStatusComment() {
		return statusComment;
	}

	public List<CodeTableResource> getPaymentStages() {
		return paymentStages;
	}
}
