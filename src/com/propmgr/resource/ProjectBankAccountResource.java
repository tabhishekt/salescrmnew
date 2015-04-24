package com.propmgr.resource;


public class ProjectBankAccountResource {
	private long id;
	private long projectId;
	private AddressResource address;
	private CodeTableResource bankAccountType;
	private String accountTypeName;
	private String bankName;
	private String accountNumber;
	private String accountHolderName;
	private String ifscCode;
	private String micrCode;
	private String displayBankInformation;
	
	public ProjectBankAccountResource(long id, long projectId,
			AddressResource address, CodeTableResource bankAccountType, String accountTypeName,
			String bankName, String accountNumber, String accountHolderName,
			String ifscCode, String micrCode, String displayBankInformation) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.address = address;
		this.bankAccountType = bankAccountType;
		this.accountTypeName = accountTypeName;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.ifscCode = ifscCode;
		this.micrCode = micrCode;
		this.displayBankInformation = displayBankInformation;
	}

	public long getId() {
		return id;
	}

	public long getProjectId() {
		return projectId;
	}

	public AddressResource getAddress() {
		return address;
	}

	public CodeTableResource getBankAccountType() {
		return bankAccountType;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public String getBankName() {
		return bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public String getMicrCode() {
		return micrCode;
	}

	public String getDisplayBankInformation() {
		return displayBankInformation;
	}
}
