-- unitType
INSERT INTO unitType (unitTypeID, unitTypeCode, unitTypeName) VALUES ('1', 'ONE', '1 BHK');
INSERT INTO unitType (unitTypeID, unitTypeCode, unitTypeName) VALUES ('2', 'TWO', '2 BHK');
INSERT INTO unitType (unitTypeID, unitTypeCode, unitTypeName) VALUES ('3', 'THREE', '3 BHK');


-- paymentState
INSERT INTO paymentState (paymentStateID, paymentStateName, allowStateChange) VALUES ('1', 'Received not deposited', 'true');
INSERT INTO paymentState (paymentStateID, paymentStateName, allowStateChange) VALUES ('2', 'Awaiting clearance', 'true');
INSERT INTO paymentState (paymentStateID, paymentStateName, allowStateChange) VALUES ('3', 'Cleared', 'false');
INSERT INTO paymentState (paymentStateID, paymentStateName, allowStateChange) VALUES ('4', 'Bounced', 'false');

-- unitModificationState
INSERT INTO unitModificationState (unitModificationStateID, unitModificationStateName) VALUES ('1', 'Request submitted');
INSERT INTO unitModificationState (unitModificationStateID, unitModificationStateName) VALUES ('2', 'Request approved');
INSERT INTO unitModificationState (unitModificationStateID, unitModificationStateName) VALUES ('3', 'Request declined');

-- paymentType
INSERT INTO paymentType (paymentTypeID, paymentTypeCode, paymentTypeDescription) VALUES ('1', 'CASH', 'Cash');
INSERT INTO paymentType (paymentTypeID, paymentTypeCode, paymentTypeDescription) VALUES ('2', 'CCRD', 'Credit Card');
INSERT INTO paymentType (paymentTypeID, paymentTypeCode, paymentTypeDescription) VALUES ('3', 'DCRD', 'Debit Card');
INSERT INTO paymentType (paymentTypeID, paymentTypeCode, paymentTypeDescription) VALUES ('4', 'BCHQ', 'Bank Cheque');

-- parkingType
INSERT INTO parkingType (parkingTypeId, parkingCode, parkingName) VALUES ('1', 'COV', 'Covered Parking');
INSERT INTO parkingType (parkingTypeId, parkingCode, parkingName) VALUES ('2', 'OPEN', 'Open Parking');
INSERT INTO parkingType (parkingTypeId, parkingCode, parkingName) VALUES ('3', 'BSMT', 'Basement Parking');

-- sourceMaster
INSERT INTO sourceMaster (sourceMasterID, sourceCode, sourceName) VALUES ('1', 'NNA', 'www.99acres.com');
INSERT INTO sourceMaster (sourceMasterID, sourceCode, sourceName) VALUES ('2', 'MB', '');


-- stateMaster
INSERT INTO stateMaster (stateMasterID, stateCode, stateName) VALUES ('1', 'KA', 'Karnataka');
INSERT INTO stateMaster (stateMasterID, stateCode, stateName) VALUES ('2', 'MH', 'Maharastra');

-- amenity
INSERT INTO amenity (amenityID, amenityCode, amenityDescription) VALUES ('1', 'GF', 'Garden Facing');
INSERT INTO amenity (amenityID, amenityCode, amenityDescription) VALUES ('2', 'CHF', 'Clubhouse Facing');

-- cityMaster
INSERT INTO cityMaster (cityMasterID, cityCode, cityName) VALUES ('1', 'BLR', 'Bangalore');
INSERT INTO cityMaster (cityMasterID, cityCode, cityName) VALUES ('2', 'PNQ', 'Pune');

-- bankAccountType
INSERT INTO bankAccountType (bankAccountTypeID, accountTypeCode, accountTypeName) VALUES ('1', 'BLDRAC', 'Builder Account');
INSERT INTO bankAccountType (bankAccountTypeID, accountTypeCode, accountTypeName) VALUES ('2', 'TAXAC', 'Tax Account');
INSERT INTO bankAccountType (bankAccountTypeID, accountTypeCode, accountTypeName) VALUES ('3', 'LEGAC', 'Legal Account');
INSERT INTO bankAccountType (bankAccountTypeID, accountTypeCode, accountTypeName) VALUES ('4', 'SOCAC', 'Society Account');

INSERT INTO contactInfo (contactInfoID, phoneNumber, alternateNumber, mobileNumber, emailID) VALUES ('1', '080-12345678', '', '1234567890', 'arvind@gmail.com');
INSERT INTO contactInfo (contactInfoID, phoneNumber, alternateNumber, mobileNumber, emailID) VALUES ('2', '020-12345678', '', '1234567890', 'manoj@gmail.com');
INSERT INTO contactInfo (contactInfoID, phoneNumber, alternateNumber, mobileNumber, emailID) VALUES ('3', '020-12345678', '', '1234567890', 'manoj@gmail.com');
INSERT INTO contactInfo (contactInfoID, phoneNumber, alternateNumber, mobileNumber, emailID) VALUES ('4', '020-12345678', '', '1234567890', 'yash@gmail.com');

INSERT INTO person (personID, firstName, middleName, lastName, contactInfo, dateOfBirth, profession, otherDetail) VALUES ('1', 'Arvind', 'D', 'Sharma', '1', '1978-06-01', 'Service', 'Other');
INSERT INTO person (personID, firstName, middleName, lastName, contactInfo, dateOfBirth, profession, otherDetail) VALUES ('2', 'Manoj', 'C', 'Patil', '2', '1974-06-01', 'Service', 'Other');
INSERT INTO person (personID, firstName, middleName, lastName, contactInfo, dateOfBirth, profession, otherDetail) VALUES ('3', 'Yash', 'C', 'Patil', '4', '1974-06-01', 'Service', 'Other');

INSERT INTO address (addressID, addressLine1, addressLine2, city, state, zipCode) VALUES ('1', 'Line 1', 'Line 2', '1', '1', '564045');
INSERT INTO address (addressID, addressLine1, addressLine2, city, state, zipCode) VALUES ('2', 'Line 1', 'Line 2', '2', '2', '411027');
INSERT INTO address (addressID, addressLine1, addressLine2, city, state, zipCode) VALUES ('3', 'Line 1', 'Line 2', '2', '2', '411027');
INSERT INTO address (addressID, addressLine1, addressLine2, city, state, zipCode) VALUES ('4', 'Line 1', 'Line 2', '2', '2', '411027');

INSERT INTO roleMaster (roleMasterID, roleName, roleDescription, isAdmin) VALUES ('1', 'Sales Executive', '', 'TRUE');

INSERT INTO userMaster (userMasterID, personDetail, userAddress, loginName, password, isActive) VALUES ('1', '1', '1', 'arvind', 'password', 'TRUE');

INSERT INTO userRole (userRoleID, orgUser, role) VALUES ('1', '1', '1');


-- organization
INSERT INTO organization (orgID, orgName, orgAddress, contactInfo, contactPerson, logoFileName) VALUES ('1', 'DRG Landmarks', '2', '2', '3', 'drg_logo.png');


-- projectMaster
INSERT INTO projectMaster (projectID, orgnization, address, projectName, projectDescription, totalPhases, termsAndConditions, logoFileName) VALUES ('1', '1', '3', 'Ostia', 'Sample', '1', 'test', 'ostia_logo.png');


-- projectPhase
INSERT INTO projectPhase (projectPhaseID, project, projectPhaseName, projectPhaseDescription) VALUES ('1', '1', 'Phase 1', 'Test');


-- projectBuilding
INSERT INTO projectBuilding (projectBuildingID, projectPhase, buildingName, wingName, floorCount, buildingType, currentStatus, expectedCompletionDate, hasMultiplePaymentSchedules, remarks) VALUES ('1', '1', 'A', 'A', '12', 'Residential', '0', null, '0', '');


-- unitPricePolicy
INSERT INTO unitPricePolicy (unitPricePolicyID, policyName, baseRate, readyReckonerRate, stampDuty, registrationCharge, serviceTax, valueAddedTax, maintenanceCharge1, maintenanceCharge2, legalCharge) VALUES ('1', 'Ostia5000', '5000', '4000', '6', '1', '3', '1', '50', '50', '10000');


-- customerMaster
INSERT INTO customerMaster (customerID, personDetail, address, createdBy) VALUES ('1', '3', '4', '1');
