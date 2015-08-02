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