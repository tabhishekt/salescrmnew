
/* Drop Tables */

DROP TABLE actionRole;
DROP TABLE actionMaster;
DROP TABLE unitAmenity;
DROP TABLE refundMaster;
DROP TABLE paymentStage;
DROP TABLE paymentStatus;
DROP TABLE paymentMaster;
DROP TABLE unitBooking;
DROP TABLE unitMaster;
DROP TABLE unitPaymentSchedule;
DROP TABLE projectBuilding;
DROP TABLE projectPhase;
DROP TABLE projectMaster;
DROP TABLE enquiryComment;
DROP TABLE userRole;
DROP TABLE enquiry;
DROP TABLE customerMaster;
DROP TABLE userMaster;
DROP TABLE organization;
DROP TABLE address;
DROP TABLE amenity;
DROP TABLE cityMaster;
DROP TABLE person;
DROP TABLE contactInfo;
DROP TABLE pageMaster;
DROP TABLE paymentState;
DROP TABLE paymentType;
DROP TABLE roleMaster;
DROP TABLE sourceMaster;
DROP TABLE stateMaster;
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
	PRIMARY KEY (orgID)
);


CREATE TABLE pageMaster
(
	pageMasterID bigint auto_increment NOT NULL UNIQUE,
	pageName varchar(64) NOT NULL,
	pageDescription clob,
	PRIMARY KEY (pageMasterID)
);


CREATE TABLE paymentMaster
(
	paymentID bigint auto_increment NOT NULL UNIQUE,
	unitBooking bigint NOT NULL,
	paymentType bigint NOT NULL,
	orgUser bigint NOT NULL,
	receiptNumber bigint NOT NULL,
	altReceiptNumber bigint,
	paymentAmount double NOT NULL,
	paymentDescription clob,
	paymentReceivedDate date,
	bankName varchar(64),
	bankBranch varchar(64),
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
	contactInfo bigint NOT NULL,
	dateOfBirth date,
	profession varchar(64),
	otherDetail clob,
	PRIMARY KEY (personID)
);


CREATE TABLE projectBuilding
(
	projectBuildingID bigint auto_increment NOT NULL UNIQUE,
	projectPhase bigint NOT NULL,
	buildingName varchar(64) NOT NULL,
	wingName varchar(64),
	floorCount bigint NOT NULL,
	buildingType varchar(64),
	currentStatus int,
	expectedCompletionDate date,
	hasMultiplePaymentSchedules boolean NOT NULL,
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
	bankName varchar(64),
	bankAddress bigint NOT NULL,
	accountNumber varchar(64),
	accountHolderName varchar(64),
	ifscCode varchar(64),
	micrCode varchar(64),
	termsAndConditions clob,
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
	bankName varchar(64),
	bankBranch varchar(64),
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
	isCancelled boolean,
	cancelDeduction double,
	cancelledBy bigint,
	cancellationDate date,
	cancellationComment clob,
	PRIMARY KEY (unitBookingID)
);


CREATE TABLE unitMaster
(
	unitID bigint auto_increment NOT NULL UNIQUE,
	projectBuilding bigint NOT NULL,
	unitType bigint NOT NULL,
	unitPricePolicyID bigint,
	unitNumber varchar(32) NOT NULL,
	floorNumber int NOT NULL,
	floorType int NOT NULL,
	carpetArea bigint NOT NULL,
	saleableArea bigint NOT NULL,
	floorRise double,
	otherOptions boolean NOT NULL,
	bookingAmount double,
	otherCharges double,
	registrationDone boolean,
	agreementValue double,
	totalTax double,
	totalCostWithTax double,
	totalCost double,
	PRIMARY KEY (unitID)
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
	stampDuty double NOT NULL,
	registrationCharge double NOT NULL,
	serviceTax double NOT NULL,
	valueAddedTax double NOT NULL,
	maintenanceCharge1 double NOT NULL,
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


ALTER TABLE projectMaster
	ADD FOREIGN KEY (bankAddress)
	REFERENCES address (addressID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE userMaster
	ADD FOREIGN KEY (userAddress)
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


ALTER TABLE unitAmenity
	ADD FOREIGN KEY (amenity)
	REFERENCES amenity (amenityID)
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


ALTER TABLE enquiry
	ADD FOREIGN KEY (customer)
	REFERENCES customerMaster (customerID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitBooking
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


ALTER TABLE paymentStage
	ADD FOREIGN KEY (paymentID)
	REFERENCES paymentMaster (paymentID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE paymentStatus
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


ALTER TABLE userMaster
	ADD FOREIGN KEY (personDetail)
	REFERENCES person (personID)
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


ALTER TABLE unitMaster
	ADD FOREIGN KEY (projectBuilding)
	REFERENCES projectBuilding (projectBuildingID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE unitPaymentSchedule
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


ALTER TABLE paymentMaster
	ADD FOREIGN KEY (unitBooking)
	REFERENCES unitBooking (unitBookingID)
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


ALTER TABLE enquiryComment
	ADD FOREIGN KEY (orgUser)
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


ALTER TABLE paymentMaster
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


ALTER TABLE userRole
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


ALTER TABLE customerMaster
	ADD FOREIGN KEY (createdBy)
	REFERENCES userMaster (userMasterID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


