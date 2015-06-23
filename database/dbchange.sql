CREATE TABLE amenityPricePolicy
(
	amenityPricePolicyID bigint auto_increment NOT NULL UNIQUE,
	amenityID bigint NOT NULL,
	unitPricePolicyID bigint NOT NULL,
	amenityCharge double NOT NULL,
	PRIMARY KEY (amenityPricePolicyID)
);

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

ALTER TABLE unitPricePolicy
	ADD maintenanceCharge1Duration int

ALTER TABLE paymentMaster
	ADD utrNumber varchar(64)

ALTER TABLE customerMaster
	ADD coOwnerDetail bigint
	
ALTER TABLE customerMaster
	ADD FOREIGN KEY (coOwnerDetail)
	REFERENCES person (personID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

alter table person alter contactInfo drop not null;