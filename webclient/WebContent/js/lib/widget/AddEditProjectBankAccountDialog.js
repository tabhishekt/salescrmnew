define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditProjectBankAccountTemplate.html"
], function (declare, registry, AddEditDialog, AddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.projectId) {
				this.project.set("value", this.projectId);
			}
		},
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.project.set("value", data.projectId);
			this.bankaccounttype.set("item", data.bankAccountType);
			this.accountholdername.set("value", data.accountHolderName);
			this.bankname.set("value", data.bankName);
			this.accountnumber.set("value", data.accountNumber);
			this.ifsccode.set("value", data.ifscCode);
			this.micrcode.set("value", data.micrCode);
			
			this.address.addressline1.set("value", data.address.addressLine1);
			this.address.addressline2.set("value", data.address.addressLine2);
			this.address.city.set("item", data.address.city);
			this.address.state.set("item", data.address.state);
			this.address.zipcode.set("value", data.address.zipCode);
		},
		
		postCreate:function () {
			this.inherited(arguments);
			this.setDataToDropdown('../rest/json/data/codetable/bankaccounttype/get/all', this.bankaccounttype);
        }
    });
});