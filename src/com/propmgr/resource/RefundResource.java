package com.propmgr.resource;



public class RefundResource {
	private long id;
	private double refundAmount;
	private String refundDate;
	private String bankName;
	private String bankBranch;
	private String chequeNumber;
	private String chequeDate;
	
	public RefundResource(long id, double refundAmount, String refundDate,
			String bankName, String bankBranch, String chequeNumber,
			String chequeDate) {
		super();
		this.id = id;
		this.refundAmount = refundAmount;
		this.refundDate = refundDate;
		this.bankName = bankName;
		this.bankBranch = bankBranch;
		this.chequeNumber = chequeNumber;
		this.chequeDate = chequeDate;
	}

	public long getId() {
		return id;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public String getRefundDate() {
		return refundDate;
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
}
