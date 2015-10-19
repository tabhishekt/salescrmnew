
/* Drop Tables */

DROP TABLE actionRole;
DROP TABLE actionMaster;
DROP TABLE paymentStatus;
DROP TABLE paymentStage;
DROP TABLE paymentMaster;
DROP TABLE enquiryComment;
DROP TABLE enquiry;
DROP TABLE refundMaster;
DROP TABLE unitModificationStatus;
DROP TABLE unitBooking;
DROP TABLE customerMaster;
DROP TABLE userRole;
DROP TABLE userMaster;
DROP TABLE unitPaymentSchedule;
DROP TABLE unitAmenity;
DROP TABLE unitMaster;
DROP TABLE parkingMaster;
DROP TABLE projectBuilding;
DROP TABLE projectPhase;
DROP TABLE projectBankAccount;
DROP TABLE projectMaster;
DROP TABLE bankBranch;
DROP TABLE organization;
DROP TABLE address;
DROP TABLE amenityPricePolicy;
DROP TABLE amenity;
DROP TABLE bankAccountType;
DROP TABLE cityMaster;
DROP TABLE person;
DROP TABLE contactInfo;
DROP TABLE pageMaster;
DROP TABLE parkingType;
DROP TABLE paymentState;
DROP TABLE paymentType;
DROP TABLE roleMaster;
DROP TABLE sourceMaster;
DROP TABLE stateMaster;
DROP TABLE unitClassificationMaster;
DROP TABLE unitModificationState;
DROP TABLE unitPricePolicy;
DROP TABLE unitType;




/* Create Tables */

CREATE TABLE actionMaster
(
	actionMasterID bigint auto_increment NOT NULL UNIQUE,
	pageMasterID bigint NOT NULL,
	actionName varchar(64) NOT NULL,
	actionDescription clob,
	PRIMARY KEY (actionMasterID)
);


CREATE TABLE actionRole
(
	actionRoleID bigint auto_increment NOT NULL UNIQUE,
	actionMasterID bigint NOT NULL,
	roleMasterID bigint NOT NULL,
	PRIMARY KEY (actionRoleID)
);


CREATE TABLE address
(
	addressID bigint auto_increment NOT NULL UNIQUE,
	addressLine1 clob,
	addressLine2 clob,
	city bigint NOT NULL,
	state bigint NOT NULL,
	zipCode varchar(64),
	PRIMARY KEY (addressID)
);


CREATE TABLE amenity
(
	amenityID bigint auto_increment NOT NULL UNIQUE,
	amenityCode varchar(8) NOT NULL,
	amenityDescription varchar(128),
	PRIMARY KEY (amenityID)
);


CREATE TABLE amenityPricePolicy
(
	amenityPricePolicyID bigint auto_increment NOT NULL UNIQUE,
	amenityID bigint NOT NULL,
	unitPricePolicyID bigint NOT NULL,
	amenityCharge double NOT NULL,
	PRIMARY KEY (amenityPricePolicyID)
);


CREATE TABLE bankAccountType
(
	bankAccountTypeID bigint auto_increment NOT NULL UNIQUE,
	accountTypeCode varchar(8) NOT NULL,
	accountTypeName varchar(64) NOT NULL,
	PRIMARY KEY (bankAccountTypeID)
);


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


CREATE TABLE cityMaster
(
	cityMasterID bigint auto_increment NOT NULL UNIQUE,
	cityCode varchar(8) NOT NULL,
	cityName varchar(64) NOT NULL,
	PRIMARY KEY (cityMasterID)
);


CREATE TABLE contactInfo
(
	contactInfoID bigint auto_increment NOT NULL UNIQUE,
	phoneNumber varchar(64),
	alternateNumber varchar(64),
	mobileNumber varchar(64),
	emailID varchar(128),
	PRIMARY KEY (contactInfoID)
);


CREATE TABLE customerMaster
(
	customerID bigint auto_increment NOT NULL UNIQUE,
	personDetail bigint NOT NULL,
	coOwnerDetail bigint NOT NULL,
	address bigint NOT NULL,
	createdBy bigint NOT NULL,
	PRIMARY KEY (customerID)
);


CREATE TABLE enquiry
(
	enquiryID bigint auto_increment NOT NULL UNIQUE,
	orgUser bigint NOT NULL,
	customer bigint NOT NULL,
	sourceName bigint NOT NULL,
	interest bigint NOT NULL,
	originalEnquiryDate date,
	budget int,
	probability int,
	currentHomeStatus int,
	PRIMARY KEY (enquiryID)
);


CREATE TABLE enquiryComment
(
	enquiryCommentID bigint auto_increment NOT NULL UNIQUE,
	enquiry bigint NOT NULL,
	orgUser bigint NOT NULL,
	visitComment clob,
	visitDate date,
	followupDate date,
	PRIMARY KEY (enquiryCommentID)
);


CREATE TABLE organization
(
	orgID bigint auto_increment NOT NULL UNIQUE,
	orgName varchar(64) NOT NULL,
	orgAddress bigint NOT NULL,
	contactInfo bigint NOT NULL,
	contactPerson bigint NOT NULL,
	logoFileName varchar(64),
	PRIMARY KEY (orgID)
);


CREATE TABLE pageMaster
(
	pageMasterID bigint auto_increment NOT NULL UNIQUE,
	pageName varchar(64) NOT NULL,
	pageDescription clob,
	PRIMARY KEY (pageMasterID)
);


CREATE TABLE parkingMaster
(
	parkingMasterId bigint auto_increment NOT NULL UNIQUE,
	parkingTypeId bigint NOT NULL,
	projectBuildingID bigint NOT NULL,
	total int NOT NULL,
	available int NOT NULL,
	booked int NOT NULL,
	PRIMARY KEY (parkingMasterId)
);


CREATE TABLE parkingType
(
	parkingTypeId bigint auto_increment NOT NULL UNIQUE,
	parkingCode varchar(8) NOT NULL,
	parkingName varchar(64),
	PRIMARY KEY (parkingTypeId)
);


CREATE TABLE paymentMaster
(
	paymentID bigint auto_increment NOT NULL UNIQUE,
	unitBooking bigint NOT NULL,
	project bigint,
	paymentType bigint NOT NULL,
	orgUser bigint NOT NULL,
	receiptNumber bigint NOT NULL,
	altReceiptNumber varchar(32),
	paymentAmount double NOT NULL,
	paymentDescription clob,
	paymentReceivedDate date,
	bankBranch bigint,
	utrNumber varchar(64),
	chequeNumber varchar(32),
	chequeDate date,
	cardNumber varchar(64),
	cardExpiryDate varchar(32),
	cardHolderName varchar(64),
	cardType varchar(32),
	editable boolean NOT NULL,
	PRIMARY KEY (paymentID)
);


CREATE TABLE paymentStage
(
	paymentStageID bigint auto_increment NOT NULL UNIQUE,
	paymentStageType int NOT NULL,
	paymentID bigint NOT NULL,
	PRIMARY KEY (paymentStageID)
);


CREATE TABLE paymentState
(
	paymentStateID bigint auto_increment NOT NULL UNIQUE,
	paymentStateName varchar(64) NOT NULL,
	allowStateChange boolean NOT NULL,
	PRIMARY KEY (paymentStateID)
);


CREATE TABLE paymentStatus
(
	paymentStatusID bigint auto_increment NOT NULL UNIQUE,
	paymentID bigint NOT NULL,
	paymentStateID bigint NOT NULL,
	statusDate date NOT NULL,
	statusComment clob,
	updatedBy bigint NOT NULL,
	PRIMARY KEY (paymentStatusID)
);


CREATE TABLE paymentType
(
	paymentTypeID bigint auto_increment NOT NULL UNIQUE,
	paymentTypeCode varchar(8),
	paymentTypeDescription clob NOT NULL,
	PRIMARY KEY (paymentTypeID)
);


CREATE TABLE person
(
	personID bigint auto_increment NOT NULL UNIQUE,
	firstName varchar(64),
	middleName varchar(64),
	lastName varchar(64),
	contactInfo bigint,
	dateOfBirth date,
	profession varchar(64),
	otherDetail clob,
	PRIMARY KEY (personID)
);


CREATE TABLE projectBankAccount
(
	projectBankAccountID bigint auto_increment NOT NULL UNIQUE,
	project bigint NOT NULL,
	bankBranch bigint NOT NULL,
	accountType bigint NOT NULL,
	accountNumber varchar(64),
	accountHolderName varchar(64),
	PRIMARY KEY (projectBankAccountID)
);


CREATE TABLE projectBuilding
(
	projectBuildingID bigint auto_increment NOT NULL UNIQUE,
	projectPhase bigint NOT NULL,
	buildingName varchar(64) NOT NULL,
	wingName varchar(64),
	floorCount bigint NOT NULL,
	parkingFloorCount int,
	buildingType varchar(64),
	currentStatus int,
	expectedCompletionDate date,
	hasMultiplePaymentSchedules boolean NOT NULL,
	planApprovalDate date,
	remarks clob,
	PRIMARY KEY (projectBuildingID)
);


CREATE TABLE projectMaster
(
	projectID bigint auto_increment NOT NULL UNIQUE,
	orgnization bigint NOT NULL,
	address bigint NOT NULL,
	projectName varchar(64) NOT NULL,
	projectDescription clob,
	totalPhases bigint NOT NULL,
	termsAndConditions clob,
	logoFileName varchar(64),
	PRIMARY KEY (projectID)
);


CREATE TABLE projectPhase
(
	projectPhaseID bigint auto_increment NOT NULL UNIQUE,
	project bigint NOT NULL,
	projectPhaseName varchar(64) NOT NULL,
	projectPhaseDescription clob,
	PRIMARY KEY (projectPhaseID)
);


CREATE TABLE refundMaster
(
	refundMasterID bigint auto_increment NOT NULL UNIQUE,
	unitBookingID bigint NOT NULL,
	refundAmount double,
	refundDate date,
	bankBranch bigint,
	chequeNumber varchar(64),
	chequeDate date,
	PRIMARY KEY (refundMasterID)
);


CREATE TABLE roleMaster
(
	roleMasterID bigint auto_increment NOT NULL UNIQUE,
	roleName varchar(64) NOT NULL,
	roleDescription clob,
	isAdmin boolean NOT NULL,
	PRIMARY KEY (roleMasterID)
);


CREATE TABLE sourceMaster
(
	sourceMasterID bigint auto_increment NOT NULL UNIQUE,
	sourceCode varchar(8),
	sourceName varchar(64),
	PRIMARY KEY (sourceMasterID)
);


CREATE TABLE stateMaster
(
	stateMasterID bigint auto_increment NOT NULL UNIQUE,
	stateCode varchar(8),
	stateName varchar(64),
	PRIMARY KEY (stateMasterID)
);


CREATE TABLE unitAmenity
(
	unitAmenityID bigint auto_increment NOT NULL UNIQUE,
	unit bigint NOT NULL,
	amenity bigint NOT NULL,
	PRIMARY KEY (unitAmenityID)
);


CREATE TABLE unitBooking
(
	unitBookingID bigint auto_increment NOT NULL UNIQUE,
	unit bigint NOT NULL,
	customer bigint NOT NULL,
	bookedBy bigint NOT NULL,
	bookingDate date NOT NULL,
	bookingFormNumber bigint NOT NULL,
	bookingDiscount double,
	deductionOnOtherCharges double,
	bookingComment clob,
	parkingMasterId bigint,
	parkingType int,
	isCancelled boolean,
	cancelDeduction double,
	cancelledBy bigint,
	cancellationDate date,
	cancellationComment clob,
	unitModificationDetails clob,
	demandLetterGenerationDate date,
	PRIMARY KEY (unitBookingID)
);


CREATE TABLE unitClassificationMaster
(
	unitClassId bigint auto_increment NOT NULL UNIQUE,
	-- clss code
	unitClassCode varchar(10) NOT NULL,
	unitClassDesc varchar(30),
	PRIMARY KEY (unitClassId)
);


CREATE TABLE unitMaster
(
	unitID bigint auto_increment NOT NULL UNIQUE,
	projectBuilding bigint NOT NULL,
	unitType bigint NOT NULL,
	unitClassId bigint NOT NULL,
	unitPricePolicyID bigint,
	unitNumber varchar(32) NOT NULL,
	floorNumber int NOT NULL,
	floorType int NOT NULL,
	carpetArea bigint NOT NULL,
	saleableArea bigint NOT NULL,
	carpetAreaForTerrace bigint NOT NULL,
	parkingArea double,
	floorRise double,
	otherOptions boolean NOT NULL,
	bookingAmount double,
	otherCharges double,
	registrationDone boolean,
	agreementValue double,
	agreementValureReadyReckoner double,
	totalTax double,
	totalCostWithTax double,
	totalCostWithTaxReadyReckoner double,
	totalCost double,
	totalCostReadyReckoner double,
	PRIMARY KEY (unitID)
);


CREATE TABLE unitModificationState
(
	unitModificationStateID bigint auto_increment NOT NULL UNIQUE,
	unitModificationStateName varchar(64) NOT NULL,
	PRIMARY KEY (unitModificationStateID)
);


CREATE TABLE unitModificationStatus
(
	unitModificationStatusID bigint auto_increment NOT NULL UNIQUE,
	statusDate date NOT NULL,
	statusComment clob,
	unitBookingID bigint NOT NULL,
	unitModificationStateID bigint NOT NULL,
	updatedBy bigint NOT NULL,
	PRIMARY KEY (unitModificationStatusID)
);


CREATE TABLE unitPaymentSchedule
(
	unitPaymentScheduleID bigint auto_increment NOT NULL UNIQUE,
	projectBuildingID bigint NOT NULL,
	paymentSchedulePosition int NOT NULL,
	paymentScheduleType varchar(64) NOT NULL,
	paymentScheduleDescription clob,
	percentAmount double,
	paymentScheduleDate date,
	applicableTo int NOT NULL,
	PRIMARY KEY (unitPaymentScheduleID)
);


CREATE TABLE unitPricePolicy
(
	unitPricePolicyID bigint auto_increment NOT NULL UNIQUE,
	policyName varchar(64) NOT NULL,
	baseRate double NOT NULL,
	readyReckonerRate double NOT NULL,
	landRate double NOT NULL,
	interestRate double,
	gracePeriod int,
	stampDuty double NOT NULL,
	registrationCharge double NOT NULL,
	serviceTax double NOT NULL,
	valueAddedTax double NOT NULL,
	maintenanceCharge1 double NOT NULL,
	maintenanceCharge1Duration int,
	maintenanceCharge2 double NOT NULL,
	legalCharge double NOT NULL,
	PRIMARY KEY (unitPricePolicyID)
);


CREATE TABLE unitType
(
	unitTypeID bigint auto_increment NOT NULL UNIQUE,
	unitTypeCode varchar(8),
	unitTypeName varchar(64),
	PRIMARY KEY (unitTypeID)
);


CREATE TABLE userMaster
(
	userMasterID bigint auto_increment NOT NULL UNIQUE,
	personDetail bigint NOT NULL,
	userAddress bigint NOT NULL,
	loginName varchar(64) NOT NULL,
	password varchar(64),
	isActive boolean NOT NULL,
	PRIMARY KEY (userMasterID)
);


CREATE TABLE userRole
(
	userRoleID bigint auto_increment NOT NULL UNIQUE,
	orgUser bigint NOT NULL,
	role bigint NOT NULL,
	PRIMARY KEY (userRoleID)
);



/* Create Foreign Keys */

ALTER TABLE actionRole
	ADD FOREIGN KEY (actionMasterID)
	REFERENCES actionMaster (actionMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE userMaster
	ADD FOREIGN KEY (userAddress)
	REFERENCES address (addressID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE customerMaster
	ADD FOREIGN KEY (address)
	REFERENCES address (addressID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE projectMaster
	ADD FOREIGN KEY (address)
	REFERENCES address (addressID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE bankBranch
	ADD FOREIGN KEY (branchAddress)
	REFERENCES address (addressID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE organization
	ADD FOREIGN KEY (orgAddress)
	REFERENCES address (addressID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitAmenity
	ADD FOREIGN KEY (amenity)
	REFERENCES amenity (amenityID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE amenityPricePolicy
	ADD FOREIGN KEY (amenityID)
	REFERENCES amenity (amenityID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE projectBankAccount
	ADD FOREIGN KEY (accountType)
	REFERENCES bankAccountType (bankAccountTypeID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE paymentMaster
	ADD FOREIGN KEY (bankBranch)
	REFERENCES bankBranch (bankBranchID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE refundMaster
	ADD FOREIGN KEY (bankBranch)
	REFERENCES bankBranch (bankBranchID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE projectBankAccount
	ADD FOREIGN KEY (bankBranch)
	REFERENCES bankBranch (bankBranchID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE address
	ADD FOREIGN KEY (city)
	REFERENCES cityMaster (cityMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE organization
	ADD FOREIGN KEY (contactInfo)
	REFERENCES contactInfo (contactInfoID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE person
	ADD FOREIGN KEY (contactInfo)
	REFERENCES contactInfo (contactInfoID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitBooking
	ADD FOREIGN KEY (customer)
	REFERENCES customerMaster (customerID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE enquiry
	ADD FOREIGN KEY (customer)
	REFERENCES customerMaster (customerID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE enquiryComment
	ADD FOREIGN KEY (enquiry)
	REFERENCES enquiry (enquiryID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE projectMaster
	ADD FOREIGN KEY (orgnization)
	REFERENCES organization (orgID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE actionMaster
	ADD FOREIGN KEY (pageMasterID)
	REFERENCES pageMaster (pageMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitBooking
	ADD FOREIGN KEY (parkingMasterId)
	REFERENCES parkingMaster (parkingMasterId)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE parkingMaster
	ADD FOREIGN KEY (parkingTypeId)
	REFERENCES parkingType (parkingTypeId)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE paymentStatus
	ADD FOREIGN KEY (paymentID)
	REFERENCES paymentMaster (paymentID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE paymentStage
	ADD FOREIGN KEY (paymentID)
	REFERENCES paymentMaster (paymentID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE paymentStatus
	ADD FOREIGN KEY (paymentStateID)
	REFERENCES paymentState (paymentStateID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE paymentMaster
	ADD FOREIGN KEY (paymentType)
	REFERENCES paymentType (paymentTypeID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE customerMaster
	ADD FOREIGN KEY (personDetail)
	REFERENCES person (personID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE organization
	ADD FOREIGN KEY (contactPerson)
	REFERENCES person (personID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE customerMaster
	ADD FOREIGN KEY (coOwnerDetail)
	REFERENCES person (personID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE userMaster
	ADD FOREIGN KEY (personDetail)
	REFERENCES person (personID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitPaymentSchedule
	ADD FOREIGN KEY (projectBuildingID)
	REFERENCES projectBuilding (projectBuildingID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitMaster
	ADD FOREIGN KEY (projectBuilding)
	REFERENCES projectBuilding (projectBuildingID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE parkingMaster
	ADD FOREIGN KEY (projectBuildingID)
	REFERENCES projectBuilding (projectBuildingID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE projectPhase
	ADD FOREIGN KEY (project)
	REFERENCES projectMaster (projectID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE projectBankAccount
	ADD FOREIGN KEY (project)
	REFERENCES projectMaster (projectID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE projectBuilding
	ADD FOREIGN KEY (projectPhase)
	REFERENCES projectPhase (projectPhaseID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE userRole
	ADD FOREIGN KEY (role)
	REFERENCES roleMaster (roleMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE actionRole
	ADD FOREIGN KEY (roleMasterID)
	REFERENCES roleMaster (roleMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE enquiry
	ADD FOREIGN KEY (sourceName)
	REFERENCES sourceMaster (sourceMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE address
	ADD FOREIGN KEY (state)
	REFERENCES stateMaster (stateMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE refundMaster
	ADD FOREIGN KEY (unitBookingID)
	REFERENCES unitBooking (unitBookingID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitModificationStatus
	ADD FOREIGN KEY (unitBookingID)
	REFERENCES unitBooking (unitBookingID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE paymentMaster
	ADD FOREIGN KEY (unitBooking)
	REFERENCES unitBooking (unitBookingID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitMaster
	ADD FOREIGN KEY (unitClassId)
	REFERENCES unitClassificationMaster (unitClassId)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitAmenity
	ADD FOREIGN KEY (unit)
	REFERENCES unitMaster (unitID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitBooking
	ADD FOREIGN KEY (unit)
	REFERENCES unitMaster (unitID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitModificationStatus
	ADD FOREIGN KEY (unitModificationStateID)
	REFERENCES unitModificationState (unitModificationStateID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE amenityPricePolicy
	ADD FOREIGN KEY (unitPricePolicyID)
	REFERENCES unitPricePolicy (unitPricePolicyID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitMaster
	ADD FOREIGN KEY (unitPricePolicyID)
	REFERENCES unitPricePolicy (unitPricePolicyID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE enquiry
	ADD FOREIGN KEY (interest)
	REFERENCES unitType (unitTypeID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitMaster
	ADD FOREIGN KEY (unitType)
	REFERENCES unitType (unitTypeID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE paymentMaster
	ADD FOREIGN KEY (orgUser)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE enquiry
	ADD FOREIGN KEY (orgUser)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitBooking
	ADD FOREIGN KEY (cancelledBy)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE enquiryComment
	ADD FOREIGN KEY (orgUser)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE customerMaster
	ADD FOREIGN KEY (createdBy)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE userRole
	ADD FOREIGN KEY (orgUser)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE paymentStatus
	ADD FOREIGN KEY (updatedBy)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitModificationStatus
	ADD FOREIGN KEY (updatedBy)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitBooking
	ADD FOREIGN KEY (bookedBy)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



