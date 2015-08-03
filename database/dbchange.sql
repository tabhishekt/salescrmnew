ALTER TABLE unitPricePolicy
	ADD interestRate double;
	
ALTER TABLE projectBuilding
	ADD parkingFloorCount int;
	
ALTER TABLE unitBooking
	ADD demandLetterGenerationDate date;
	
ALTER TABLE projectBuilding
	ADD planApprovalDate date;
	
ALTER TABLE unitPricePolicy
	ADD gracePeriod int;
	
UPDATE unitPricePolicy SET
	gracePeriod = 10;

UPDATE unitPricePolicy SET
	interestRate = 24;

CREATE TABLE bankBranch
(
	bankBranchID bigint auto_increment NOT NULL UNIQUE,
	bankName varchar(64) NOT NULL,
	branchName varchar(64) NOT NULL,
	branchAddress bigint NOT NULL,
	ifscCode varchar(64) NOT NULL,
	micrCode varchar(64) NOT NULL,
	PRIMARY KEY (bankBranchID)
);

ALTER TABLE bankBranch
	ADD FOREIGN KEY (branchAddress)
	REFERENCES address (addressID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE paymentMaster
	DROP bankName;

ALTER TABLE paymentMaster
	DROP bankBranch;
	
ALTER TABLE paymentMaster
	ADD bankBranch bigint;

ALTER TABLE paymentMaster
	ADD FOREIGN KEY (bankBranch)
	REFERENCES bankBranch (bankBranchID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

UPDATE paymentMaster SET
	bankBranch = 1 where paymentType = 4;

ALTER TABLE projectBankAccount
	DROP bankName;

ALTER TABLE projectBankAccount
	DROP bankAddress;

ALTER TABLE projectBankAccount
	DROP micrCode;
	
ALTER TABLE projectBankAccount
	DROP ifscCode;

ALTER TABLE projectBankAccount
	ADD bankBranch bigint;

ALTER TABLE projectBankAccount
	ADD FOREIGN KEY (bankBranch)
	REFERENCES bankBranch (bankBranchID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

UPDATE projectBankAccount SET
	bankBranch = 1;

ALTER TABLE refundMaster
	DROP bankName;

ALTER TABLE refundMaster
	DROP bankBranch;
	
ALTER TABLE refundMaster
	ADD bankBranch bigint;
	
ALTER TABLE refundMaster
	ADD FOREIGN KEY (bankBranch)
	REFERENCES bankBranch (bankBranchID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

UPDATE refundMaster SET
	bankBranch = 1;