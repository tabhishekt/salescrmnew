-- unitType
INSERT INTO unitType (unitTypeID, unitTypeCode, unitTypeName) VALUES ('1', 'ONE', '1 BHK');
INSERT INTO unitType (unitTypeID, unitTypeCode, unitTypeName) VALUES ('2', 'TWO', '2 BHK');
INSERT INTO unitType (unitTypeID, unitTypeCode, unitTypeName) VALUES ('3', 'THREE', '3 BHK');


-- paymentState
INSERT INTO paymentState (paymentStateID, paymentStateName, allowStateChange) VALUES ('1', 'Received not deposited', 'true');
INSERT INTO paymentState (paymentStateID, paymentStateName, allowStateChange) VALUES ('2', 'Awaiting clearance', 'true');
INSERT INTO paymentState (paymentStateID, paymentStateName, allowStateChange) VALUES ('3', 'Cleared', 'false');
INSERT INTO paymentState (paymentStateID, paymentStateName, allowStateChange) VALUES ('4', 'Bounced', 'false');


-- paymentType
INSERT INTO paymentType (paymentTypeID, paymentTypeCode, paymentTypeDescription) VALUES ('1', 'CASH', 'Cash');
INSERT INTO paymentType (paymentTypeID, paymentTypeCode, paymentTypeDescription) VALUES ('2', 'CCRD', 'Credit Card');
INSERT INTO paymentType (paymentTypeID, paymentTypeCode, paymentTypeDescription) VALUES ('3', 'DCRD', 'Debit Card');
INSERT INTO paymentType (paymentTypeID, paymentTypeCode, paymentTypeDescription) VALUES ('4', 'BCHQ', 'Bank Cheque');


-- sourceMaster
INSERT INTO sourceMaster (sourceMasterID, sourceCode, sourceName) VALUES ('1', 'NNA', 'www.99acres.com');
INSERT INTO sourceMaster (sourceMasterID, sourceCode, sourceName) VALUES ('2', 'MB', '');


-- stateMaster
INSERT INTO stateMaster (stateMasterID, stateCode, stateName) VALUES ('2', 'MH', 'Maharastra');
INSERT INTO stateMaster (stateMasterID, stateCode, stateName) VALUES ('1', 'KA', 'Karnataka');

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

INSERT INTO person (personID, firstName, middleName, lastName, contactInfo, dateOfBirth, profession, otherDetail) VALUES ('1', 'Arvind', 'D', 'Sharma', '1', '1978-06-01', 'Service', 'Other');

INSERT INTO address (addressID, addressLine1, addressLine2, city, state, zipCode) VALUES ('1', 'Line 1', 'Line 2', '1', '1', '564045');

INSERT INTO roleMaster (roleMasterID, roleName, roleDescription, isAdmin) VALUES ('1', 'Sales Executive', '', 'TRUE');

INSERT INTO userMaster (userMasterID, personDetail, userAddress, loginName, password, isActive) VALUES ('1', '1', '1', 'arvind', 'password', 'TRUE');

INSERT INTO userRole (userRoleID, orgUser, role) VALUES ('1', '1', '1');
