CREATE TABLE amenityPricePolicy
(
	amenityPricePolicyID bigint auto_increment NOT NULL UNIQUE,
	amenityID bigint NOT NULL,
	unitPricePolicyID bigint NOT NULL,
	amenityCharge double NOT NULL,
	PRIMARY KEY (amenityPricePolicyID)
);

ALTER TABLE unitPricePolicy
	ADD maintenanceCharge1Duration int

ALTER TABLE amenityPricePolicy
	ADD FOREIGN KEY (amenityID)
	REFERENCES amenity (amenityID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE amenityPricePolicy
	ADD FOREIGN KEY (unitPricePolicyID)
	REFERENCES unitPricePolicy (unitPricePolicyID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE amenityPricePolicy
	ADD FOREIGN KEY (amenityID)
	REFERENCES amenity (amenityID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;