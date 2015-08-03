package com.propmgr.resource;


public class ProjectBankAccountResource {
	private long id;
	private long projectId;
	private String displayBankName;
	private CodeTableResource bankAccountType;
	private String accountTypeName;
	private String accountNumber;
	private String accountHolderName;
	private BankBranchResource bankBranch;
	private String displayBankInformation;
	
	public ProjectBankAccountResource(long id, long projectId, String displayBankName,
			CodeTableResource bankAccountType, String accountTypeName,
			String accountNumber, String accountHolderName,
			BankBranchResource bankBranch, String displayBankInformation) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.displayBankName = displayBankName;
		this.bankAccountType = bankAccountType;
		this.accountTypeName = accountTypeName;
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.bankBranch = bankBranch;
		this.displayBankInformation = displayBankInformation;
	}

	public long getId() {
		return id;
	}

	public long getProjectId() {
		return projectId;
	}

	public String getDisplayBankName() {
		return displayBankName;
	}

	public CodeTableResource getBankAccountType() {
		return bankAccountType;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public BankBranchResource getBankBranch() {
		return bankBranch;
	}

	public String getDisplayBankInformation() {
		return displayBankInformation;
	}
}
