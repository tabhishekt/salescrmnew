define([
    'dojo/_base/declare',
    "dijit/registry",
    "lib/widget/AddEditDialog",
    'lib/widget/AddressWidget',
    "dojo/text!./template/AddEditUnitPaymentScheduleTemplate.html"
], function (declare, registry, AddEditDialog, AddressWidget, template) {
    return declare([AddEditDialog], {
		templateString: template,
		
		reset : function () {
			this.inherited(arguments);
			if (this.buildingId) {
				this.projectbuilding.set("value", this.buildingId);
			}
		},
		setData: function (data) {
			this.rowId.set("value", data.id);
			this.projectbuilding.set("value", data.buildingId);
			this.type.set("value", data.type);
			this.description.set("value", data.description);
			this.scheduledate.set("value", data.scheduledate);
			this.percentamount.set("value", data.percentamount);
		}
    });
});