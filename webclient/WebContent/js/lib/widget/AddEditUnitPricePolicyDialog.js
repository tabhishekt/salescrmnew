define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditUnitPricePolicyTemplate.html"
], function (declare, registry, AddEditDialog, AddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.policyname.set("value", data.policyName);
			this.baserate.set("value", data.baserate);
			this.stampduty.set("value", data.stampduty);
			this.registrationcharge.set("value", data.registrationcharge);
			this.servicetax.set("value", data.servicetax);
			this.valueaddedtax.set("value", data.valueaddedtax);
			this.maintenancecharge1.set("value", data.maintenancecharge1);
			this.maintenancecharge2.set("value", data.maintenancecharge2);
			this.legalcharge.set("value", data.legalcharge);
		}
    });
});