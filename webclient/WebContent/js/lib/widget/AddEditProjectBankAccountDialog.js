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
			this.bankbranch.set("item", data.bankBranch);
			this.accountholdername.set("value", data.accountHolderName);
			this.accountnumber.set("value", data.accountNumber);
		},
		
		postCreate:function () {
			this.inherited(arguments);
			this.setDataToDropdown('../rest/json/data/codetable/bankaccounttype/get/all', this.bankaccounttype);
			this.setDataToDropdown('../rest/json/data/inventory/bankbranch/get/all', this.bankbranch);
        }
    });
});