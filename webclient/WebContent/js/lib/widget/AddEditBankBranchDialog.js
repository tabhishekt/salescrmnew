define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditBankBranchTemplate.html"
], function (declare, registry, AddEditDialog, AddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.bankname.set("value", data.bankName);
			this.branchname.set("value", data.branchName);
			this.ifsccode.set("value", data.ifscCode);
			this.micrcode.set("value", data.micrCode);
			
			this.branchaddress.addressline1.set("value", data.branchAddress.addressLine1);
			this.branchaddress.addressline2.set("value", data.branchAddress.addressLine2);
			this.branchaddress.city.set("item", data.branchAddress.city);
			this.branchaddress.state.set("item", data.branchAddress.state);
			this.branchaddress.zipcode.set("value", data.branchAddress.zipCode);
		}
    });
});