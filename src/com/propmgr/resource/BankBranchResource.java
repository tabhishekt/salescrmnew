package com.propmgr.resource;

public class BankBranchResource {
	private long id;
	private String name;
	private String bankName;
	private String branchName;
	private String ifscCode;
	private String micrCode;
	private AddressResource branchAddress;
	private String displayBranchAddress;
	
	public BankBranchResource(long id, String name, String bankName,
			String branchName, String ifscCode, String micrCode,
			AddressResource branchAddress, String displayBranchAddress) {
		super();
		this.id = id;
		this.name = name;
		this.bankName = bankName;
		this.branchName = branchName;
		this.ifscCode = ifscCode;
		this.micrCode = micrCode;
		this.branchAddress = branchAddress;
		this.displayBranchAddress = displayBranchAddress;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBankName() {
		return bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public String getMicrCode() {
		return micrCode;
	}

	public AddressResource getBranchAddress() {
		return branchAddress;
	}

	public String getDisplayBranchAddress() {
		return displayBranchAddress;
	}
}
