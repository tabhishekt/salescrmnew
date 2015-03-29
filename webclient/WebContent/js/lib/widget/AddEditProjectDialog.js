define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    'lib/widget/BankAddressWidget',
    "dojo/text!./template/AddEditProjectTemplate.html"
], function (declare, registry, AddEditDialog, AddressWidget, BankAddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.name.set("value", data.name);
			this.description.set("value", data.description);
			this.totalphases.set("value", data.totalPhases);
			this.organization.set("item", data.organization);
			this.address.addressline1.set("value", data.address.addressLine1);
			this.address.addressline2.set("value", data.address.addressLine2);
			this.address.city.set("item", data.address.city);
			this.address.state.set("item", data.address.state);
			this.address.zipcode.set("value", data.address.zipCode);
			
			this.accountholdername.set("value", data.accountHolderName);
			this.bankname.set("value", data.bankName);
			this.accountnumber.set("value", data.accountNumber);
			this.ifsccode.set("value", data.ifscCode);
			this.micrcode.set("value", data.micrCode);
			
			this.bankaddress.bankaddressline1.set("value", data.bankAddress.addressLine1);
			this.bankaddress.bankaddressline2.set("value", data.bankAddress.addressLine2);
			this.bankaddress.bankcity.set("item", data.bankAddress.city);
			this.bankaddress.bankstate.set("item", data.bankAddress.state);
			this.bankaddress.bankzipcode.set("value", data.bankAddress.zipCode);
			
			this.termsandconditions.set("value", data.termsAndConditions);
		},
		
		postCreate:function () {
			this.inherited(arguments);
        	this.setDataToDropdown('../rest/json/data/inventory/organization/get/all', this.organization);
        }
    });
});